package com.ahmadfebrianto.dicodingbfaasubmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmadfebrianto.dicodingbfaasubmission.R
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import com.ahmadfebrianto.dicodingbfaasubmission.ui.userdetail.UserDetailActivity
import com.bumptech.glide.Glide

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ListViewHolder>() {

    private val followerList = ArrayList<User>()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        var userUserName: TextView = itemView.findViewById(R.id.tv_userName)
        var userId: TextView = itemView.findViewById(R.id.tv_userId)
        var userProfileUrl: TextView = itemView.findViewById(R.id.tv_profileUrl)
    }

    fun setData(items: ArrayList<User>) {
        followerList.clear()
        followerList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = followerList[position]

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.userAvatar)
        holder.userUserName.text = user.username
        holder.userId.text = user.userId.toString()
        holder.userProfileUrl.text = user.profileUrl

        holder.itemView.setOnClickListener {
            val userDetailIntent = Intent(holder.itemView.context, UserDetailActivity::class.java)
            userDetailIntent.putExtra(UserDetailActivity.USER_OBJECT, user)
            holder.itemView.context.startActivity(userDetailIntent)
        }
    }

    override fun getItemCount(): Int = followerList.size

}