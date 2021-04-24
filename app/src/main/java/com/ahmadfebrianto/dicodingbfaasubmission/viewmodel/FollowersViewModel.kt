package com.ahmadfebrianto.dicodingbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadfebrianto.dicodingbfaasubmission.BuildConfig
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowersViewModel : ViewModel() {

    private val followerList = MutableLiveData<ArrayList<User>>()

    fun setFollowerList(username: String) {
        val userObjects = ArrayList<User>()

        val url = "https://api.github.com/users/$username/followers?page=1&per_page=20"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
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
                    followerList.postValue(userObjects)

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

    fun getFollowerList(): LiveData<ArrayList<User>> = followerList
}