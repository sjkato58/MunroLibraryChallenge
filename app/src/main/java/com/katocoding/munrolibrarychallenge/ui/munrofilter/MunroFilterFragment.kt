package com.katocoding.munrolibrarychallenge.ui.munrofilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.katocoding.munrolibrarychallenge.databinding.FragmentMunrofilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MunroFilterFragment: Fragment() {

    private val viewModel: MunroFilterViewModel by viewModels()

    private val args: MunroFilterFragmentArgs by navArgs()

    private var _binding: FragmentMunrofilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMunrofilterBinding.inflate(inflater, container, false)
        initViews()
        initObservers()
        return binding.root
    }

    private fun initViews() {

    }

    private fun initObservers() {
        viewModel.filterModel.observe(viewLifecycleOwner) { response ->
            Log.e("seiji", "response: $response")
        }
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it(findNavController())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateFilterModel(args.filtermodelstring)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}