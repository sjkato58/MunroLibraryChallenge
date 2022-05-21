package com.katocoding.munrolibrarychallenge.data.munrolist

import android.content.Context
import com.katocoding.munrolibrarychallenge.data.ApiResponse
import com.katocoding.munrolibrarychallenge.data.DELIMITER_COMMA
import com.katocoding.munrolibrarychallenge.data.MUNRO_CSV_FILE
import com.katocoding.munrolibrarychallenge.data.errors.DataErrorHandler
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FilterModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.MunroListFilter
import com.katocoding.munrolibrarychallenge.util.isInt
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@ActivityScoped
class MunroListRepository @Inject constructor(
    private val context: Context,
    private val munroListExtractor: MunroListExtractor,
    private val iODispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getMunroRecords(): ApiResponse<List<MunroModel>> = withContext(iODispatcher) {
        val rawRecords = obtainMunroRecordsFromCSV()
        val extractedRecords = munroListExtractor.extractMunroListData(rawRecords)
        ApiResponse.Success(extractedRecords)
    }

    fun obtainMunroRecordsFromCSV(): MutableList<MutableList<String>> {
        val records = mutableListOf<MutableList<String>>()
        val inputStream = context.assets.open(MUNRO_CSV_FILE)
        val scanner = Scanner(inputStream)
        scanner.use {
            while (it.hasNextLine()) {
                records.add(getRecordFromLine(it.nextLine()))
            }
        }
        return records
    }

    fun getRecordFromLine(line: String): MutableList <String> {
        val values = mutableListOf<String>()
        val rowScanner = Scanner(line)
        rowScanner.useDelimiter(DELIMITER_COMMA)
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next())
        }
        return values
    }
}