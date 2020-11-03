package com.example.tawkpracticaltest.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "github_user")
data class GithubUser(
    @ColumnInfo(name = "login")
    @SerializedName("login")
    val login: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "node_id")
    @SerializedName("node_id")
    val nodeId: String,

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "gravatar_url", defaultValue = "")
    @SerializedName("gravatar_url")
    val gravatarId: String?,

    @ColumnInfo(name = "url", defaultValue = "")
    @SerializedName("url")
    val url: String?,

    @ColumnInfo(name = "html_url", defaultValue = "")
    @SerializedName("html_url")
    val htmlUrl: String?,

    @ColumnInfo(name = "followers_url", defaultValue = "")
    @SerializedName("followers_url")
    val followersUrl: String?,

    @ColumnInfo(name = "following_url", defaultValue = "")
    @SerializedName("following_url")
    val followingUrl: String?,

    @ColumnInfo(name = "gists_url", defaultValue = "")
    @SerializedName("gists_url")
    val gistsUrl: String?,

    @ColumnInfo(name = "starred_url", defaultValue = "")
    @SerializedName("starred_url")
    val starredUrl: String?,

    @ColumnInfo(name = "subscriptions_url", defaultValue = "")
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String?,

    @ColumnInfo(name = "organizations_url", defaultValue = "")
    @SerializedName("organizations_url")
    val organizationsUrl: String?,

    @ColumnInfo(name = "repos_url", defaultValue = "")
    @SerializedName("repos_url")
    val reposUrl: String?,

    @ColumnInfo(name = "events_url", defaultValue = "")
    @SerializedName("events_url")
    val eventsUrl: String?,

    @ColumnInfo(name = "received_events_url", defaultValue = "")
    @SerializedName("received_events_url")
    val receivedEventsUrl: String?,

    @ColumnInfo(name = "type", defaultValue = "")
    @SerializedName("type")
    val type: String?,

    @ColumnInfo(name = "site_admin")
    @SerializedName("site_admin")
    val siteAdmin: Boolean,

    @ColumnInfo(name = "notes", defaultValue = "")
    var notes: String? = ""
) : Parcelable