package com.katocoding.munrolibrarychallenge.ui.munrofilter

import androidx.lifecycle.Observer
import com.katocoding.munrolibrarychallenge.InstantExecutorExtension
import com.katocoding.munrolibrarychallenge.data.errors.ErrorType
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FilterModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType
import com.katocoding.munrolibrarychallenge.ui.munrolist.MunroListViewModelTest
import com.katocoding.munrolibrarychallenge.ui.munrolist.MunroListViewState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MunroFilterViewModelTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()

    lateinit var viewModel: MunroFilterViewModel

    companion object {
        const val VALUE_ONCE = 1
        const val VALUE_EMPTY = ""
        const val VALUE_TEN = 10
        const val VALUE_TWENTY = 20

        const val FILTER_HILLCAT = "Either"
        const val FILTER_HILLCAT_MUNRO = "Munro"
        const val FILTER_HILLCAT_TOP = "Munro Top"
        const val FILTER_SORT_ASCENDING = "Ascending"
        const val FILTER_SORT_DESCENDING = "Descending"
        const val FILTER_SORT_LIMIT = "20"
        const val FILTER_SORT_HEIGHT_MAX = "1050.5"
        const val FILTER_SORT_HEIGHT_MAX_SMALL = "950.5"
        const val FILTER_SORT_HEIGHT_MIN = "950.5"
        const val FILTER_SORT_HEIGHT_MIN_BIG = "1050.5"
    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        viewModel = MunroFilterViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun `when providing an initial filter to the viewmodel then the viewmodels own filtermodel should update`(){
        val slot = slot<FilterModel>()
        val responsesList = mutableListOf<FilterModel>()
        val observer = mockk<Observer<FilterModel>>()

        every {
            observer.onChanged(capture(slot))
        } answers {
            responsesList.add(slot.captured)
        }
        viewModel.filterModel.observeForever(observer)

        val filterModel = FilterModel(sortLimit = VALUE_TWENTY)

        viewModel.initFilterModel(filterModel.convertToString())

        print("This is the responsesList: $responsesList")

        assertTrue(responsesList.size > 0)
        assertTrue(responsesList.size == 2)
        assertTrue(responsesList[0].sortLimit == VALUE_TEN)//<- Default Value
        assertTrue(responsesList[1].sortLimit == VALUE_TWENTY)//<- Initial Value

        viewModel.filterModel.removeObserver(observer)
    }

    @Test
    fun `when updating the filter without errors then the updated filter should be obtainable to pass back to the munro list`(){
        val slot = slot<FilterModel>()
        val responsesList = mutableListOf<FilterModel>()
        val observer = mockk<Observer<FilterModel>>()
        val slotChanged = slot<Boolean>()
        val responsesListChanged = mutableListOf<Boolean>()
        val observerChanged = mockk<Observer<Boolean>>()

        every {
            observer.onChanged(capture(slot))
        } answers {
            responsesList.add(slot.captured)
        }
        viewModel.filterModel.observeForever(observer)
        every {
            observerChanged.onChanged(capture(slotChanged))
        } answers {
            responsesListChanged.add(slotChanged.captured)
        }
        viewModel.filterChanged.observeForever(observerChanged)

        viewModel.updateFilterModel(
            FILTER_HILLCAT_MUNRO,
            FILTER_SORT_ASCENDING,
            FILTER_SORT_DESCENDING,
            FILTER_SORT_LIMIT,
            FILTER_SORT_HEIGHT_MAX,
            FILTER_SORT_HEIGHT_MIN
        )

        println("This is the responsesList: $responsesList")
        println("This is the responsesListChanged: $responsesListChanged")

        assertTrue(responsesList.size > 0)
        assertTrue(responsesList.size == 2)
        assertTrue(responsesList[0].sortLimit == VALUE_TEN)
        assertTrue(responsesList[1].sortLimit == VALUE_TWENTY)
        assertTrue(responsesList[1].hillCategory == HillCategoryType.Munro)

        assertTrue(responsesListChanged.size > 0)
        assertTrue(responsesListChanged.size == 2)
        assertFalse(responsesListChanged[0])
        assertTrue(responsesListChanged[1])

        val filterResult = viewModel.obtainFilterModel()

        assertTrue(filterResult != null)
        assertTrue(filterResult!!.hillCategory == HillCategoryType.Munro)
        assertTrue(filterResult.sortLimit == VALUE_TWENTY)

        viewModel.filterModel.removeObserver(observer)
        viewModel.filterChanged.removeObserver(observerChanged)
    }

    @Test
    fun `when the height maximum is smaller than the height minumum then an error should be reported`(){
        val slot = slot<FilterModel>()
        val responsesList = mutableListOf<FilterModel>()
        val observer = mockk<Observer<FilterModel>>()
        val slotError = slot<MunroFilterViewState>()
        val responsesListError = mutableListOf<MunroFilterViewState>()
        val observerError = mockk<Observer<MunroFilterViewState>>()

        every {
            observer.onChanged(capture(slot))
        } answers {
            responsesList.add(slot.captured)
        }
        viewModel.filterModel.observeForever(observer)
        every {
            observerError.onChanged(capture(slotError))
        } answers {
            responsesListError.add(slotError.captured)
        }
        viewModel.filterErrorState.observeForever(observerError)

        viewModel.updateFilterModel(
            FILTER_HILLCAT_MUNRO,
            FILTER_SORT_ASCENDING,
            FILTER_SORT_DESCENDING,
            FILTER_SORT_LIMIT,
            FILTER_SORT_HEIGHT_MAX_SMALL,
            FILTER_SORT_HEIGHT_MIN_BIG
        )

        println("This is the responsesList: $responsesList")
        println("This is the responsesListError: $responsesListError")

        assertTrue(responsesList.size > 0)
        assertTrue(responsesList.size == 1)
        assertTrue(responsesList[0].hillCategory == HillCategoryType.Either)

        assertTrue(responsesListError.size > 0)
        assertTrue(responsesListError.size == 1)
        assertTrue(responsesListError[0].maxError == ErrorType.MaxSmallerThanMin)

        viewModel.filterModel.removeObserver(observer)
        viewModel.filterErrorState.removeObserver(observerError)
    }
}