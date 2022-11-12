package com.compultra.silent.ui.messages

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import com.compultra.silent.MYTAG
import com.compultra.silent.databinding.FragmentMessagesBinding

class MessagesFragment : Fragment() {


    private val viewModel by viewModels<MessagesViewModel> {
        SavedStateViewModelFactory(application = activity?.application, owner = this)
    }
    lateinit var binding: FragmentMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MessagesItemAdapter()
        binding.apply {
            messagesList.adapter = adapter
            address.text = viewModel.address
            name.text = viewModel.name
            initials.text = viewModel.initials
            compose.addTextChangedListener { viewModel.setMessage(it.toString()) }
            send.setOnClickListener {
                Toast.makeText(context, "Sending message", Toast.LENGTH_LONG).show()
                viewModel.send()
                compose.setText("")
            }
            buttonBack.setOnClickListener { findNavController().popBackStack() }
        }
        viewModel.messages.observe(viewLifecycleOwner) {
            Log.d(MYTAG, it.toString())
            adapter.submitList(it) }
        viewModel.sendEnabled.observe(viewLifecycleOwner) { binding.send.isEnabled = it }

        TextMessageBroadcastReceiver.setAddress(viewModel.address)
    }


    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(TextMessageBroadcastReceiver)
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(TextMessageBroadcastReceiver, TextMessageBroadcastReceiver.intentFilter)
    }

    companion object {
        const val ARG_ADDRESS = "arg_address"
    }
}