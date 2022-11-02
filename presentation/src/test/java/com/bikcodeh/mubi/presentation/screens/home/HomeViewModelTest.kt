package com.bikcodeh.mubi.presentation.screens.home

import androidx.paging.PagingData
import androidx.paging.map
import com.bikcodeh.mubi.core_test.util.CoroutineRule
import com.bikcodeh.mubi.data.mappers.SeasonMapperEntity
import com.bikcodeh.mubi.data.mappers.TvShowMapperEntity
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.usecase.GetTvShowsUC
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var getTvShowsUC: GetTvShowsUC

    private val seasonMapper = SeasonMapperEntity()

    private val mapper = TvShowMapperEntity(seasonMapper)

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            getTvShowsUC,
            mapper,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getTvShows() {
        assertThat(homeViewModel.tvShows.value).isInstanceOf(Flow::class.java)
    }

    @Test
    fun searchTvShows() = runTest {
        /** Given */
        every { getTvShowsUC(tvShowType = TvShowType.POPULAR) } returns flowOf(
            PagingData.from(
                listOf(
                    TvShowEntity(
                        backdropPath = "",
                        firstAirDate = "",
                        id = "1",
                        name = "test data",
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
            )
        )

        val results = arrayListOf<Flow<PagingData<TVShow>>>()

        val job = launch(UnconfinedTestDispatcher()) {
            homeViewModel.tvShows.toList(results)
        }

        /** When */
        homeViewModel.searchTvShows(tvShowType = TvShowType.POPULAR)

        /** Then */
        assertThat(results).isNotEmpty()
        results[1].first().map {
            assertThat(it).isNotNull()
            assertThat(it.name).isEqualTo("test data")
            assertThat(it.id).isEqualTo("1")
        }
        job.cancel()
    }
}