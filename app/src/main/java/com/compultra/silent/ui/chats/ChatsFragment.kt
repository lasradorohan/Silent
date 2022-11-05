package com.compultra.silent.ui.chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.compultra.silent.ui.common.ListGroupDecoration
import com.compultra.silent.ui.common.ListSingleHeaderDecoration
import com.compultra.silent.databinding.FragmentChatsBinding
import com.compultra.silent.R

class ChatsFragment : Fragment() {

    companion object {
        fun newInstance() = ChatsFragment()
    }

    val viewModel by activityViewModels<ChatsViewModel>()
    lateinit var binding: FragmentChatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ChatItemAdapter {
//            val action = ChatsFragmentDirections.actionFirstFragmentToMessagesFragment()

//            Toast.makeText(context, it.address, Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.messages_fragment)
        }
        binding.chatsList.adapter = adapter
        adapter.submitList(ChatsDataSource().load())


        binding.chatsList.addItemDecoration(ListSingleHeaderDecoration(requireContext(), "Chats"))
        binding.chatsList.addItemDecoration(ListGroupDecoration(requireContext()))

    }
}