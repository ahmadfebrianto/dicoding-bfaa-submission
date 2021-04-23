package com.ahmadfebrianto.consumerapp.ui.main

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadfebrianto.consumerapp.R
import com.ahmadfebrianto.consumerapp.adapter.FavoriteUserAdapter
import com.ahmadfebrianto.consumerapp.databinding.ActivityMainBinding
import com.ahmadfebrianto.consumerapp.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.ahmadfebrianto.consumerapp.helper.MappingHelper
import com.ahmadfebrianto.consumerapp.model.User
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.consumer_app)

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
}