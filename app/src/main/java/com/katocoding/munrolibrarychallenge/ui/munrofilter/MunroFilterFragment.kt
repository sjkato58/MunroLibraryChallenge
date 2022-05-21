package com.katocoding.munrolibrarychallenge.ui.munrofilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE
import com.katocoding.munrolibrarychallenge.KEY_UPDATE_MUNRO_FILTER
import com.katocoding.munrolibrarychallenge.R
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType
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

        binding.ivToolbarBack.setOnClickListener { closingFilterFragment(false) }
        binding.tvToolbarDone.setOnClickListener { closingFilterFragment(true) }
        val list = requireContext().resources.getStringArray(R.array.sa_hillcategory)
        binding.spvHillcategory.adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_simple, list)
        val sortList = requireContext().resources.getStringArray(R.array.sa_sorttype)
        binding.spvSortHeight.adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_simple, sortList)
        binding.spvSortAlphabetic.adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_simple, sortList)
        val sortMaxList = requireContext().resources.getStringArray(R.array.sa_maxsort)
        binding.spvFilterSortmax.adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_simple, sortMaxList)
    }

    fun closingFilterFragment(wasDoneClick: Boolean) {
        if (wasDoneClick)  {
            val hillCategory = binding.spvHillcategory.selectedItem.toString()
            val sortHeightMType = binding.spvSortHeight.selectedItem.toString()
            val sortAlphabetType = binding.spvSortAlphabetic.selectedItem.toString()
            val sortLimit = binding.spvFilterSortmax.selectedItem.toString()
            val maxHeight = DEFAULT_DOUBLE
            val minHeight = DEFAULT_DOUBLE

            viewModel.updateFilterModel(hillCategory, sortHeightMType, sortAlphabetType, sortLimit, maxHeight, minHeight)

            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                KEY_UPDATE_MUNRO_FILTER, viewModel.filterModel.value?.convertToString())
        }
        findNavController().popBackStack()
    }

    private fun initObservers() {
        viewModel.filterModel.observe(viewLifecycleOwner) { response ->

            val list = requireContext().resources.getStringArray(R.array.sa_hillcategory)
            binding.spvHillcategory.setSelection(list.indexOf(response.hillCategory.label))

            val sortList = requireContext().resources.getStringArray(R.array.sa_sorttype)
            binding.spvSortHeight.setSelection(sortList.indexOf(response.sortHeightMType.label))
            binding.spvSortAlphabetic.setSelection(sortList.indexOf(response.sortAlphabetType.label))

            val sortMaxList = requireContext().resources.getStringArray(R.array.sa_maxsort)
            binding.spvFilterSortmax.setSelection(sortMaxList.indexOf(response.sortLimit.toString()))
        }
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it(findNavController())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initFilterModel(args.filtermodelstring)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}