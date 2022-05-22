package com.katocoding.munrolibrarychallenge.data.munrolist

import com.katocoding.munrolibrarychallenge.DEFAULT_INTEGER
import com.katocoding.munrolibrarychallenge.data.*
import com.katocoding.munrolibrarychallenge.data.errors.ErrorType
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

    private val headerErroredList = mutableListOf(
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT
    )

    val exampleMunRecords = mutableListOf(
        EXAMPLE_RUNNINGNUMBER, EXAMPLE_DEFAULT, EXAMPLE_STREETMAP, EXAMPLE_STREETMAP_PART2, EXAMPLE_GEOGRAPH,
        EXAMPLE_HILLBAGGING, EXAMPLE_NAME, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_HEIGHTM, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_GRIDREF,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HILLCATEGORY_MUN, EXAMPLE_DEFAULT
    )

    val exampleMunRecordsFixed = mutableListOf(
        EXAMPLE_RUNNINGNUMBER, EXAMPLE_DEFAULT, EXAMPLE_STREETMAP2, EXAMPLE_GEOGRAPH, EXAMPLE_HILLBAGGING,
        EXAMPLE_NAME, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HEIGHTM,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_GRIDREF, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HILLCATEGORY_MUN, EXAMPLE_DEFAULT
    )

    val exampleTopRecords = mutableListOf(
        EXAMPLE_RUNNINGNUMBER, EXAMPLE_DEFAULT, EXAMPLE_STREETMAP, EXAMPLE_STREETMAP_PART2, EXAMPLE_GEOGRAPH,
        EXAMPLE_HILLBAGGING, EXAMPLE_NAME, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_HEIGHTM, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_GRIDREF,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HILLCATEGORY_TOP, EXAMPLE_DEFAULT
    )

    val exampleTopRecordsFixed = mutableListOf(
        EXAMPLE_RUNNINGNUMBER, EXAMPLE_DEFAULT, EXAMPLE_STREETMAP2, EXAMPLE_GEOGRAPH, EXAMPLE_HILLBAGGING,
        EXAMPLE_NAME, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HEIGHTM,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_GRIDREF, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_DEFAULT,
        EXAMPLE_DEFAULT, EXAMPLE_DEFAULT, EXAMPLE_HILLCATEGORY_TOP, EXAMPLE_DEFAULT
    )

    companion object {
        const val EXAMPLE_DEFAULT = "ThisIsAnExample"
        const val EXAMPLE_RUNNINGNUMBER = "1"
        const val EXAMPLE_STREETMAP = "http://www.streetmap.co.uk/newmap.srf?x=262911&y=718916&z=3&sv=262911"
        const val EXAMPLE_STREETMAP_PART2 = "718916&st=4&tl=~&bi=~&lu=N&ar=y"
        const val EXAMPLE_STREETMAP2 = "http://www.streetmap.co.uk/newmap.srf?x=262911&y=718916&z=3&sv=262911718916&st=4&tl=~&bi=~&lu=N&ar=y"
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

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME)
        assertTrue(result.data!![0].hillCategory == HillCategoryType.Munro)
        assertTrue(result.data!![0].heightM == EXAMPLE_HEIGHTM.toDouble())
        assertTrue(result.data!![0].gridReference == EXAMPLE_GRIDREF)
        assertTrue(result.data!![1].hillCategory == HillCategoryType.MunroTop)
    }

    @Test
    fun `when provided with a valid yet modified list of data then it should be extracted into a list of munromodels`() {
        val recordList = mutableListOf(headerList, exampleMunRecordsFixed, exampleTopRecordsFixed)

        val result = munroListExtractor.extractMunroListData(recordList)

        print("result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME)
        assertTrue(result.data!![0].hillCategory == HillCategoryType.Munro)
        assertTrue(result.data!![0].heightM == EXAMPLE_HEIGHTM.toDouble())
        assertTrue(result.data!![0].gridReference == EXAMPLE_GRIDREF)
        assertTrue(result.data!![1].hillCategory == HillCategoryType.MunroTop)
    }

    @Test
    fun `when streetmap data has been split then the divided data should be fixed on return`() {
        val recordList = mutableListOf(headerList, exampleMunRecords, exampleTopRecords)

        val result = munroListExtractor.checkStreetMap(recordList)

        print("result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0][0] == EXAMPLE_RUNNINGNUMBER)
        assertTrue(result.data!![0][2] == "$EXAMPLE_STREETMAP,$EXAMPLE_STREETMAP_PART2")
        assertTrue(result.data!![0][5] == EXAMPLE_NAME)
        assertTrue(result.data!![0][27] == EXAMPLE_HILLCATEGORY_MUN)
        assertTrue(result.data!![1][2] == "$EXAMPLE_STREETMAP,$EXAMPLE_STREETMAP_PART2")
        assertTrue(result.data!![1][27] == EXAMPLE_HILLCATEGORY_TOP)
    }

    @Test
    fun `when streetmap data has not been split then the data should be returned as is`() {
        val recordList = mutableListOf(headerList, exampleMunRecordsFixed, exampleTopRecordsFixed)

        val result = munroListExtractor.checkStreetMap(recordList)

        print("result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0][0] == EXAMPLE_RUNNINGNUMBER)
        assertTrue(result.data!![0][2] == EXAMPLE_STREETMAP2)
        assertTrue(result.data!![0][5] == EXAMPLE_NAME)
        assertTrue(result.data!![0][27] == EXAMPLE_HILLCATEGORY_MUN)
        assertTrue(result.data!![1][2] == EXAMPLE_STREETMAP2)
        assertTrue(result.data!![1][27] == EXAMPLE_HILLCATEGORY_TOP)
    }

    @Test
    fun `when streetmap data has been split but the headers are wrong then an error response should return`() {
        val recordList = mutableListOf(headerErroredList, exampleMunRecordsFixed, exampleTopRecordsFixed)

        val result = munroListExtractor.checkStreetMap(recordList)

        print("result: $result")

        assertTrue(result is ApiResponse.Error)
        assertTrue(result.errorType != null)
        assertTrue(result.errorType == ErrorType.CSVHeaderMissing)
    }

    @Test
    fun `when provided with clean data then it should be converted to data class list`() {
        val recordList = mutableListOf(exampleMunRecordsFixed, exampleTopRecordsFixed)
        val result = munroListExtractor.extractMunroLists(headerList, recordList)

        print("result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME)
        assertTrue(result.data!![0].hillCategory == HillCategoryType.Munro)
        assertTrue(result.data!![0].heightM == EXAMPLE_HEIGHTM.toDouble())
        assertTrue(result.data!![0].gridReference == EXAMPLE_GRIDREF)
        assertTrue(result.data!![1].hillCategory == HillCategoryType.MunroTop)
    }

    @Test
    fun `when provided with clean data but the header positions are wrong then an error response should return`() {
        val recordList = mutableListOf(exampleMunRecordsFixed, exampleTopRecordsFixed)

        val result = munroListExtractor.extractMunroLists(headerErroredList, recordList)

        print("result: $result")

        assertTrue(result is ApiResponse.Error)
        assertTrue(result.errorType != null)
        assertTrue(result.errorType == ErrorType.CSVHeaderMissing)
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

    @Test
    fun `when checking for a header which is not present then it should return the default interger property`() {
        val checkRunningNo = munroListExtractor.getHeaderListItem(headerList, EXAMPLE_NAME)

        assertTrue(checkRunningNo == DEFAULT_INTEGER)
    }

    @Test
    fun `when checking for vital data and it is present then a no error response should be sent back`() {
        val namePos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_NAME)
        assertTrue(namePos != DEFAULT_INTEGER)
        assertTrue(headerList[namePos] == MUNRODATA_NAME)

        val heightMPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HEIGHTM)
        assertTrue(heightMPos != DEFAULT_INTEGER)
        assertTrue(headerList[heightMPos] == MUNRODATA_HEIGHTM)

        val gridRefPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_GRIDREF)
        assertTrue(gridRefPos != DEFAULT_INTEGER)
        assertTrue(headerList[gridRefPos] == MUNRODATA_GRIDREF)

        val hillCategoryPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HILLCATEGORY)
        assertTrue(hillCategoryPos != DEFAULT_INTEGER)
        assertTrue(headerList[hillCategoryPos] == MUNRODATA_HILLCATEGORY)

        val result = munroListExtractor.checkVitalData(namePos, heightMPos, gridRefPos, hillCategoryPos, exampleMunRecordsFixed)

        print("result: $result")

        assertTrue(result == ErrorType.NONE)
    }

    @Test
    fun `when checking for vital data and name is missing then an error response should be sent back`() {
        val heightMPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HEIGHTM)
        assertTrue(heightMPos != DEFAULT_INTEGER)
        assertTrue(headerList[heightMPos] == MUNRODATA_HEIGHTM)

        val gridRefPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_GRIDREF)
        assertTrue(gridRefPos != DEFAULT_INTEGER)
        assertTrue(headerList[gridRefPos] == MUNRODATA_GRIDREF)

        val hillCategoryPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HILLCATEGORY)
        assertTrue(hillCategoryPos != DEFAULT_INTEGER)
        assertTrue(headerList[hillCategoryPos] == MUNRODATA_HILLCATEGORY)

        val result = munroListExtractor.checkVitalData(30, heightMPos, gridRefPos, hillCategoryPos, exampleMunRecordsFixed)

        print("result: $result")

        assertTrue(result == ErrorType.CSVNameMissing)
    }

    @Test
    fun `when checking for vital data and height is missing then an error response should be sent back`() {
        val namePos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_NAME)
        assertTrue(namePos != DEFAULT_INTEGER)
        assertTrue(headerList[namePos] == MUNRODATA_NAME)

        val gridRefPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_GRIDREF)
        assertTrue(gridRefPos != DEFAULT_INTEGER)
        assertTrue(headerList[gridRefPos] == MUNRODATA_GRIDREF)

        val hillCategoryPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HILLCATEGORY)
        assertTrue(hillCategoryPos != DEFAULT_INTEGER)
        assertTrue(headerList[hillCategoryPos] == MUNRODATA_HILLCATEGORY)

        val result = munroListExtractor.checkVitalData(namePos, 4, gridRefPos, hillCategoryPos, exampleMunRecordsFixed)

        print("result: $result")

        assertTrue(result == ErrorType.CSVHeightMMissing)
    }

    @Test
    fun `when checking for vital data and gridref is missing then an error response should be sent back`() {
        val namePos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_NAME)
        assertTrue(namePos != DEFAULT_INTEGER)
        assertTrue(headerList[namePos] == MUNRODATA_NAME)

        val heightMPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HEIGHTM)
        assertTrue(heightMPos != DEFAULT_INTEGER)
        assertTrue(headerList[heightMPos] == MUNRODATA_HEIGHTM)

        val hillCategoryPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HILLCATEGORY)
        assertTrue(hillCategoryPos != DEFAULT_INTEGER)
        assertTrue(headerList[hillCategoryPos] == MUNRODATA_HILLCATEGORY)

        val result = munroListExtractor.checkVitalData(namePos, heightMPos, 30, hillCategoryPos, exampleMunRecordsFixed)

        print("result: $result")

        assertTrue(result == ErrorType.CSVGridRefMissing)
    }

    @Test
    fun `when checking for vital data and hillcategory is missing then an error response should be sent back`() {
        val namePos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_NAME)
        assertTrue(namePos != DEFAULT_INTEGER)
        assertTrue(headerList[namePos] == MUNRODATA_NAME)

        val heightMPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HEIGHTM)
        assertTrue(heightMPos != DEFAULT_INTEGER)
        assertTrue(headerList[heightMPos] == MUNRODATA_HEIGHTM)

        val gridRefPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_GRIDREF)
        assertTrue(gridRefPos != DEFAULT_INTEGER)
        assertTrue(headerList[gridRefPos] == MUNRODATA_GRIDREF)

        val hillCategoryPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HILLCATEGORY)
        assertTrue(hillCategoryPos != DEFAULT_INTEGER)
        assertTrue(headerList[hillCategoryPos] == MUNRODATA_HILLCATEGORY)

        val result = munroListExtractor.checkVitalData(namePos, heightMPos, gridRefPos, 30, exampleMunRecordsFixed)

        print("result: $result")

        assertTrue(result == ErrorType.CSVHillCategoryMissing)
    }

    @Test
    fun `when a streetmap requires modification to work then this should be reported back`() {
        val streetMapPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_STREETMAP)
        val geographPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_GEOGRAPH)
        val hillBaggingPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HILLBAGGING)

        assertTrue(streetMapPos != DEFAULT_INTEGER)
        assertTrue(geographPos != DEFAULT_INTEGER)
        assertTrue(hillBaggingPos != DEFAULT_INTEGER)

        val result = munroListExtractor.isStreetMapModificationNeeded(
            exampleMunRecords,
            streetMapPos,
            geographPos,
            hillBaggingPos
        )

        assertTrue(result)
    }

    @Test
    fun `when a streetmap does not require modification to work then this should be reported back`() {
        val streetMapPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_STREETMAP)
        val geographPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_GEOGRAPH)
        val hillBaggingPos = munroListExtractor.getHeaderListItem(headerList, MUNRODATA_HILLBAGGING)

        assertTrue(streetMapPos != DEFAULT_INTEGER)
        assertTrue(geographPos != DEFAULT_INTEGER)
        assertTrue(hillBaggingPos != DEFAULT_INTEGER)

        val result = munroListExtractor.isStreetMapModificationNeeded(
            exampleMunRecordsFixed,
            streetMapPos,
            geographPos,
            hillBaggingPos
        )

        assertFalse(result)
    }
}