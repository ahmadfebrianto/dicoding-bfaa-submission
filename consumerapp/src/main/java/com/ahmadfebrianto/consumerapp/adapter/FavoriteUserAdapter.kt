package com.ahmadfebrianto.consumerapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmadfebrianto.consumerapp.databinding.UserRowBinding
import com.ahmadfebrianto.consumerapp.model.User
import com.ahmadfebrianto.consumerapp.ui.userdetail.UserDetailActivity
import com.bumptech.glide.Glide

class FavoriteUserAdapter(private val favUserList: ArrayList<User>): RecyclerView.Adapter<FavoriteUserAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: UserRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.ivAvatar)
            binding.tvUserName.text = user.username
            binding.tvUserId.text = user.userId.toString()
            binding.tvProfileUrl.text = user.profileUrl

            itemView.setOnClickListener {
                val userDetailIntent = Intent(itemView.context, UserDetailActivity::class.java)
                userDetailIntent.putExtra(UserDetailActivity.USER_OBJECT, user)
                itemView.context.startActivity(userDetailIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(favUserList[position])
    }

    override fun getItemCount(): Int = favUserList.size

    fun refreshFavUserList(favUsers: ArrayList<User>) {
        favUserList.clear()
        favUserList.addAll(favUsers)
        notifyDataSetChanged()
    }

}
