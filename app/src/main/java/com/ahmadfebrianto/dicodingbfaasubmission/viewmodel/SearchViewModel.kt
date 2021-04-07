package com.ahmadfebrianto.dicodingbfaasubmission.viewmodel

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadfebrianto.dicodingbfaasubmission.R
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class SearchViewModel: ViewModel() {
    private val userList = MutableLiveData<ArrayList<User>>()

    fun setUserList(keyword: String) {
        val userObjects = ArrayList<User>()

        val token = "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"
        val url = "https://api.github.com/search/users?q=$keyword&page=1&per_page=10"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", token)
        client.addHeader("User-Agent", "request")

        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val jsonString = String(responseBody)
                    val jsonObject = JSONObject(jsonString)
                    val jsonArray = jsonObject.getJSONArray("items")

                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val user = User()

                        user.avatarUrl = item.getString("avatar_url")
                        user.username = item.getString("login")
                        user.userId = item.getInt("id")
                        user.profileUrl = item.getString("html_url")

                        userObjects.add(user)
                    }
                    userList.postValue(userObjects)

                } catch (e: Exception) {
                    Log.d("onSuccess", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUserList(): LiveData<ArrayList<User>> = userList


}