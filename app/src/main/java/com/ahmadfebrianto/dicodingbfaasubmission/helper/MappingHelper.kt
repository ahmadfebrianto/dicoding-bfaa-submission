package com.ahmadfebrianto.dicodingbfaasubmission.helper

import android.database.Cursor
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import java.util.ArrayList

object MappingHelper {
    fun mapCursorToArrayList(favUserCursor: Cursor): ArrayList<User> {
        val favUserList = ArrayList<User>()
        favUserCursor.apply {
            while (moveToNext()) {
                val user = User()
                user.userId = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._USER_ID))
                user.avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.USER_AVATAR_URL))
                user.username = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.USERNAME))
                user.profileUrl = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.USER_PROFILE_URL))
                favUserList.add(user)
            }
        }
        return favUserList
    }
}