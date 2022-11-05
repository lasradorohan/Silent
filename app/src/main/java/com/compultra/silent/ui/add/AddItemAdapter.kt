package com.compultra.silent.ui.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.compultra.silent.data.Contact
import com.compultra.silent.databinding.AddListitemBinding

class AddItemAdapter: ListAdapter<Contact, AddItemAdapter.AddItemViewHolder>(DiffCallback) {

    class AddItemViewHolder(private val binding: AddListitemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Contact){
            binding.apply {
                initials.text = data.initials
                name.text = data.name
                number.text = data.address
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        return AddItemViewHolder(AddListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.initials == newItem.initials
                    && oldItem.name == newItem.name
                    && oldItem.address == newItem.address
        }

    }

}