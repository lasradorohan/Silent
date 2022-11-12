package com.compultra.silent.ui.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.compultra.silent.data.LatestTextMessage
import com.compultra.silent.databinding.ChatItemBinding

class ChatItemAdapter(val onItemClick: (LatestTextMessage) -> Unit = {}) :
    ListAdapter<LatestTextMessage, ChatItemAdapter.ChatItemViewHolder>(DiffCallback) {

    class ChatItemViewHolder(
        private val binding: ChatItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { onClick(adapterPosition) }
        }

        fun bind(data: LatestTextMessage) {
            binding.apply {
                initials.text = data.initials
                name.text = data.name
                message.text = data.message
                timestamp.text = data.timestampFormated
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        return ChatItemViewHolder(
            ChatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), {
                onItemClick(getItem(it))
            }
        )
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<LatestTextMessage>() {
        override fun areItemsTheSame(oldItem: LatestTextMessage, newItem: LatestTextMessage): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: LatestTextMessage, newItem: LatestTextMessage): Boolean {
            return oldItem.initials == newItem.initials
                    && oldItem.name == newItem.name
                    && oldItem.message == newItem.message
                    && oldItem.timestamp == newItem.timestamp
        }

    }

}

fun ChatItemBinding.setOnClick(data: LatestTextMessage, onClick: (LatestTextMessage) -> Unit) {
    root.setOnClickListener { onClick(data) }
}