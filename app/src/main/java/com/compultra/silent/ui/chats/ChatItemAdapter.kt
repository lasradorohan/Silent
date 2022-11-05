package com.compultra.silent.ui.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.compultra.silent.databinding.ChatListitemBinding

class ChatItemAdapter(val onItemClick: (ChatsDataModel) -> Unit = {}) :
    ListAdapter<ChatsDataModel, ChatItemAdapter.ChatItemViewHolder>(DiffCallback) {

    class ChatItemViewHolder(
        private val binding: ChatListitemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { onClick(adapterPosition) }
        }

        fun bind(data: ChatsDataModel) {
            binding.apply {
                initials.text = data.initials
                name.text = data.name
                message.text = data.message
                timestamp.text = data.timestampFormatted
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        return ChatItemViewHolder(
            ChatListitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            onItemClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatsDataModel>() {
        override fun areItemsTheSame(oldItem: ChatsDataModel, newItem: ChatsDataModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatsDataModel, newItem: ChatsDataModel): Boolean {
            return oldItem.initials == newItem.initials
                    && oldItem.name == newItem.name
                    && oldItem.message == newItem.message
                    && oldItem.timestamp == newItem.timestamp
        }

    }

}

fun ChatListitemBinding.setOnClick(data: ChatsDataModel, onClick: (ChatsDataModel) -> Unit) {
    root.setOnClickListener { onClick(data) }
}