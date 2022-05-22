package com.katocoding.munrolibrarychallenge.data.munrolist

import com.katocoding.munrolibrarychallenge.DEFAULT_INTEGER
import com.katocoding.munrolibrarychallenge.data.*
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MunroListExtractorTest {

    lateinit var munroListExtractor: MunroListExtractor

    private val headerList = mutableListOf(
        MUNRODATA_RUNNINGNO, EXAMPLE_DEFAULT, MUNRODATA_STREETMAP, MUNRODATA_GEOGRAPH, MUNRODATA_HILLBAGGING,
        MUNRODATA_NAME, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, MUNRODATA_HEIGHTM,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, MUNRODATA_GRIDREF, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, MUNRODATA_HILLCATEGORY, EXAMPLE_DEFAULT
    )

    val exampleMunRecords = mutableListOf(
        EXAMPLE_RUNNINGNUMBER, EXAMPLE_DEFAULT, EXAMPLE_STREETMAP, EXAMPLE_GEOGRAPH, EXAMPLE_HILLBAGGING,
        EXAMPLE_NAME, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HEIGHTM,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_GRIDREF, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HILLCATEGORY_MUN, EXAMPLE_DEFAULT
    )

    val exampleTopRecords = mutableListOf(
        EXAMPLE_RUNNINGNUMBER, EXAMPLE_DEFAULT, EXAMPLE_STREETMAP, EXAMPLE_GEOGRAPH, EXAMPLE_HILLBAGGING,
        EXAMPLE_NAME, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HEIGHTM,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_GRIDREF, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HILLCATEGORY_TOP, EXAMPLE_DEFAULT
    )

    companion object {
        const val EXAMPLE_DEFAULT = "ThisIsAnExample"
        const val EXAMPLE_RUNNINGNUMBER = "1"
        const val EXAMPLE_STREETMAP = "http://www.streetmap.co.uk/newmap.srf?x=277324&y=730857&z=3&sv=277324,730857&st=4&tl=~&bi=~&lu=N&ar=y"
        const val EXAMPLE_GEOGRAPH = "http://www."
        const val EXAMPLE_HILLBAGGING = "http://www."
        const val EXAMPLE_NAME = "Ben Chonzie"
        const val EXAMPLE_HEIGHTM = "1044.9"
        const val EXAMPLE_GRIDREF = "NN367028"
        const val EXAMPLE_HILLCATEGORY_MUN = "MUN"
        const val EXAMPLE_HILLCATEGORY_TOP = "TOP"
    }

    @BeforeEach
    fun setUp() {
        munroListExtractor = MunroListExtractor()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun `when provided with a valid list of data then it should be extracted into a list of munromodels`() {
        val recordList = mutableListOf(headerList, exampleMunRecords, exampleTopRecords)

        val result = munroListExtractor.extractMunroListData(recordList)

        print("result: $result")

        assertTrue(result.isNotEmpty())
        assertTrue(result[0].name == EXAMPLE_NAME)
        assertTrue(result[0].hillCategory == HillCategoryType.Munro)
        assertTrue(result[0].heightM == EXAMPLE_HEIGHTM.toDouble())
        assertTrue(result[0].gridReference == EXAMPLE_GRIDREF)
        assertTrue(result[1].hillCategory == HillCategoryType.MunroTop)
    }

    @Test
    fun `when checking for a specific header then it should return the headers position in the csv table`() {
        val checkRunningNo = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_RUNNINGNO)

        assertTrue(checkRunningNo != DEFAULT_INTEGER)
        assertTrue(headerList[checkRunningNo] == MUNRODATA_RUNNINGNO)

        val streetMapNo = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_STREETMAP)

        assertTrue(streetMapNo != DEFAULT_INTEGER)
        assertTrue(headerList[streetMapNo] == MUNRODATA_STREETMAP)

        val nameNo = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_NAME)

        assertTrue(nameNo != DEFAULT_INTEGER)
        assertTrue(headerList[nameNo] == MUNRODATA_NAME)

        val gridRefNo = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_GRIDREF)

        assertTrue(gridRefNo != DEFAULT_INTEGER)
        assertTrue(headerList[gridRefNo] == MUNRODATA_GRIDREF)

        val hillCategoryNo = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HILLCATEGORY)

        assertTrue(hillCategoryNo != DEFAULT_INTEGER)
        assertTrue(headerList[hillCategoryNo] == MUNRODATA_HILLCATEGORY)
    }
}