package com.gaffaryucel.artbookhlttestingapp.viewmodel

import com.gaffaryucel.artbookhlttestingapp.MainCoroutineRule
import com.gaffaryucel.artbookhlttestingapp.getOrAwaitValueTest
import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel
import com.gaffaryucel.artbookhlttestingapp.repo.ArtRepoInterface
import com.gaffaryucel.artbookhlttestingapp.repo.FakeArtRepo
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.gaffaryucel.artbookhlttestingapp.util.Status
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ArtListViewModelTest {

    @InjectMocks
    private lateinit var viewModel: ArtListViewModel

    private val repo = FakeArtRepo()

    @Before
    fun setUp() {
        viewModel = ArtListViewModel(repo)
    }

    @Test
    fun `searchImage should return loading state when search string is empty`() {
        // Arrange
        val searchString = ""

        // Act
        viewModel.searchImage(searchString)

        // Assert
        val expected = Resource.loading(null)
        assertEquals(expected, viewModel.imageList.value)
    }

    @Test
    fun `searchImage should return success state when search string is not empty`() = runTest{
        // Arrange
        val searchString = "mona lisa"
        val expected = Resource.success(PixabeyModel(arrayListOf(),0,0))
        Mockito.`when`(repo.searchImage(searchString)).thenReturn(expected)

        // Act
        viewModel.searchImage(searchString)

        // Assert
        assertEquals(expected, viewModel.imageList.value)
    }

}