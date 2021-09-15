package com.lanedever.placebook.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.lanedever.placebook.db.BookmarkDao
import com.lanedever.placebook.db.PlaceBookDatabase
import com.lanedever.placebook.model.Bookmark

class BookmarkRepo(context: Context) {

    private val db = PlaceBookDatabase.getInstance(context)
    private val bookmarkDao: BookmarkDao = db.bookmarkDao()

    fun addBookmark(bookmark: Bookmark): Long? {
        val newId = bookmarkDao.insertBookmark(bookmark)
        bookmark.id = newId
        return newId
    }

    fun createBookmark(): Bookmark {
        return Bookmark()
    }

    val allBookmarks: LiveData<List<Bookmark>>
        get() {
            return bookmarkDao.loadAll()
        }

    fun getLiveBookmark(bookmarkId: Long): LiveData<Bookmark> =
        bookmarkDao.loadLiveBookmark(bookmarkId)
}