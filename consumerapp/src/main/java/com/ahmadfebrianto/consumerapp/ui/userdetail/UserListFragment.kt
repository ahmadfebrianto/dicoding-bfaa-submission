package com.ahmadfebrianto.consumerapp.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadfebrianto.consumerapp.R
import com.ahmadfebrianto.consumerapp.adapter.FollowersAdapter
import com.ahmadfebrianto.consumerapp.adapter.FollowingAdapter
import com.ahmadfebrianto.consumerapp.viewmodel.FollowersViewModel
import com.ahmadfebrianto.consumerapp.viewmodel.FollowingViewModel

class UserListFragment : Fragment() {

    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var followingAdapter: FollowingAdapter

    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel

    companion object {
        private const val USERNAME = "username"
        private const val SECTION_NUMBER = "section_number"

        fun newInstance(index: Int, username: String?): UserListFragment {
            val fragment = UserListFragment()

            val bundle = Bundle()
            bundle.putString(USERNAME, username)
            bundle.putInt(SECTION_NUMBER, index)

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemDecor = DividerItemDecoration(context, RecyclerView.VERTICAL)

        val username = arguments?.getString(USERNAME)

        val rvUserList: RecyclerView = view.findViewById(R.id.rv_user_list)
        rvUserList.addItemDecoration(itemDecor)
        rvUserList.setHasFixedSize(true)
        rvUserList.layoutManager = LinearLayoutManager(activity)

        when (arguments?.getInt(SECTION_NUMBER, 0) ?: 1) {
            1 -> {
                followersAdapter = FollowersAdapter()
                rvUserList.adapter = followersAdapter

                followersViewModel = ViewModelProvider(
                    this, ViewModelProvider.NewInstanceFactory()
                ).get(FollowersViewModel::class.java)

                followersViewModel.setFollowerList(username.toString())
                followersViewModel.getFollowerList().observe(
                    viewLifecycleOwner, { user ->
                        followersAdapter.setData(user)
                    }
                )
            }

            2 -> {
                followingAdapter = FollowingAdapter()
                rvUserList.adapter = followingAdapter

                followingViewModel = ViewModelProvider(
                    this, ViewModelProvider.NewInstanceFactory()
                ).get(FollowingViewModel::class.java)

                followingViewModel.setFollowingList(username.toString())
                followingViewModel.getFollowingList().observe(viewLifecycleOwner, { user ->
                    if (user != null) {
                        followingAdapter.setData(user)
                    }
                })

            }
        }
    }
}