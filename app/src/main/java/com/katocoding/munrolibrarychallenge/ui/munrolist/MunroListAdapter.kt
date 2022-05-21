package com.katocoding.munrolibrarychallenge.ui.munrolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katocoding.munrolibrarychallenge.R
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType
import com.katocoding.munrolibrarychallenge.databinding.ItemMunrolistBinding

class MunroListAdapter: RecyclerView.Adapter<MunroListAdapter.MunroListItemViewHolder>() {

    private var dataList = mutableListOf<MunroListViewState>()

    fun updateDataList(newList: List<MunroListViewState>) {
        dataList = mutableListOf()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MunroListItemViewHolder {
        return MunroListItemViewHolder(ItemMunrolistBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MunroListItemViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    class MunroListItemViewHolder constructor(
        private val binding: ItemMunrolistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(
            data: MunroListViewState
        ) {
            val context = binding.root.context
            binding.tvNameDescription.text = data.name
            binding.tvHeightDescription.text = data.heightM.toString()
            binding.tvHillcategoryDescription.text = context.resources.getString(obtainHillCategoryString(data.hillCategory))
            binding.tvGridreferenceDescription.text = data.gridReference
        }

        fun obtainHillCategoryString(hillCategoryType: HillCategoryType) =
            if (hillCategoryType == HillCategoryType.MunroTop) R.string.d_munrotop else R.string.d_munro
    }
}