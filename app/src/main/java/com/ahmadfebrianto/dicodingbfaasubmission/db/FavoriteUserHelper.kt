package com.ahmadfebrianto.dicodingbfaasubmission.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion._USER_ID
import java.sql.SQLException

class FavoriteUserHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private var INSTANCE: FavoriteUserHelper? = null
        fun getInstance(context: Context): FavoriteUserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteUserHelper((context))
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$_USER_ID DESC",
            null
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            TABLE_NAME, null, "$_USER_ID=$id", null,
            null, null, null, null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(TABLE_NAME, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(TABLE_NAME, values, "$_USER_ID = ?", arrayOf(id))
    }

    fun deleteById(id: Int): Int {
        return database.delete(TABLE_NAME, "$_USER_ID=$id", null)
    }
}