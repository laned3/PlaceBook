package com.lanedever.placebook.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lanedever.placebook.model.Bookmark
import com.lanedever.placebook.repository.BookmarkRepo
import com.lanedever.placebook.util.ImageUtils

class BookmarkDetailsViewModel(application: Application) :
    AndroidViewModel(application) {

    private val bookmarkRepo = BookmarkRepo(getApplication())
    private var bookmarkDetailsView: LiveData<BookmarkDetailsView>? = null

    private fun bookmarkToBookmarkView(bookmark: Bookmark): BookmarkDetailsView {
        return BookmarkDetailsView(
            bookmark.id,
            bookmark.name,
            bookmark.phone,
            bookmark.address,
            bookmark.notes
        )
    }

    private fun mapBookmarkToBookmarkView(bookmarkId: Long) {
        val bookmark = bookmarkRepo.getLiveBookmark(bookmarkId)
        bookmarkDetailsView = Transformations.map(bookmark)
        { repoBookmark ->
            bookmarkToBookmarkView(repoBookmark)
        }
    }

    fun getBookmark(bookmarkId: Long):
            LiveData<BookmarkDetailsView>? {
        if (bookmarkDetailsView == null) {
            mapBookmarkToBookmarkView(bookmarkId)
        }
        return bookmarkDetailsView
    }



}

data class BookmarkDetailsView(
    var id: Long? = null,
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    var notes: String = ""
) {
    fun getImage(context: Context) = id?.let {
        ImageUtils.loadBitmapFromFile(context,
            Bookmark.generateImageFilename(it))
    }
}

