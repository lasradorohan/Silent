package com.compultra.silent.ui.empty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.compultra.silent.R
import com.compultra.silent.databinding.FragmentEmptyBinding

class EmptyFragment : Fragment() {

    lateinit var  binding:FragmentEmptyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmptyBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }


}