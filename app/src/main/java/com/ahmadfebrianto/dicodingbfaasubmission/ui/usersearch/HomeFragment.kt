package com.ahmadfebrianto.dicodingbfaasubmission.ui.usersearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.ahmadfebrianto.dicodingbfaasubmission.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val svUserName: SearchView = view.findViewById(R.id.sv_username)
        svUserName.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val bundle = Bundle()
                    bundle.putString(SearchResultFragment.KEYWORD, query)

                    val searchResultFragment = SearchResultFragment()
                    searchResultFragment.arguments = bundle

                    fragmentManager?.beginTransaction()?.apply {
                        replace(
                            R.id.frame_container,
                            searchResultFragment,
                            SearchResultFragment::class.java.simpleName
                        )
                        addToBackStack(null)
                        commit()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}