package com.ahmadfebrianto.dicodingandroidfundamental

data class User(
    var userName: String = "",
    var fullName: String = "",
    var location: String = "",
    var repository: Int = 0,
    var company: String = "",
    var follower: Int = 0,
    var following: Int = 0,
    var profile: Int = 0
)
