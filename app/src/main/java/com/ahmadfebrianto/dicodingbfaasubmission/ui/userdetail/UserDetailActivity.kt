package com.ahmadfebrianto.dicodingbfaasubmission.ui.userdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ahmadfebrianto.dicodingbfaasubmission.R
import com.ahmadfebrianto.dicodingbfaasubmission.adapter.SectionsPagerAdapter
import com.ahmadfebrianto.dicodingbfaasubmission.databinding.ActivityUserDetailBinding
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import com.ahmadfebrianto.dicodingbfaasubmission.viewmodel.DetailViewModel
import com.ahmadfebrianto.dicodingbfaasubmission.viewmodel.FollowingViewModel
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    private lateinit var detailViewModel: DetailViewModel
    private var user: User? = null

    companion object {
        internal val TAG = UserDetailActivity::class.java
        const val USER_OBJECT = "user_object"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra(USER_OBJECT)
        supportActionBar?.title = user?.username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.progressBar.visibility = View.GONE
        user?.let { setDataDetail(it) }
        user?.let { setFollowersFollowing(it) }

    }

    private fun setDataDetail(user: User) {

        detailViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        detailViewModel.setUserData(user.username)
        detailViewModel.getUserData().observe(this, { userObject ->
            if (userObject != null) {

                Glide.with(this)
                    .load(userObject.avatarUrl)
                    .into(binding.ivAvatar)
                println(userObject.avatarUrl)
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


    private fun setFollowersFollowing(user: User) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = user.username
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}