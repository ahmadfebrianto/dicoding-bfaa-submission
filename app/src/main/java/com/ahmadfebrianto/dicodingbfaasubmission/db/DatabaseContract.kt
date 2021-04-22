package com.ahmadfebrianto.dicodingbfaasubmission.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.ahmadfebrianto.dicodingbfaasubmission"
    const val SCHEME = "content"

    class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_users"
            const val USER_AVATAR_URL = "user_avatar_url"
            const val _USER_ID = "user_id"
            const val USERNAME = "username"
            const val USER_PROFILE_URL = "user_profile_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}