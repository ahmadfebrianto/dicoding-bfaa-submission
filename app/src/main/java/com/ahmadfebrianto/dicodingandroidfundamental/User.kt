package com.ahmadfebrianto.dicodingandroidfundamental

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    var profile: Int? = 0,
    var userName: String? = "",
    var fullName: String? = "",
    var location: String? = "",
    var company: String? = "",
    var repository: Int? = 0,
    var follower: Int? = 0,
    var following: Int? = 0
): Parcelable
