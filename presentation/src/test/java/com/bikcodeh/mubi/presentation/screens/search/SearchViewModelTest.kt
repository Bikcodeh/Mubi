package com.bikcodeh.mubi.presentation.screens.search

import com.bikcodeh.mubi.core_test.util.CoroutineRule
import com.bikcodeh.mubi.domain.R
import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.usecase.SearchTvShowsRemoteUC
import com.bikcodeh.mubi.domain.usecase.SearchTvShowsUC
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var searchTvShowsUC: SearchTvShowsUC

    @RelaxedMockK
    private lateinit var searchTvShowsRemoteUC: SearchTvShowsRemoteUC

    private lateinit var searchViewModel: SearchViewModel

    private val slot = slot<String>()

    private val tvShow = TVShow(
        backdropPath = "",
        firstAirDate = "",
        id = "1",
        name = "test",
        originalLanguage = "",
        originalName = "",
        overview = "",
        popularity = 0.0,
        posterPath = "",
        voteAverage = 0.0,
        voteCount = 0,
        isFavorite = false,
        category = "",
        seasons = listOf()
    )

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(
            searchTvShowsUC,
            searchTvShowsRemoteUC,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getSearchTvShowsState() {
        assertThat(searchViewModel.searchTvShowsState.value).isInstanceOf(SearchState::class.java)
    }

    @Test
    fun `getSearchTvShowsState initial state`() {
        assertThat((searchViewModel.searchTvShowsState.value)).isEqualTo(SearchState.Idle)
    }

    @Test
    fun searchTvShows() = runTest {
        /** Given */
        coEvery { searchTvShowsUC(capture(slot)) } returns flowOf(
            listOf(
                tvShow,
                tvShow.copy(name = "test2")
            )
        )
        val results = arrayListOf<SearchState>()

        val job = launch(UnconfinedTestDispatcher()) {
            searchViewModel.searchTvShowsState.toList(results)
        }

        /** When */
        searchViewModel.searchTvShows("data")

        /** Then */
        assertThat(slot.captured).isEqualTo("data")
        assertThat(results).isNotNull()
        assertThat(results).isNotEmpty()
        assertThat(results[1]).isInstanceOf(SearchState.Success::class.java)
        assertThat((results[1] as SearchState.Success).tvShows).isNotNull()
        assertThat((results[1] as SearchState.Success).tvShows).isNotEmpty()
        assertThat((results[1] as SearchState.Success).tvShows.first().name).isEqualTo("test")
        assertThat((results[1] as SearchState.Success).tvShows[1].name).isEqualTo("test2")

        coVerify { searchTvShowsUC("data") }
        job.cancel()
    }

    @Test
    fun `searchTvShows when return empty list should fetch from remote`() = runTest {
        /** Given */
        coEvery { searchTvShowsUC("data") } returns flowOf(listOf())
        coEvery { searchTvShowsRemoteUC(capture(slot)) } returns Result.Success(
            listOf(
                tvShow,
                tvShow.copy(name = "test2")
            )
        )
        val results = arrayListOf<SearchState>()

        val job = launch(UnconfinedTestDispatcher()) {
            searchViewModel.searchTvShowsState.toList(results)
        }

        /** When */
        searchViewModel.searchTvShows("data")

        /** Then */
        assertThat(slot.captured).isEqualTo("data")
        assertThat(results).isNotNull()
        assertThat(results).isNotEmpty()
        assertThat(results[1]).isEqualTo(SearchState.Loading)
        assertThat(results[2]).isInstanceOf(SearchState.Success::class.java)
        assertThat((results[2] as SearchState.Success).tvShows).isNotNull()
        assertThat((results[2] as SearchState.Success).tvShows).isNotEmpty()
        assertThat((results[2] as SearchState.Success).tvShows.first().name).isEqualTo("test")
        assertThat((results[2] as SearchState.Success).tvShows[1].name).isEqualTo("test2")

        coVerify { searchTvShowsUC("data") }
        job.cancel()
    }

    @Test
    fun `searchTvShowsRemote should handle result error (Http exception)`() = runTest {
        /** Given */
        coEvery { searchTvShowsUC("data") } returns flowOf(listOf())
        coEvery { searchTvShowsRemoteUC(capture(slot)) } returns Result.Error(401, "error")
        val results = arrayListOf<SearchState>()

        val job = launch(UnconfinedTestDispatcher()) {
            searchViewModel.searchTvShowsState.toList(results)
        }

        /** When */
        searchViewModel.searchTvShows("data")

        /** Then */
        assertThat(slot.captured).isEqualTo("data")
        assertThat(results).isNotNull()
        assertThat(results).isNotEmpty()
        assertThat(results[1]).isEqualTo(SearchState.Loading)
        assertThat(results[2]).isInstanceOf(SearchState.Failure::class.java)
        assertThat((results[2] as SearchState.Failure).error).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.displayTryAgainBtn).isTrue()
        assertThat((results[2] as SearchState.Failure).error?.notFoundError).isFalse()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isEqualTo(R.string.unauthorized_error)

        coVerify { searchTvShowsUC("data") }
        coVerify { searchTvShowsRemoteUC("data") }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Connectivity)`() = runTest {
        /** Given */
        coEvery { searchTvShowsUC("data") } returns flowOf(listOf())
        coEvery { searchTvShowsRemoteUC(capture(slot)) } returns Result.Exception(
            java.io.IOException(
                "error"
            )
        )
        val results = arrayListOf<SearchState>()

        val job = launch(UnconfinedTestDispatcher()) {
            searchViewModel.searchTvShowsState.toList(results)
        }

        /** When */
        searchViewModel.searchTvShows("data")

        /** Then */
        assertThat(slot.captured).isEqualTo("data")
        assertThat(results).isNotNull()
        assertThat(results).isNotEmpty()
        assertThat(results[1]).isEqualTo(SearchState.Loading)
        assertThat(results[2]).isInstanceOf(SearchState.Failure::class.java)
        assertThat((results[2] as SearchState.Failure).error).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.displayTryAgainBtn).isTrue()
        assertThat((results[2] as SearchState.Failure).error?.notFoundError).isFalse()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isEqualTo(R.string.connectivity_error)

        coVerify { searchTvShowsUC("data") }
        coVerify { searchTvShowsRemoteUC("data") }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Unknown)`() = runTest {
        /** Given */
        coEvery { searchTvShowsUC("data") } returns flowOf(listOf())
        coEvery { searchTvShowsRemoteUC(capture(slot)) } returns Result.Exception(
            IllegalAccessException(
                "error"
            )
        )
        val results = arrayListOf<SearchState>()

        val job = launch(UnconfinedTestDispatcher()) {
            searchViewModel.searchTvShowsState.toList(results)
        }

        /** When */
        searchViewModel.searchTvShows("data")

        /** Then */
        assertThat(slot.captured).isEqualTo("data")
        assertThat(results).isNotNull()
        assertThat(results).isNotEmpty()
        assertThat(results[1]).isEqualTo(SearchState.Loading)
        assertThat(results[2]).isInstanceOf(SearchState.Failure::class.java)
        assertThat((results[2] as SearchState.Failure).error).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.displayTryAgainBtn).isTrue()
        assertThat((results[2] as SearchState.Failure).error?.notFoundError).isFalse()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isEqualTo(R.string.unknown_error)

        coVerify { searchTvShowsRemoteUC("data") }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Internet connection)`() = runTest {
        /** Given */
        coEvery { searchTvShowsUC("data") } returns flowOf(listOf())
        coEvery { searchTvShowsRemoteUC(capture(slot)) } returns Result.Exception(
            UnknownHostException("error")
        )
        val results = arrayListOf<SearchState>()

        val job = launch(UnconfinedTestDispatcher()) {
            searchViewModel.searchTvShowsState.toList(results)
        }

        /** When */
        searchViewModel.searchTvShows("data")

        /** Then */
        assertThat(slot.captured).isEqualTo("data")
        assertThat(results).isNotNull()
        assertThat(results).isNotEmpty()
        assertThat(results[1]).isEqualTo(SearchState.Loading)
        assertThat(results[2]).isInstanceOf(SearchState.Failure::class.java)
        assertThat((results[2] as SearchState.Failure).error).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.displayTryAgainBtn).isTrue()
        assertThat((results[2] as SearchState.Failure).error?.notFoundError).isFalse()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isEqualTo(R.string.internet_error)

        coVerify { searchTvShowsUC("data") }
        coVerify { searchTvShowsRemoteUC("data") }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Not Found)`() = runTest {
        /** Given */
        coEvery { searchTvShowsUC("data") } returns flowOf(listOf())
        coEvery { searchTvShowsRemoteUC(capture(slot)) } returns Result.Error(404, "error")
        val results = arrayListOf<SearchState>()

        val job = launch(UnconfinedTestDispatcher()) {
            searchViewModel.searchTvShowsState.toList(results)
        }

        /** When */
        searchViewModel.searchTvShows("data")

        /** Then */
        assertThat(slot.captured).isEqualTo("data")
        assertThat(results).isNotNull()
        assertThat(results).isNotEmpty()
        assertThat(results[1]).isEqualTo(SearchState.Loading)
        assertThat(results[2]).isInstanceOf(SearchState.Failure::class.java)
        assertThat((results[2] as SearchState.Failure).error).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isNotNull()
        assertThat((results[2] as SearchState.Failure).error?.displayTryAgainBtn).isFalse()
        assertThat((results[2] as SearchState.Failure).error?.notFoundError).isTrue()
        assertThat((results[2] as SearchState.Failure).error?.errorMessage).isEqualTo(R.string.not_found_error)

        coVerify { searchTvShowsUC("data") }
        coVerify { searchTvShowsRemoteUC("data") }
        job.cancel()
    }
}