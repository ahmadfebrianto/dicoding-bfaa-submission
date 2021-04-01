package com.ahmadfebrianto.dicodingbfaasubmission

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserDetail : AppCompatActivity() {

    companion object {
        const val USER_OBJECT = "user_object"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val actionBar = supportActionBar
        actionBar?.title = "User Detail"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>(USER_OBJECT) as User

        val userProfile: ImageView = findViewById(R.id.user_profile)
        Glide.with(this)
                .load(user.profile)
                .apply(RequestOptions())
                .into(userProfile)

        val userFullName: TextView = findViewById(R.id.full_name)
        userFullName.text = user.fullName

        val userUserName: TextView = findViewById(R.id.user_name)
        userUserName.text = user.userName

        val userLocation: TextView = findViewById(R.id.location)
        userLocation.text = user.location

        val userCompany: TextView = findViewById(R.id.company)
        userCompany.text = user.company

        val userRepositories: TextView = findViewById(R.id.repositories)
        userRepositories.text = user.repository.toString()

        val userFollowers: TextView = findViewById(R.id.followers)
        userFollowers.text = user.follower.toString()

        val userFollowing: TextView = findViewById(R.id.following)
        userFollowing.text = user.following.toString()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}