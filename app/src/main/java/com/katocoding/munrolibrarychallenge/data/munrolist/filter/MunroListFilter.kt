package com.katocoding.munrolibrarychallenge.data.munrolist.filter

import com.katocoding.munrolibrarychallenge.data.munrolist.MunroModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MunroListFilter @Inject constructor(

) {

    fun checkFilterData(
        filterModel: FilterModel,
        records: List<MunroModel>
    ): List<MunroModel> {

        return records
    }
}