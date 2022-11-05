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
class RequestsItemAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val incomingList: MutableList<RequestIncomingDataModel> = mutableListOf()
    val outgoingList: MutableList<RequestOutgoingDataModel> = mutableListOf()

    class IncomingRequestViewHolder(private val binding: RequestListitemAcceptBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RequestIncomingDataModel) {
            binding.apply {
                name.text = data.name
                initials.text = data.initials
                date.text = binding.root.context.resources.getString(R.string.requested_date, data.timestampFormatted)
            }
        }
    }
    class OutgoingRequestViewHolder(private val binding: RequestListitemWithdrawBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RequestOutgoingDataModel) {
            binding.apply {
                name.text = data.name
                initials.text = data.initials
                date.text = binding.root.context.resources.getString(R.string.requested_date, data.timestampFormatted)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when(viewType) {
            ItemType.INCOMING.ordinal -> return IncomingRequestViewHolder(RequestListitemAcceptBinding.inflate(inflater, parent, false))
            ItemType.OUTGOING.ordinal -> return OutgoingRequestViewHolder(RequestListitemWithdrawBinding.inflate(inflater, parent, false))
        }
        throw IllegalStateException("Unkown ItemType $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return if(position<incomingList.size) ItemType.INCOMING.ordinal else ItemType.OUTGOING.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is IncomingRequestViewHolder -> holder.bind(getItem(position) as RequestIncomingDataModel)
            is OutgoingRequestViewHolder -> holder.bind(getItem(position) as RequestOutgoingDataModel)
        }

    }
    fun getItem(position: Int): RequestsDataModel {
        return if(position<incomingList.size) incomingList[position]
        else outgoingList[position-incomingList.size]
    }

    override fun getItemCount() = incomingList.size + outgoingList.size

    @SuppressLint("NotifyDataSetChanged")
    fun reloadLists(incomingList: List<RequestIncomingDataModel>, outgoingList: List<RequestOutgoingDataModel>) {
        this.incomingList.clear()
        this.outgoingList.clear()
        this.incomingList.addAll(incomingList)
        this.outgoingList.addAll(outgoingList)
        refreshHeaders()
        notifyDataSetChanged()
    }

    private var _headers = mutableMapOf<Int,String>()
    val headers: Map<Int,String> get() = _headers

    fun refreshHeaders() {
        _headers.clear()
        if(incomingList.size>0) _headers[0] = "Requests"
        if(outgoingList.size>0) _headers[incomingList.size] = "Requested"
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