package com.ahmadfebrianto.dicodingbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    private val userObject = MutableLiveData<User>()

    fun setUserData(username: String?) {
        val token = "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"
        val url = "https://api.github.com/users/$username"

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
                    val jsonObject = JSONObject(jsonString)
                    val user = User()
                    user.avatarUrl = jsonObject.getString("avatar_url")
                    user.username = jsonObject.getString("login")
                    user.name = jsonObject.getString("name")
                    user.company = jsonObject.getString("company")
                    user.location = jsonObject.getString("location")
                    user.repository = jsonObject.getInt("public_repos")
                    user.followers = jsonObject.getInt("followers")
                    user.following = jsonObject.getInt("following")
                    user.bio = jsonObject.getString("bio")

                    userObject.postValue(user)

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

    fun getUserData(): LiveData<User> = userObject
}