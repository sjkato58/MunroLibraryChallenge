package com.katocoding.munrolibrarychallenge.ui.munrolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.katocoding.munrolibrarychallenge.KEY_UPDATE_MUNRO_FILTER
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FilterModel
import com.katocoding.munrolibrarychallenge.databinding.FragmentMunrolistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MunroListFragment: Fragment() {

    private val viewModel: MunroListViewModel by viewModels()

    private var _binding: FragmentMunrolistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMunrolistBinding.inflate(inflater, container, false)
        initViews()
        initObservers()
        return binding.root
    }

    fun initViews() {
        binding.tvToolbarFilter.setOnClickListener { viewModel.navigateToMunroFilter() }
        binding.rvMunrolist.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMunrolist.adapter = MunroListAdapter()
    }

    fun initObservers() {
        viewModel.munroList.observe(viewLifecycleOwner) { responseList ->
            when {
                responseList[0].showError -> {

                }
                else -> {
                    (binding.rvMunrolist.adapter as MunroListAdapter).updateDataList(responseList)
                }
            }
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            KEY_UPDATE_MUNRO_FILTER)?.observe(viewLifecycleOwner) {

            findNavController().currentBackStackEntry?.savedStateHandle?.remove<Boolean>(KEY_UPDATE_MUNRO_FILTER)
        }
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it(findNavController())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMunroList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}