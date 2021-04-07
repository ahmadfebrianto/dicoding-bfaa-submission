package com.ahmadfebrianto.dicodingbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowingViewModel: ViewModel() {
    private val followingList = MutableLiveData<ArrayList<User>>()

    fun setFollowingList(username: String) {
        val userObjects = ArrayList<User>()

        val token = "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"
        val url = "https://api.github.com/users/$username/following?page=1&per_page=20"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", token)
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val jsonString = String(responseBody)
                    val jsonArray = JSONArray(jsonString)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        val user = User()
                        user.avatarUrl = jsonObject.getString("avatar_url")
                        user.username = jsonObject.getString("login")
                        user.userId = jsonObject.getInt("id")
                        user.profileUrl = jsonObject.getString("html_url")

                        userObjects.add(user)
                    }
                    followingList.postValue(userObjects)

                } catch (e: Exception) {
                    Log.d("onSuccess", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowingList() : LiveData<ArrayList<User>> = followingList
}