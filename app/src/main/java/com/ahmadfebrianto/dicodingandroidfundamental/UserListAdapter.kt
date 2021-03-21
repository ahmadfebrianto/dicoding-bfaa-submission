package com.ahmadfebrianto.dicodingandroidfundamental

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*


class UserListAdapter(private val userList: ArrayList<User>): RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_view, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = userList[position]
        Glide.with(holder.itemView.context)
            .load(user.profile)
            .apply(RequestOptions().override(55,55))
            .into(holder.userProfile)

        holder.userFullName.text = user.fullName
        holder.userUserName.text = user.userName
        holder.userLocation.text = user.location

        val classContext = holder.itemView.context

        holder.itemView.setOnClickListener{
            val detailIntent = Intent(classContext, UserDetail::class.java)
            detailIntent.putExtra(UserDetail.USER_PROFILE, user.profile.toString())
            detailIntent.putExtra(UserDetail.USER_FULLNAME, user.fullName)
            detailIntent.putExtra(UserDetail.USER_USERNAME, user.userName)
            detailIntent.putExtra(UserDetail.USER_LOCATION, user.location)

            detailIntent.putExtra(UserDetail.USER_COMPANY, user.company)
            detailIntent.putExtra(UserDetail.USER_REPO, user.repository.toString())
            detailIntent.putExtra(UserDetail.USER_FOLLOWERS, user.follower.toString())
            detailIntent.putExtra(UserDetail.USER_FOLLOWING, user.following.toString())
            classContext.startActivity(detailIntent)

        }
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var userProfile: ImageView = itemView.findViewById(R.id.user_profile)
        var userFullName: TextView = itemView.findViewById(R.id.full_name)
        var userUserName: TextView = itemView.findViewById(R.id.user_name)
        var userLocation: TextView = itemView.findViewById(R.id.location)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}
