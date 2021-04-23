package com.ahmadfebrianto.consumerapp.ui.userdetail

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ahmadfebrianto.consumerapp.R
import com.ahmadfebrianto.consumerapp.adapter.SectionsPagerAdapter
import com.ahmadfebrianto.consumerapp.databinding.ActivityUserDetailBinding
import com.ahmadfebrianto.consumerapp.db.DatabaseContract
import com.ahmadfebrianto.consumerapp.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.ahmadfebrianto.consumerapp.model.User
import com.ahmadfebrianto.consumerapp.viewmodel.DetailViewModel
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var uriWithId: Uri
    private var user: User? = null

    companion object {
        const val USER_OBJECT = "user_object"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra(USER_OBJECT)

        supportActionBar?.title = user?.username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user?.let { setDataDetail(it) }
        user?.let { setFollowersFollowing(it) }
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user?.userId)
        setFavoriteIcon()


        binding.fabFavorite.setOnClickListener {
            handleFavUser()
            checkFavUserById(user!!.userId)
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

    private fun isFavoriteUser(): Boolean {
        return when (checkFavUserById(user!!.userId)) {
            1 -> true
            else -> false
        }
    }

    private fun insertFavUser(user: User): Uri? {
        val args = ContentValues()
        args.put(DatabaseContract.NoteColumns._USER_ID, user.userId)
        args.put(DatabaseContract.NoteColumns.USERNAME, user.username)
        args.put(DatabaseContract.NoteColumns.USER_AVATAR_URL, user.avatarUrl)
        args.put(DatabaseContract.NoteColumns.USER_PROFILE_URL, user.profileUrl)
        return contentResolver.insert(CONTENT_URI, args)
    }

    private fun setFavoriteIcon() {
        when (isFavoriteUser()) {
            true -> {
                binding.fabFavorite.setImageResource(R.drawable.ic_is_favorite)
            }
            else -> {
                binding.fabFavorite.setImageResource(R.drawable.ic_not_favorite)
            }
        }
    }

    private fun handleFavUser() {
        when (isFavoriteUser()) {
            true -> {
                contentResolver.delete(uriWithId, null, null)
                setFavoriteIcon()
            }
            else -> {
                insertFavUser(user!!)
                setFavoriteIcon()
            }
        }
    }

    private fun checkFavUserById(id: Int): Int? {
        val cursor = contentResolver.query(uriWithId, null, null, null, null)
        val num = cursor?.count
        cursor?.close()
        return num
    }

}