package com.example.tawkpracticaltest.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_user_profile")
data class GithubUserProfile(
    @ColumnInfo(name = "login")
    @SerializedName("login")
    val login: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "node_id", defaultValue = "")
    @SerializedName("node_id")
    val nodeId: String?,

    @ColumnInfo(name = "avatar_url", defaultValue = "")
    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @ColumnInfo(name = "gravatar_id", defaultValue = "")
    @SerializedName("gravatar_id")
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

    @ColumnInfo(name = "received_events_url", defaultValue = "")
    @SerializedName("received_events_url")
    val receivedEventsUrl: String?,

    @ColumnInfo(name = "type", defaultValue = "")
    @SerializedName("type")
    val type: String?,

    @ColumnInfo(name = "site_admin")
    @SerializedName("site_admin")
    val siteAdmin: Boolean,

    @ColumnInfo(name = "name", defaultValue = "")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "company", defaultValue = "")
    @SerializedName("company")
    val company: String?,

    @ColumnInfo(name = "blog", defaultValue = "")
    @SerializedName("blog")
    val blog: String?,

    @ColumnInfo(name = "location", defaultValue = "")
    @SerializedName("location")
    val location: String?,

    @ColumnInfo(name = "email", defaultValue = "")
    @SerializedName("email")
    val email: String?,

    @ColumnInfo(name = "hireable", defaultValue = "")
    @SerializedName("hireable")
    val hireable: Boolean?,

    @ColumnInfo(name = "bio", defaultValue = "")
    @SerializedName("bio")
    val bio: String?,

    @ColumnInfo(name = "twitter_username", defaultValue = "")
    @SerializedName("twitter_username")
    val twitterUsername: String?,

    @ColumnInfo(name = "public_repos")
    @SerializedName("public_repos")
    val publicRepos: Int,

    @ColumnInfo(name = "public_gists")
    @SerializedName("public_gists")
    val publicGists: Int,

    @ColumnInfo(name = "followers")
    @SerializedName("followers")
    val followers: Long,

    @ColumnInfo(name = "following")
    @SerializedName("following")
    val following: Long,

    @ColumnInfo(name = "created_at", defaultValue = "")
    @SerializedName("created_at")
    val createdAt: String,

    @ColumnInfo(name = "updated_at", defaultValue = "")
    @SerializedName("updated_at")
    val updatedAt: String?
)