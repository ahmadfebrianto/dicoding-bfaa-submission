package com.ahmadfebrianto.dicodingbfaasubmission.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var avatarUrl: String? = null,
    var name: String? = null,
    var username: String? = null,
    var userId: Int = 0,
    var profileUrl: String? = null,
    var company: String? = null,
    var location: String? = null,
    var bio: String? = null,
    var followers: Int = 0,
    var following: Int = 0,
    var repository: Int = 0,
) : Parcelable
