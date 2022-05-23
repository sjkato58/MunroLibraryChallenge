package com.katocoding.munrolibrarychallenge.ui.munrolist

import androidx.lifecycle.Observer
import com.katocoding.munrolibrarychallenge.InstantExecutorExtension
import com.katocoding.munrolibrarychallenge.data.ApiResponse
import com.katocoding.munrolibrarychallenge.data.errors.ErrorType
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroListRepository
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FilterModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.MunroListFilter
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MunroListViewModelTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()

    private val munroListRepository = mockk<MunroListRepository>()
    private val munroListFilter = mockk<MunroListFilter>()

    lateinit var viewModel: MunroListViewModel

    private lateinit var filterModel: FilterModel
    private val munroModel1 = MunroModel(EXAMPLE_NAME, EXAMPLE_HEIGHTM, EXAMPLE_HILLCATEGORY_MUN, EXAMPLE_GRIDREF)
    private val munroModel2 = MunroModel(EXAMPLE_NAME2, EXAMPLE_HEIGHTM2, EXAMPLE_HILLCATEGORY_TOP, EXAMPLE_GRIDREF2)
    private val munroModel3 = MunroModel(EXAMPLE_NAME3, EXAMPLE_HEIGHTM3, EXAMPLE_HILLCATEGORY_MUN, EXAMPLE_GRIDREF3)

    private val munroList = mutableListOf(munroModel1, munroModel2, munroModel3)

    companion object {
        const val VALUE_ONCE = 1

        const val EXAMPLE_DEFAULT = "ThisIsAnExample"
        const val EXAMPLE_NAME = "Ben Chonzie"
        const val EXAMPLE_HEIGHTM = 1044.9
        const val EXAMPLE_GRIDREF = "NN367028"
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
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        filterModel = FilterModel()

        viewModel = MunroListViewModel(
            munroListRepository,
            munroListFilter
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun `when attempting to get list of munro records then should get a list`() {
        coEvery {
            munroListRepository.getMunroRecords()
        } returns ApiResponse.Success(munroList)
        coEvery {
            munroListFilter.checkFilterData(filterModel, munroList)
        } returns ApiResponse.Success(munroList)

        runTest {
            viewModel.getMunroList(MunroListLoadStatus.Initial)
        }

        coVerify(exactly = VALUE_ONCE) {
            munroListRepository.getMunroRecords()
        }
        coVerify(exactly = VALUE_ONCE) {
            munroListFilter.checkFilterData(filterModel, munroList)
        }
    }

    @Test
    fun `when attempting to get list of munro records then receives a complete munro list`() {
        val slot = slot<List<MunroListViewState>>()
        val responsesList = mutableListOf<List<MunroListViewState>>()
        val observer = mockk<Observer<List<MunroListViewState>>>()
        coEvery {
            munroListRepository.getMunroRecords()
        } returns ApiResponse.Success(munroList)
        coEvery {
            munroListFilter.checkFilterData(filterModel, munroList)
        } returns ApiResponse.Success(munroList)
        coEvery {
            observer.onChanged(capture(slot))
        } answers {
            responsesList.add(slot.captured)
        }
        viewModel.munroList.observeForever(observer)

        runTest {
            viewModel.getMunroList(MunroListLoadStatus.Initial)
        }

        print("This is the responsesList: $responsesList")

        coVerify(exactly = VALUE_ONCE) {
            munroListRepository.getMunroRecords()
        }
        coVerify(exactly = VALUE_ONCE) {
            munroListFilter.checkFilterData(filterModel, munroList)
        }
        assertTrue(responsesList.size > 0)
        assertTrue(responsesList[0][0].showLoading)
        assertTrue(responsesList[1][0].name == EXAMPLE_NAME)

        viewModel.munroList.removeObserver(observer)
    }

    @Test
    fun `when attempting to get list of munro records but an error occurs then the error should be sent back`() {
        val slot = slot<List<MunroListViewState>>()
        val responsesList = mutableListOf<List<MunroListViewState>>()
        val observer = mockk<Observer<List<MunroListViewState>>>()
        coEvery {
            munroListRepository.getMunroRecords()
        } returns ApiResponse.Error(ErrorType.CSVHillCategoryMissing)
        coEvery {
            observer.onChanged(capture(slot))
        } answers {
            responsesList.add(slot.captured)
        }
        viewModel.munroList.observeForever(observer)

        runTest {
            viewModel.getMunroList(MunroListLoadStatus.Initial)
        }

        print("This is the responsesList: $responsesList")

        coVerify(exactly = VALUE_ONCE) {
            munroListRepository.getMunroRecords()
        }
        assertTrue(responsesList.size > 0)
        assertTrue(responsesList[0][0].showLoading)
        assertTrue(responsesList[1][0].showError)
        assertTrue(responsesList[1][0].errorType == ErrorType.CSVHillCategoryMissing)

        viewModel.munroList.removeObserver(observer)
    }
}