package com.bikcodeh.mubi.presentation.screens.profile

import com.bikcodeh.mubi.core_test.util.CoroutineRule
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.repository.TvRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var tvRepository: TvRepository

    private lateinit var profileViewModel: ProfileViewModel

    @Before
    fun setUp() {
        profileViewModel = ProfileViewModel(
            tvRepository,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getFavoritesTvShows() {
        assertThat(profileViewModel.favoritesTvShows.value).isEmpty()
    }

    @Test
    fun getFavorites() = runTest {
        /** Given */
        coEvery { tvRepository.getFavoritesTvShows() } returns listOf(
            TVShow(
                backdropPath = "",
                firstAirDate = "",
                id = "",
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
        )
        val results = arrayListOf<List<TVShow>>()

        val job = launch(UnconfinedTestDispatcher()) {
            profileViewModel.favoritesTvShows.toList(results)
        }

        /** When */
        profileViewModel.getFavorites()

        /** Then */
        assertThat(results).isNotEmpty()
        assertThat(results[1]).isNotEmpty()
        assertThat(results[1].first().name).isEqualTo("test")
        job.cancel()
    }

    @Test
    fun `getFavorites should return a empty list`() = runTest {
        /** Given */
        coEvery { tvRepository.getFavoritesTvShows() } returns listOf()
        val results = arrayListOf<List<TVShow>>()

        val job = launch(UnconfinedTestDispatcher()) {
            profileViewModel.favoritesTvShows.toList(results)
        }

        /** When */
        profileViewModel.getFavorites()

        /** Then */
        assertThat(results.first()).isEmpty()
        job.cancel()
    }
}