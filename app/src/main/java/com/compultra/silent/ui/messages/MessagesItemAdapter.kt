package com.compultra.silent.ui.messages

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.compultra.silent.MYTAG
import com.compultra.silent.data.MessageType
import com.compultra.silent.data.SilentMessage
import com.compultra.silent.data.formattedTime
import com.compultra.silent.databinding.MessageItemBinding

class MessagesItemAdapter :
    ListAdapter<SilentMessage.Text, MessagesItemAdapter.MessageItemViewHolder>(DiffCallback) {

    class MessageItemViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SilentMessage.Text) {
            Log.d(MYTAG, "attempting to bind data: $data")
            binding.apply {
                date.text = data.formattedTime
                message.text = data.message
                content.applyGravity(
                    when (data.type) {
                        MessageType.INBOX -> Gravity.START
                        MessageType.SENT -> Gravity.END
                        MessageType.OUTBOX -> Gravity.END
                        else -> return
                    }
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItemViewHolder {
        return MessageItemViewHolder(
            MessageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageItemViewHolder, position: Int) {
        Log.d(MYTAG, "attempting to bind position: $position")

        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SilentMessage.Text>() {
        override fun areItemsTheSame(oldItem: SilentMessage.Text, newItem: SilentMessage.Text): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SilentMessage.Text, newItem: SilentMessage.Text): Boolean {
            return oldItem.type == newItem.type
                    && oldItem.timestamp == newItem.timestamp
                    && oldItem.address == newItem.address
                    && oldItem.message == newItem.message

        }

    }

}


fun LinearLayout.applyGravity(newGravity: Int) {
    layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        gravity = newGravity
    }
}