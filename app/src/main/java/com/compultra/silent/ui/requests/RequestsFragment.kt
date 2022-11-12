package com.compultra.silent.ui.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.compultra.silent.ui.common.ListGroupDecoration
import com.compultra.silent.ui.common.ListMultiHeaderDecoration
import com.compultra.silent.databinding.FragmentRequestsBinding

class RequestsFragment : Fragment() {

    companion object {
        fun newInstance() = RequestsFragment()
    }

    private val viewModel by activityViewModels<RequestsViewModel>()
    private lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestsBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshMessages()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RequestsItemAdapter(
            onIncomingItemClick = {
                viewModel.sendConnection(it.address)
            },
            onOutgoingItemClick = {} //Unused
        )
        binding.requestsList.adapter = adapter
//        val dataSource = RequestsDataSource()
//        adapter.reloadLists(dataSource.loadIncoming(), dataSource.loadOutgoing())

        viewModel.incoming.observe(viewLifecycleOwner) {
            adapter.reloadList(it)
            binding.requestsList.invalidateItemDecorations()
        }
        viewModel.outgoing.observe(viewLifecycleOwner) {
            adapter.reloadList(it)
            binding.requestsList.invalidateItemDecorations()
        }

        binding.requestsList.apply {
            addItemDecoration(ListMultiHeaderDecoration(context) { adapter.headers })
            addItemDecoration(ListGroupDecoration(context) { adapter.headers.keys })
        }

    }
}