package com.ahmadfebrianto.dicodingbfaasubmission.ui.favoriteusers

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadfebrianto.dicodingbfaasubmission.R
import com.ahmadfebrianto.dicodingbfaasubmission.adapter.FavoriteUserAdapter
import com.ahmadfebrianto.dicodingbfaasubmission.databinding.ActivityFavoriteUsersBinding
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.ahmadfebrianto.dicodingbfaasubmission.db.FavoriteUserHelper
import com.ahmadfebrianto.dicodingbfaasubmission.helper.MappingHelper
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import java.util.*

class FavoriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUsersBinding
    private lateinit var adapter: FavoriteUserAdapter
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

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                getFavUserList()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        showRecyclerList()

        binding.progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        getFavUserList()?.let { adapter.refreshFavUserList(it) }
    }

    private fun getFavUserList(): ArrayList<User>? {
        return contentResolver.query(
            CONTENT_URI, null, null, null,
            null
        )?.let {
            MappingHelper.mapCursorToArrayList(
                it
            )
        }
    }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, RecyclerView.VERTICAL)
        binding.rvUser.addItemDecoration(itemDecor)
        binding.rvUser.setHasFixedSize(true)
        adapter = getFavUserList()?.let { FavoriteUserAdapter(it) }!!
        binding.rvUser.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}