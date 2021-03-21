package com.ahmadfebrianto.dicodingandroidfundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.w3c.dom.Text

class UserDetail : AppCompatActivity() {

    companion object {
        const val USER_PROFILE = "user_profile"
        const val USER_FULLNAME = "user_fullname"
        const val USER_USERNAME = "user_username"
        const val USER_LOCATION = "user_location"
        const val USER_COMPANY = "user_company"
        const val USER_REPO = "user_repo"
        const val USER_FOLLOWERS = "user_followers"
        const val USER_FOLLOWING = "user_following"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val actionBar = supportActionBar
        actionBar?.title = "User Detail"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val userProfile: ImageView = findViewById(R.id.user_profile)
        Glide.with(this)
            .load(intent.getStringExtra(USER_PROFILE)?.toInt())
            .apply(RequestOptions())
            .into(userProfile)

        val userFullName: TextView = findViewById(R.id.full_name)
        userFullName.text = intent.getStringExtra(USER_FULLNAME)

        val userUserName: TextView = findViewById(R.id.user_name)
        userUserName.text = intent.getStringExtra(USER_USERNAME)

        val userLocation: TextView = findViewById(R.id.location)
        userLocation.text = intent.getStringExtra(USER_LOCATION)

        val userCompany: TextView = findViewById(R.id.company)
        userCompany.text = intent.getStringExtra(USER_COMPANY)

        val userRepositories: TextView = findViewById(R.id.repositories)
        userRepositories.text = intent.getStringExtra(USER_REPO)

        val userFollowers: TextView = findViewById(R.id.followers)
        userFollowers.text = intent.getStringExtra(USER_FOLLOWERS)

        val userFollowing: TextView = findViewById(R.id.following)
        userFollowing.text = intent.getStringExtra(USER_FOLLOWING)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}