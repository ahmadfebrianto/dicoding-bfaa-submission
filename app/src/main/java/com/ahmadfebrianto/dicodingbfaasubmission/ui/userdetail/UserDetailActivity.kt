package com.ahmadfebrianto.dicodingbfaasubmission.ui.userdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.ahmadfebrianto.dicodingbfaasubmission.R
import com.ahmadfebrianto.dicodingbfaasubmission.adapter.SectionsPagerAdapter
import com.ahmadfebrianto.dicodingbfaasubmission.databinding.ActivityUserDetailBinding
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseHelper
import com.ahmadfebrianto.dicodingbfaasubmission.db.FavoriteUserHelper
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import com.ahmadfebrianto.dicodingbfaasubmission.viewmodel.DetailViewModel
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favUserHelper: FavoriteUserHelper
    private var user: User? = null

    companion object {
        const val USER_OBJECT = "user_object"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favUserHelper = FavoriteUserHelper.getInstance(applicationContext)
        favUserHelper.open()

        user = intent.getParcelableExtra(USER_OBJECT)

        supportActionBar?.title = user?.username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user?.let { setDataDetail(it) }
        user?.let { setFollowersFollowing(it) }
        setFavoriteStatus()

        binding.fabFavorite.setOnClickListener {
            handleFavUser()
        }
    }

    private fun setDataDetail(user: User) {
        detailViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        detailViewModel.setUserData(user.username)
        detailViewModel.getUserData().observe(this, { userObject ->

            if (userObject != null) {
                binding.progressBar.visibility = View.GONE
                Glide.with(this)
                    .load(userObject.avatarUrl)
                    .into(binding.ivAvatar)
                binding.tvName.text = userObject.name
                binding.tvCompany.text = userObject.company
                binding.tvLocation.text = userObject.location
                binding.tvBio.text = userObject.bio
                binding.tvFollowers.text = userObject.followers.toString()
                binding.tvFollowing.text = userObject.following.toString()
                binding.tvRepo.text = userObject.repository.toString()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setFollowersFollowing(user: User) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = user.username
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    private fun checkFavoriteUser(): Boolean {
        return when (favUserHelper.checkFavUserById(user!!.userId)) {
            1 -> true
            else -> false
        }
    }

    private fun setFavoriteStatus() {
        when (checkFavoriteUser()) {
            true -> binding.fabFavorite.setImageResource(R.drawable.ic_is_favorite)
            else -> binding.fabFavorite.setImageResource(R.drawable.ic_not_favorite)
        }
    }

    private fun handleFavUser() {
        when (checkFavoriteUser()) {
            true -> {
                favUserHelper.deleteFavUser(user!!.userId)
                setFavoriteStatus()
            }
            else -> {
                favUserHelper.insertFavUser(user!!)
                setFavoriteStatus()
            }
        }
    }
}