package com.compultra.silent.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.compultra.silent.ui.common.ListGroupDecoration
import com.compultra.silent.ui.common.ListSingleHeaderDecoration
import com.compultra.silent.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    companion object {
        fun newInstance() = AddFragment()
    }

    val viewModel by activityViewModels<AddViewModel>()

    private lateinit var binding: FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AddItemAdapter()
        binding.addList.adapter = adapter
        adapter.submitList(AddDataSource().load())

        binding.addList.addItemDecoration(ListGroupDecoration(requireContext()))
        binding.addList.addItemDecoration(ListSingleHeaderDecoration(requireContext(), "Add"))
    }
}