package com.ahmadfebrianto.dicodingandroidfundamental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var user: RecyclerView
    private val userList: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar?.title = "Github User"

        user = findViewById(R.id.rv_user)
        user.setHasFixedSize(true)

        userList.addAll(UserData.userData)
        showRecyclerList()

    }

    private fun showRecyclerList() {
        user.layoutManager = LinearLayoutManager(this)
        val userListAdapter = UserListAdapter(userList)
        user.adapter = userListAdapter
    }


    // Attach option menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        processOption(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun processOption(selectedOption: Int) {
        when (selectedOption) {
            R.id.about_me -> {
                val aboutIntent = Intent(this@MainActivity, AboutMe::class.java)
                startActivity(aboutIntent)
            }
        }
    }

}