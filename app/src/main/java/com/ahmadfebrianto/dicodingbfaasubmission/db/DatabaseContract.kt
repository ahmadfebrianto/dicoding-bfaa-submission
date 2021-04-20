package com.ahmadfebrianto.dicodingbfaasubmission.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_users"
            const val USER_AVATAR_URL = "user_avatar_url"
            const val USER_ID = "user_id"
            const val USERNAME = "username"
            const val USER_PROFILE_URL = "user_profile_url"
        }
    }
}