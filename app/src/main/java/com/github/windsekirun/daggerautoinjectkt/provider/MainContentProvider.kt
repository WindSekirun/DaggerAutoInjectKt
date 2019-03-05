package com.github.windsekirun.daggerautoinjectkt.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri


import com.github.windsekirun.daggerautoinjectkt.InjectContentProvider
import dagger.android.AndroidInjection

/**
 * Created by pyxis on 18. 4. 25.
 */

@InjectContentProvider
class MainContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        AndroidInjection.inject(this)
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}
