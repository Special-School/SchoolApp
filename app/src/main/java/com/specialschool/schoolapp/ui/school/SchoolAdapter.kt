package com.specialschool.schoolapp.ui.school

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.specialschool.schoolapp.databinding.SearchSchoolItemBinding
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.ui.event.EventActions

class SchoolAdapter(
    private val eventListener: EventActions,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<UserItem, SchoolViewHolder>(SchoolDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding = SearchSchoolItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SchoolViewHolder(binding, eventListener, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SchoolViewHolder(
    private val binding: SearchSchoolItemBinding,
    private val eventListener: EventActions,
    private val lifecycleOwner: LifecycleOwner
) : ViewHolder(binding.root) {

    fun bind(userItem: UserItem) {
        binding.userItem = userItem
        binding.eventListener = eventListener
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}

object SchoolDiff : DiffUtil.ItemCallback<UserItem>() {

    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.school.id == newItem.school.id
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem == newItem
    }
}
