package com.compultra.silent.ui.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.compultra.silent.data.Contact
import com.compultra.silent.databinding.AddItemBinding

class AddItemAdapter(val onItemAdd: (Contact)->Unit) : ListAdapter<Contact, AddItemAdapter.AddItemViewHolder>(DiffCallback) {

    class AddItemViewHolder(private val binding: AddItemBinding, val onAdd: (Int)->Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init{
            binding.apply {
                buttonRequest.setOnClickListener {
                    onAdd(adapterPosition)
                    buttonRequest.isEnabled = false
                    buttonRequest.visibility = View.GONE
                }
                root.setOnClickListener {
                    buttonRequest.visibility =
                        if (buttonRequest.visibility == View.VISIBLE) View.GONE else View.VISIBLE

                }
            }
        }
        fun bind(data: Contact) {
            binding.apply {
                initials.text = data.initials
                name.text = data.name
                number.text = data.address
                buttonRequest.isEnabled = true

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        return AddItemViewHolder(
            AddItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), {
                onItemAdd(getItem(it))
                removeItem(it)
            }
        )
    }

    fun removeItem(position: Int) {
        submitList(currentList.toMutableList().apply { removeAt(position) })
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