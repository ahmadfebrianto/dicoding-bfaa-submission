package com.ahmadfebrianto.dicodingbfaasubmission.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.AUTHORITY
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.ahmadfebrianto.dicodingbfaasubmission.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.ahmadfebrianto.dicodingbfaasubmission.db.FavoriteUserHelper

class FavUserProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private lateinit var favUserHelper: FavoriteUserHelper
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USER_ID)
        }
    }

    override fun onCreate(): Boolean {
        favUserHelper = FavoriteUserHelper.getInstance(context as Context)
        favUserHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER -> favUserHelper.queryAll()
            USER_ID -> favUserHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USER) {
            uriMatcher.match(uri) -> favUserHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when (USER_ID) {
            uriMatcher.match(uri) -> favUserHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USER_ID) {
            uriMatcher.match(uri) -> favUserHelper.deleteById(uri.lastPathSegment!!.toInt())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}