package com.ahmadfebrianto.dicodingbfaasubmission.ui.usersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ahmadfebrianto.dicodingbfaasubmission.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val fragment = supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }
    }
}