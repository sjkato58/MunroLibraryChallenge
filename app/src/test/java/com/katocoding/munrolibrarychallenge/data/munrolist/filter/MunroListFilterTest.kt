package com.katocoding.munrolibrarychallenge.data.munrolist.filter

import com.katocoding.munrolibrarychallenge.data.ApiResponse
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroModel
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MunroListFilterTest {

    lateinit var munroListFilter: MunroListFilter

    private lateinit var filterModel: FilterModel
    private val munroModel1 = MunroModel(EXAMPLE_NAME1, EXAMPLE_HEIGHTM1, EXAMPLE_HILLCATEGORY_MUN, EXAMPLE_GRIDREF1)
    private val munroModel2 = MunroModel(EXAMPLE_NAME2, EXAMPLE_HEIGHTM2, EXAMPLE_HILLCATEGORY_TOP, EXAMPLE_GRIDREF2)
    private val munroModel3 = MunroModel(EXAMPLE_NAME3, EXAMPLE_HEIGHTM3, EXAMPLE_HILLCATEGORY_MUN, EXAMPLE_GRIDREF3)

    private val munroList = mutableListOf(munroModel1, munroModel2, munroModel3)

    companion object {
        const val EXAMPLE_DEFAULT = "ThisIsAnExample"
        const val EXAMPLE_NAME1 = "Ben Chonzie"
        const val EXAMPLE_HEIGHTM1 = 1044.9
        const val EXAMPLE_GRIDREF1 = "NN367028"
        const val EXAMPLE_NAME2 = "Chonzy"
        const val EXAMPLE_HEIGHTM2 = 988.4
        const val EXAMPLE_GRIDREF2 = "NN023678"
        const val EXAMPLE_NAME3 = "Aaron"
        const val EXAMPLE_HEIGHTM3 = 1102.6
        const val EXAMPLE_GRIDREF3 = "NN367028"
        val EXAMPLE_HILLCATEGORY_MUN = HillCategoryType.Munro
        val EXAMPLE_HILLCATEGORY_TOP = HillCategoryType.MunroTop
    }

    @BeforeEach
    fun setUp() {
        munroListFilter = MunroListFilter()

        filterModel = FilterModel()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun `when a list does not have any filtering or sorting required then it should return unchanged`() {
        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME1)
        assertTrue(result.data!![1].name == EXAMPLE_NAME2)
        assertTrue(result.data!![2].name == EXAMPLE_NAME3)
    }

    @Test
    fun `when a list requires alphabetic ascending sorting then it should return sorted`() {
        filterModel.sortAlphabetType = SortType.Ascending

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME3)
        assertTrue(result.data!![1].name == EXAMPLE_NAME1)
        assertTrue(result.data!![2].name == EXAMPLE_NAME2)
    }

    @Test
    fun `when a list requires alphabetic descending sorting then it should return sorted`() {
        filterModel.sortAlphabetType = SortType.Descending

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME2)
        assertTrue(result.data!![1].name == EXAMPLE_NAME1)
        assertTrue(result.data!![2].name == EXAMPLE_NAME3)
    }

    @Test
    fun `when a list requires height ascending sorting then it should return sorted`() {
        filterModel.sortHeightMType = SortType.Ascending

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME2)
        assertTrue(result.data!![1].name == EXAMPLE_NAME1)
        assertTrue(result.data!![2].name == EXAMPLE_NAME3)
    }

    @Test
    fun `when a list requires height descending sorting then it should return sorted`() {
        filterModel.sortHeightMType = SortType.Descending

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME3)
        assertTrue(result.data!![1].name == EXAMPLE_NAME1)
        assertTrue(result.data!![2].name == EXAMPLE_NAME2)
    }

    @Test
    fun `when a list has a max height sorting then it should return sorted`() {
        filterModel.maxHeight = 1050.0

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME1)
        assertTrue(result.data!![1].name == EXAMPLE_NAME2)
    }

    @Test
    fun `when a list has a max height sorting but prevents anything from being seen then it should return empty`() {
        filterModel.maxHeight = 980.0

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isEmpty())
    }

    @Test
    fun `when a list has a min height sorting then it should return sorted`() {
        filterModel.minHeight = 1000.0

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!![0].name == EXAMPLE_NAME1)
        assertTrue(result.data!![1].name == EXAMPLE_NAME3)
    }

    @Test
    fun `when a list has a min height sorting but prevents anything from being seen then it should return empty`() {
        filterModel.minHeight = 1120.0

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isEmpty())
    }

    @Test
    fun `when a list allows for only munro types then it should return with those items in question`() {
        filterModel.hillCategory = HillCategoryType.Munro

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!!.size == 2)
        assertTrue(result.data!![0].name == EXAMPLE_NAME1)
        assertTrue(result.data!![1].name == EXAMPLE_NAME3)
    }

    @Test
    fun `when a list allows for only munro top types then it should return with those items in question`() {
        filterModel.hillCategory = HillCategoryType.MunroTop

        val result = munroListFilter.checkFilterData(filterModel, munroList)

        print("This is the result: $result")

        assertTrue(result is ApiResponse.Success)
        assertTrue(result.data != null)
        assertTrue(result.data!!.isNotEmpty())
        assertTrue(result.data!!.size == 1)
        assertTrue(result.data!![0].name == EXAMPLE_NAME2)
    }
}