package com.katocoding.munrolibrarychallenge.data.munrolist

import com.katocoding.munrolibrarychallenge.DEFAULT_INTEGER
import com.katocoding.munrolibrarychallenge.data.*
import com.katocoding.munrolibrarychallenge.data.errors.ErrorType
import com.katocoding.munrolibrarychallenge.util.isInt
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MunroListExtractor{

    fun extractMunroListData(records: MutableList<MutableList<String>>): ApiResponse<List<MunroModel>> {
        return when (val checkedRecords = checkStreetMap(records)) {
            is ApiResponse.Error -> {
                ApiResponse.Error(checkedRecords.errorType!!)
            }
            else -> {
                extractMunroLists(records[0], checkedRecords.data!!)
            }
        }
    }

    fun extractMunroLists(
        headerList: MutableList<String>,
        records: MutableList<MutableList<String>>
    ): ApiResponse<List<MunroModel>>  {
        val namePos = getHeaderListItem(headerList, MUNRODATA_NAME)
        val heightMPos = getHeaderListItem(headerList, MUNRODATA_HEIGHTM)
        val gridRefPos = getHeaderListItem(headerList, MUNRODATA_GRIDREF)
        val hillCategoryPos = getHeaderListItem(headerList, MUNRODATA_HILLCATEGORY)

        return if (namePos == DEFAULT_INTEGER || heightMPos == DEFAULT_INTEGER
            || gridRefPos == DEFAULT_INTEGER || hillCategoryPos == DEFAULT_INTEGER) {
            ApiResponse.Error(ErrorType.CSVHeaderMissing)
        } else {
            var errorType = ErrorType.NONE
            val munroList = records.mapNotNull {
                errorType = checkVitalData(namePos, heightMPos, gridRefPos, hillCategoryPos, it)
                if (errorType == ErrorType.NONE) {
                    MunroModel(
                        name = it[namePos],
                        heightM = it[heightMPos].toDouble(),
                        gridReference = it[gridRefPos],
                        hillCategory = stringToHillCategoryType(it[hillCategoryPos])
                    )
                } else null
            }
            if (munroList.isEmpty() && errorType != ErrorType.NONE) {
                ApiResponse.Error(errorType)
            } else {
                ApiResponse.Success(munroList)
            }
        }
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

    fun checkVitalData(
        namePos: Int,
        heightMPos: Int,
        gridRefPos: Int,
        hillCategoryPos: Int,
        recordDataList: List<String>
    ): ErrorType = when {
        (recordDataList.size <= namePos
                || (recordDataList.size > namePos && recordDataList[namePos].isBlank())) -> {
            ErrorType.CSVNameMissing
        }
        (recordDataList.size <= heightMPos
                || (recordDataList.size > heightMPos && recordDataList[heightMPos].toDoubleOrNull() == null)) -> {
            ErrorType.CSVHeightMMissing
        }
        (recordDataList.size <= gridRefPos
                || (recordDataList.size > gridRefPos && recordDataList[gridRefPos].isBlank())) -> {
            ErrorType.CSVGridRefMissing
        }
        (recordDataList.size <= hillCategoryPos
                || (recordDataList.size > hillCategoryPos && (recordDataList[hillCategoryPos].isBlank()))) -> {
            ErrorType.CSVHillCategoryMissing
        }
        else -> ErrorType.NONE
    }

    fun checkStreetMap(records: MutableList<MutableList<String>>): ApiResponse<MutableList<MutableList<String>>> {
        val headerList = records[0]
        val runningNoPosition = getHeaderListItem(headerList, MUNRODATA_RUNNINGNO)
        val streetMapPosition = getHeaderListItem(headerList, MUNRODATA_STREETMAP)
        val geographPosition = getHeaderListItem(headerList, MUNRODATA_GEOGRAPH)
        val hillBaggingPosition = getHeaderListItem(headerList, MUNRODATA_HILLBAGGING)
        if (runningNoPosition == DEFAULT_INTEGER || streetMapPosition == DEFAULT_INTEGER
            || geographPosition == DEFAULT_INTEGER || hillBaggingPosition == DEFAULT_INTEGER) {
            return ApiResponse.Error(ErrorType.CSVHeaderMissing)
        } else {
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
                                    modifiedList.add("$streetMapValue,$value")
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
            return ApiResponse.Success(newRecords)
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