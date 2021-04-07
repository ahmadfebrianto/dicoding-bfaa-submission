package com.ahmadfebrianto.dicodingbfaasubmission.ui.usersearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadfebrianto.dicodingbfaasubmission.R
import com.ahmadfebrianto.dicodingbfaasubmission.adapter.SearchResultAdapter
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import com.ahmadfebrianto.dicodingbfaasubmission.viewmodel.SearchViewModel

class SearchResultFragment : Fragment()  {
    companion object{
        var KEYWORD = "keyword"
    }

    private lateinit var rvUser: RecyclerView

    private lateinit var adapter: SearchResultAdapter
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar)

        showRecyclerList()
        searchViewModel = ViewModelProvider(
                this, ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)

        searchViewModel.getUserList().observe(
                viewLifecycleOwner, { listOfUsers ->
            if (listOfUsers != null) {
                adapter.setData(listOfUsers)
                progressBar?.visibility = View.GONE

            }
        }
        )

        if (arguments != null) {
            val userName = arguments?.getString(KEYWORD)
            if (userName != null) {
                searchViewModel.setUserList(userName)
            }
        }
    }


    private fun showRecyclerList() {
        rvUser = view?.findViewById(R.id.rv_user)!!
        rvUser.setHasFixedSize(true)
        rvUser.layoutManager = LinearLayoutManager(activity)
        adapter = SearchResultAdapter()
        rvUser.adapter = adapter
    }
}