package com.ahmadfebrianto.dicodingbfaasubmission.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.USERNAME
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.USER_AVATAR_URL
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.USER_ID
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.USER_PROFILE_URL
import com.ahmadfebrianto.dicodingbfaasubmission.model.User
import java.sql.SQLException

class FavoriteUserHelper (context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private var INSTANCE: FavoriteUserHelper? = null
        fun getInstance(context: Context): FavoriteUserHelper =
            INSTANCE?: synchronized(this) {
                INSTANCE?: FavoriteUserHelper((context))
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen){
            database.close()
        }
    }

    fun checkFavUserById(id: Int): Int {
        val cursor = database.rawQuery("select * from $TABLE_NAME where $USER_ID=$id", null)
        val num = cursor.count
        cursor.close()
        return num
    }

    fun getAllUsers(): ArrayList<User> {
        val favUserList = ArrayList<User>()
        val cursor = database.query(
            TABLE_NAME, null, null, null, null, null,
            "$USER_ID ASC", null)
        cursor.moveToFirst()
        var user: User
        if (cursor.count > 0) {
            do {
                user = User()
                user.userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID))
                user.avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(USER_AVATAR_URL))
                user.username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME))
                user.profileUrl = cursor.getString(cursor.getColumnIndexOrThrow(USER_PROFILE_URL))

                favUserList.add(user)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return favUserList
    }

    fun insertFavUser(user: User): Long {
        val args = ContentValues()
        args.put(USER_ID, user.userId)
        args.put(USERNAME, user.username)
        args.put(USER_AVATAR_URL, user.avatarUrl)
        args.put(USER_PROFILE_URL, user.profileUrl)
        return database.insert(TABLE_NAME, null, args)
    }

    fun deleteFavUser(id: Int): Int {
        return database.delete(TABLE_NAME, "$USER_ID=$id", null)
    }
}