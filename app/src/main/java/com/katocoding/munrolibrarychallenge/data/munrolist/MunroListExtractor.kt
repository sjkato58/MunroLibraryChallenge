package com.katocoding.munrolibrarychallenge.data.munrolist

import com.katocoding.munrolibrarychallenge.DEFAULT_INTEGER
import com.katocoding.munrolibrarychallenge.data.*
import com.katocoding.munrolibrarychallenge.util.isInt
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class MunroListExtractor{

    fun extractMunroListData(records: MutableList<MutableList<String>>): List<MunroModel> = extractMunroLists(records[0], checkStreetMap(records))

    fun extractMunroLists(
        headerList: MutableList<String>,
        records: MutableList<MutableList<String>>
    ): List<MunroModel>  {
        val namePos = getHeaderListItem(headerList, MUNRODATA_NAME)
        val heightMPos = getHeaderListItem(headerList, MUNRODATA_HEIGHTM)
        val gridRefPos = getHeaderListItem(headerList, MUNRODATA_GRIDREF)
        val hillCategoryPos = getHeaderListItem(headerList, MUNRODATA_HILLCATEGORY)
        return records.mapNotNull {
            if (it.size > hillCategoryPos && it[hillCategoryPos].isNotBlank()) {
                MunroModel(
                    name = it[namePos],
                    heightM = it[heightMPos].toDouble(),
                    gridReference = it[gridRefPos],
                    hillCategory = stringToHillCategoryType(it[hillCategoryPos])
                )
            } else null
        }
    }

    fun checkStreetMap(records: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
        val headerList = records[0]
        val runningNoPosition = getHeaderListItem(headerList, MUNRODATA_RUNNINGNO)
        val streetMapPosition = getHeaderListItem(headerList, MUNRODATA_STREETMAP)
        val geographPosition = getHeaderListItem(headerList, MUNRODATA_GEOGRAPH)
        val hillBaggingPosition = getHeaderListItem(headerList, MUNRODATA_HILLBAGGING)
        val newRecords = mutableListOf<MutableList<String>>()
        records.forEachIndexed { recordIndex, mutableList ->
            if (recordIndex > 0 && mutableList[runningNoPosition].isInt()) {
                if (isStreetMapModificationNeeded(mutableList, streetMapPosition, geographPosition, hillBaggingPosition)) {
                    val modifiedList = mutableListOf<String>()
                    var streetMapValue = ""
                    mutableList.forEachIndexed { index, value ->
                        when (index) {
                            streetMapPosition -> {
                                streetMapValue = value
                            }
                            (streetMapPosition + 1) -> {
                                modifiedList.add(streetMapValue + value)
                            }
                            else -> {
                                modifiedList.add(value)
                            }
                        }
                    }
                    newRecords.add(modifiedList)
                } else {
                    newRecords.add(mutableList)
                }
            }
        }
        return newRecords
    }

    fun getHeaderListItem(
        headerList: MutableList<String>,
        header: String
    ): Int {
        val position = headerList.indexOfFirst {
            (it == header)
        }
        return if (position != -1) {
            position
        } else {
            DEFAULT_INTEGER
        }
    }

    fun isStreetMapModificationNeeded(
        mutableList: MutableList<String>,
        streetMapPosition: Int,
        geographPosition: Int,
        hillBaggingPosition: Int
    ) = ((streetMapPosition + 1 == geographPosition || streetMapPosition + 1 == hillBaggingPosition)
            && !mutableList[streetMapPosition + 1].startsWith(CHECK_HTTP))
}