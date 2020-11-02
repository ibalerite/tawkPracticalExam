package com.exam.tawk.ui.users

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tawkpracticaltest.R
import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.ui.profile.ProfileActivity
import com.example.tawkpracticaltest.ui.users.UsersViewHolder

class UsersAdapter constructor(
    private val host: Activity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(host)

    var items: List<GithubUser> = emptyList()
        /**
         * Main entry point for setting items to this adapter.
         */
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createUsersViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UsersViewHolder).bind(items[position],
            // flag invert every 4th avatar's color
            invertAvatarColors = (position + 1) % 4 == 0)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    private fun createUsersViewHolder(parent: ViewGroup): UsersViewHolder {
        return UsersViewHolder(layoutInflater.inflate(R.layout.item_user, parent, false)) {
            val intent = Intent(host, ProfileActivity::class.java)
            intent.putExtra(ProfileActivity.EXTRA_USER, it.login)
            host.startActivity(intent)
        }
    }

}