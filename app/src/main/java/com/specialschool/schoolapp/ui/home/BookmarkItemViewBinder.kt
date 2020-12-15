package com.specialschool.schoolapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.specialschool.schoolapp.R
import com.specialschool.schoolapp.databinding.ItemHomeBookmarkBinding
import com.specialschool.schoolapp.model.UserItem

object BookmarkItemHeader

class HeaderViewHolder(itemView: View) : ViewHolder(itemView)

class BookmarkItemHeaderViewBinder : HomeItemViewBinder<BookmarkItemHeader, HeaderViewHolder>(
    BookmarkItemHeader::class.java
) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return HeaderViewHolder(
            LayoutInflater.from(parent.context).inflate(getHomeItemType(), parent, false)
        )
    }

    override fun bindViewHolder(model: BookmarkItemHeader, viewHolder: HeaderViewHolder) {}

    override fun getHomeItemType(): Int = R.layout.item_home_bookmarks_header

    override fun areItemsTheSame(
        oldItem: BookmarkItemHeader,
        newItem: BookmarkItemHeader
    ): Boolean = true

    override fun areContentsTheSame(
        oldItem: BookmarkItemHeader,
        newItem: BookmarkItemHeader
    ): Boolean = true
}

class BookmarkItemViewBinder(
    private val lifecycleOwner: LifecycleOwner,
    private val eventListener: HomeItemEventListener
) : HomeItemViewBinder<UserItem, BookmarkItemViewHolder>(UserItem::class.java) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder =
        BookmarkItemViewHolder(
            ItemHomeBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            eventListener,
            lifecycleOwner
        )

    override fun bindViewHolder(model: UserItem, viewHolder: BookmarkItemViewHolder) {
        viewHolder.bind(model)
    }

    override fun getHomeItemType(): Int = R.layout.item_home_bookmark

    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
        oldItem.school.id == newItem.school.id

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
        oldItem == newItem

}

class BookmarkItemViewHolder(
    private val binding: ItemHomeBookmarkBinding,
    private val eventListener: HomeItemEventListener,
    private val lifecycleOwner: LifecycleOwner
) : ViewHolder(binding.root) {

    fun bind(userItem: UserItem) {
        binding.item = userItem
        binding.listener = eventListener
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}

object BookmarkItemEmpty

class EmptyViewHolder(itemView: View) : ViewHolder(itemView)

class BookmarkItemEmptyViewBinder : HomeItemViewBinder<BookmarkItemEmpty, EmptyViewHolder>(
    BookmarkItemEmpty::class.java
) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return EmptyViewHolder(
            LayoutInflater.from(parent.context).inflate(getHomeItemType(), parent, false)
        )
    }

    override fun bindViewHolder(model: BookmarkItemEmpty, viewHolder: EmptyViewHolder) {}

    override fun getHomeItemType(): Int = R.layout.item_home_bookmarks_empty

    override fun areItemsTheSame(
        oldItem: BookmarkItemEmpty,
        newItem: BookmarkItemEmpty
    ): Boolean = true

    override fun areContentsTheSame(
        oldItem: BookmarkItemEmpty,
        newItem: BookmarkItemEmpty
    ): Boolean = true
}
