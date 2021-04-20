package com.ahmadfebrianto.dicodingbfaasubmission.ui.favoriteusers

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadfebrianto.dicodingbfaasubmission.R
import com.ahmadfebrianto.dicodingbfaasubmission.adapter.FavoriteUserAdapter
import com.ahmadfebrianto.dicodingbfaasubmission.databinding.ActivityFavoriteUsersBinding
import com.ahmadfebrianto.dicodingbfaasubmission.db.FavoriteUserHelper
import com.ahmadfebrianto.dicodingbfaasubmission.model.User

class FavoriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUsersBinding
    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var favUserList: ArrayList<User>
    private lateinit var favUserHelper: FavoriteUserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_users)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.progressBar.visibility = View.VISIBLE

        favUserHelper = FavoriteUserHelper.getInstance(applicationContext)
        favUserHelper.open()

        favUserList = getFavUserList()

        showRecyclerList()

        binding.progressBar.visibility = View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.fav_user_refresh_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_refresh) {
            favUserList = getFavUserList()
            adapter.refreshDataset(favUserList)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getFavUserList(): ArrayList<User> {
        return favUserHelper.getAllUsers()
    }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, RecyclerView.VERTICAL)
        binding.rvUser.addItemDecoration(itemDecor)
        binding.rvUser.setHasFixedSize(true)
        adapter = FavoriteUserAdapter(favUserList)
        binding.rvUser.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}