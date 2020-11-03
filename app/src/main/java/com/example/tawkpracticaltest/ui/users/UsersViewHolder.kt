package com.example.tawkpracticaltest.ui.users

import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.tawkpracticaltest.R
import com.example.tawkpracticaltest.data.models.GithubUser

class UsersViewHolder constructor(
    itemView: View,
    private val itemClick: ((user: GithubUser) -> Unit)? = null
) : RecyclerView.ViewHolder(itemView) {
    private var user: GithubUser? = null
    private val avatar: ImageView = itemView.findViewById(R.id.avatar)
    private val username: TextView = itemView.findViewById(R.id.username)
    private val details: TextView = itemView.findViewById(R.id.details)
    private val noteIcon: ImageView = itemView.findViewById(R.id.noteIcon)

    // Negative matrix color
    private val negative = floatArrayOf(
        -1.0f, .0f, .0f, .0f, 255.0f,
        .0f, -1.0f, .0f, .0f, 255.0f,
        .0f, .0f, -1.0f, .0f, 255.0f,
        .0f, .0f, .0f, 1.0f, .0f
    )

    fun bind(user: GithubUser, invertAvatarColors: Boolean = false) {
        this.user = user

        username.text = user.login
        avatar.load(user.avatarUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        if (invertAvatarColors) {
            avatar.colorFilter = ColorMatrixColorFilter(negative)
        } else {
            avatar.colorFilter = null
        }


        if (user.notes != null) {
            noteIcon.visibility = View.VISIBLE
        } else {
            noteIcon.visibility = View.GONE
        }

        itemView.setOnClickListener {
            itemClick?.invoke(user)
        }
    }

}