package com.ahmadfebrianto.dicodingbfaasubmission.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "github_users"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_FAV_USERS = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.NoteColumns._USER_ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.NoteColumns.USER_AVATAR_URL} TEXT NOT NULL," +
                " ${DatabaseContract.NoteColumns.USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.NoteColumns.USER_PROFILE_URL} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAV_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}