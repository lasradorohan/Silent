package com.compultra.silent.ui.requests

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.compultra.silent.R
import com.compultra.silent.databinding.RequestListitemAcceptBinding
import com.compultra.silent.databinding.RequestListitemWithdrawBinding
import java.lang.IllegalStateException

enum class ItemType {
    INCOMING, OUTGOING
}

class RequestsItemAdapter(
    val onIncomingItemClick: (RequestIncomingViewData) -> Unit = {},
    val onOutgoingItemClick: (RequestOutgoingViewData) -> Unit = {}
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val incomingList: MutableList<RequestIncomingViewData> = mutableListOf()
    val outgoingList: MutableList<RequestOutgoingViewData> = mutableListOf()

    class IncomingRequestViewHolder(private val binding: RequestListitemAcceptBinding, onClick: (Int)->Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                buttonAccept.apply {
                    setOnClickListener { onClick(adapterPosition) }
                    isEnabled = false
                }
            }
        }
        fun bind(data: RequestIncomingViewData) {
            binding.apply {
                name.text = data.name
                initials.text = data.initials
                date.text = binding.root.context.resources.getString(
                    R.string.requested_date,
                    data.timestamp
                )
                buttonAccept.isEnabled = true
            }
        }
    }

    class OutgoingRequestViewHolder(private val binding: RequestListitemWithdrawBinding, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
//        Unused:
//        init {
//            binding.apply {
//                buttonWithdraw.apply {
//                    setOnClickListener { onClick(adapterPosition) }
//                    isEnabled = false
//                }
//            }
//        }
        fun bind(data: RequestOutgoingViewData) {
            binding.apply {
                name.text = data.name
                initials.text = data.initials
                date.text = binding.root.context.resources.getString(
                    R.string.requested_date,
                    data.timestamp
                )
//                buttonWithdraw.isEnabled = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ItemType.INCOMING.ordinal -> return IncomingRequestViewHolder(
                RequestListitemAcceptBinding.inflate(inflater, parent, false),
                onClick = {
                    onIncomingItemClick(incomingList[it])
                    removeIncoming(it)
                }
            )
            ItemType.OUTGOING.ordinal -> return OutgoingRequestViewHolder(
                RequestListitemWithdrawBinding.inflate(inflater, parent, false),
                onClick = {
//                    val pos = it - incomingList.size
//                    onOutgoingItemClick(outgoingList[pos])
//                    removeOutgoing(pos)
                }
            )
        }
        throw IllegalStateException("Unkown ItemType $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < incomingList.size) ItemType.INCOMING.ordinal else ItemType.OUTGOING.ordinal
    }

    fun removeIncoming(position: Int) {
        incomingList.removeAt(position)
        notifyItemRemoved(position)
    }
    fun removeOutgoing(position: Int) {
        incomingList.removeAt(position)
        notifyItemRemoved(position + incomingList.size)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IncomingRequestViewHolder -> holder.bind(getItem(position) as RequestIncomingViewData)
            is OutgoingRequestViewHolder -> holder.bind(getItem(position) as RequestOutgoingViewData)
        }

    }

    fun getItem(position: Int): RequestsViewData {
        return if (position < incomingList.size) incomingList[position]
        else outgoingList[position - incomingList.size]
    }

    override fun getItemCount() = incomingList.size + outgoingList.size

    @SuppressLint("NotifyDataSetChanged")
    fun reloadLists(
        incoming: List<RequestIncomingViewData>,
        outgoing: List<RequestOutgoingViewData>
    ) {
        incomingList.clear()
        outgoingList.clear()
        incomingList.addAll(incoming)
        outgoingList.addAll(outgoing)
        notifyDataSetChanged()
        refreshHeaders()
    }

    @JvmName("reloadListIncoming")
    fun reloadList(incoming: List<RequestIncomingViewData>) {
        val oldLength = incomingList.size
        val newLength = incoming.size
        incomingList.clear()
        incomingList.addAll(incoming)
        notifyItemRangeRemoved(0, oldLength)
        notifyItemRangeInserted(0, newLength)
        refreshHeaders()
    }

    @JvmName("reloadListOutgoing")
    fun reloadList(outgoing: List<RequestOutgoingViewData>) {
        val incomingLength = incomingList.size
        val oldLength = outgoingList.size
        val newLength = outgoing.size
        outgoingList.clear()
        outgoingList.addAll(outgoing)
        notifyItemRangeRemoved(incomingLength, oldLength)
        notifyItemRangeInserted(incomingLength, newLength)
        refreshHeaders()
    }

    private var _headers = mutableMapOf<Int, String>()
    val headers: Map<Int, String> get() = _headers

    fun refreshHeaders() {
        _headers.clear()
        if (incomingList.size > 0) _headers[0] = "Requests"
        if (outgoingList.size > 0) _headers[incomingList.size] = "Requested"
    }
//
//    companion object DiffCallback : DiffUtil.ItemCallback<ChatsDataModel>() {
//        override fun areItemsTheSame(oldItem: ChatsDataModel, newItem: ChatsDataModel): Boolean {
//            return oldItem === newItem
//        }
//        override fun areContentsTheSame(oldItem: ChatsDataModel, newItem: ChatsDataModel): Boolean {
//            return oldItem.initials == newItem.initials
//                    && oldItem.name == newItem.name
//                    && oldItem.message == newItem.message
//                    && oldItem.timestamp == newItem.timestamp
//        }
//
//    }

}