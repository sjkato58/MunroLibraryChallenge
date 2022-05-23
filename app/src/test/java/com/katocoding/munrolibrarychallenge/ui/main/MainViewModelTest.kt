package com.katocoding.munrolibrarychallenge.ui.main

import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.katocoding.munrolibrarychallenge.InstantExecutorExtension
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FilterModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType
import com.katocoding.munrolibrarychallenge.data.stringToHillCategoryType
import com.katocoding.munrolibrarychallenge.ui.munrolist.MunroListFragmentDirections
import com.katocoding.munrolibrarychallenge.util.isInt
import io.mockk.*
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class MainViewModelTest {

    lateinit var viewModel: MainViewModel

    companion object {
        const val EXAMPLE_EMPTY = ""
        const val EXAMPLE_STRING_INTEGER = "1"
        const val EXAMPLE_STRING = "This is an example"

        const val EXAMPLE_MUNRO = "MUN"
        const val EXAMPLE_TOP = "TOP"
    }

    @BeforeEach
    fun setUp() {
        viewModel = MainViewModel()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun `when attempting to navigate to a direction then the event will be fired off appropriately`() {
        val slot = slot<NavController.() -> Any>()
        val observer = mockk<Observer<NavController.() -> Any>>()
        every {
            observer.onChanged(capture(slot))
        } answers {
            slot.captured
        }
        viewModel.navigationEvent.observeForever(observer)

        viewModel.navigateInDirection(MunroListFragmentDirections.actionMunroListFragmentToMunroFilterFragment(FilterModel().convertToString()))

        print("This is the slot: ${slot.captured}")

        assertTrue(slot.captured is NavController.() -> Any)

        viewModel.navigationEvent.removeObserver(observer)
    }

    @Test
    fun `when testing whether a string can be an integer then a positive response will be returned`() {
        val result = EXAMPLE_STRING_INTEGER.isInt()

        assertTrue(result)
    }

    @Test
    fun `when a string is not an integer then then a negative response will be returned`() {
        val result = EXAMPLE_STRING.isInt()

        assertFalse(result)
    }

    @Test
    fun `when the right data for a munro hill category is provided then the munro hill category type is returned`() {
        val result = stringToHillCategoryType(EXAMPLE_MUNRO)

        assertTrue(result == HillCategoryType.Munro)
    }

    @Test
    fun `when the right data for a munro top hill category is provided then the munro top hill category type is returned`() {
        val result = stringToHillCategoryType(EXAMPLE_TOP)

        assertTrue(result == HillCategoryType.MunroTop)
    }
}