package com.bikcodeh.mubi.presentation.screens.season

import com.bikcodeh.mubi.core_test.util.CoroutineRule
import com.bikcodeh.mubi.domain.R
import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.model.Season
import com.bikcodeh.mubi.domain.repository.TvRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
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
import java.io.IOException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class SeasonViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var tvRepository: TvRepository

    private lateinit var seasonViewModel: SeasonViewModel

    @Before
    fun setUp() {
        seasonViewModel = SeasonViewModel(
            tvRepository,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getSeasonUIState() {
        assertThat(seasonViewModel.seasonUIState.value).isInstanceOf(SeasonState::class.java)
    }

    @Test
    fun `getSeasonUIState initial state`() {
        assertThat(seasonViewModel.seasonUIState.value).isEqualTo(SeasonState())
        assertThat(seasonViewModel.seasonUIState.value.isLoading).isFalse()
        assertThat(seasonViewModel.seasonUIState.value.season).isNull()
        assertThat(seasonViewModel.seasonUIState.value.error).isNull()
    }

    @Test
    fun `getSeasonDetail should be successful`() = runTest {
        /** Given */
        val season = Season(
            id = 0,
            name = "test",
            posterPath = null,
            totalEpisodes = null,
            overview = "",
            seasonNumber = 0,
            episodes = listOf()
        )
        coEvery { tvRepository.getDetailSeason("1", 1) } returns Result.Success(season)
        val results = arrayListOf<SeasonState>()

        val job = launch(UnconfinedTestDispatcher()) {
            seasonViewModel.seasonUIState.toList(results)
        }

        /** When */
        seasonViewModel.getSeasonDetail("1", 1)

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].error).isNull()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNull()
        assertThat(results[2].season).isNotNull()
        assertThat(results[2].season?.name).isEqualTo("test")

        coVerify { tvRepository.getDetailSeason("1", 1) }
        job.cancel()
    }

    @Test
    fun `getSeasonDetail should handle result error (Http exception)`() = runTest {
        /** Given */
        coEvery { tvRepository.getDetailSeason("1", 1) } returns Result.Error(401, "error")
        val results = arrayListOf<SeasonState>()

        val job = launch(UnconfinedTestDispatcher()) {
            seasonViewModel.seasonUIState.toList(results)
        }

        /** When */
        seasonViewModel.getSeasonDetail("1", 1)

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].error).isNull()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNotNull()
        assertThat(results[2].error?.errorMessage).isNotNull()
        assertThat(results[2].error?.displayTryAgainBtn).isTrue()
        assertThat(results[2].error?.notFoundError).isFalse()
        assertThat(results[2].error?.errorMessage).isEqualTo(R.string.unauthorized_error)
        assertThat(results[2].season).isNull()

        coVerify { tvRepository.getDetailSeason("1", 1) }
        job.cancel()
    }

    @Test
    fun `getSeasonDetail should handle result error (Connectivity)`() = runTest {
        /** Given */
        coEvery {
            tvRepository.getDetailSeason(
                "1",
                1
            )
        } returns Result.Exception(IOException("error"))
        val results = arrayListOf<SeasonState>()

        val job = launch(UnconfinedTestDispatcher()) {
            seasonViewModel.seasonUIState.toList(results)
        }

        /** When */
        seasonViewModel.getSeasonDetail("1", 1)

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].error).isNull()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNotNull()
        assertThat(results[2].error?.errorMessage).isNotNull()
        assertThat(results[2].error?.displayTryAgainBtn).isTrue()
        assertThat(results[2].error?.notFoundError).isFalse()
        assertThat(results[2].error?.errorMessage).isEqualTo(R.string.connectivity_error)
        assertThat(results[2].season).isNull()

        coVerify { tvRepository.getDetailSeason("1", 1) }
        job.cancel()
    }

    @Test
    fun `getSeasonDetail should handle result error (Unknown)`() = runTest {
        /** Given */
        coEvery { tvRepository.getDetailSeason("1", 1) } returns Result.Exception(
            IllegalAccessException(
                "error"
            )
        )
        val results = arrayListOf<SeasonState>()

        val job = launch(UnconfinedTestDispatcher()) {
            seasonViewModel.seasonUIState.toList(results)
        }

        /** When */
        seasonViewModel.getSeasonDetail("1", 1)

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].error).isNull()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNotNull()
        assertThat(results[2].error?.errorMessage).isNotNull()
        assertThat(results[2].error?.displayTryAgainBtn).isTrue()
        assertThat(results[2].error?.notFoundError).isFalse()
        assertThat(results[2].error?.errorMessage).isEqualTo(R.string.unknown_error)
        assertThat(results[2].season).isNull()

        coVerify { tvRepository.getDetailSeason("1", 1) }
        job.cancel()
    }

    @Test
    fun `getSeasonDetail should handle result error (Internet connection)`() = runTest {
        /** Given */
        coEvery { tvRepository.getDetailSeason("1", 1) } returns Result.Exception(
            UnknownHostException("error")
        )
        val results = arrayListOf<SeasonState>()

        val job = launch(UnconfinedTestDispatcher()) {
            seasonViewModel.seasonUIState.toList(results)
        }

        /** When */
        seasonViewModel.getSeasonDetail("1", 1)

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].error).isNull()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNotNull()
        assertThat(results[2].error?.errorMessage).isNotNull()
        assertThat(results[2].error?.displayTryAgainBtn).isTrue()
        assertThat(results[2].error?.notFoundError).isFalse()
        assertThat(results[2].error?.errorMessage).isEqualTo(R.string.internet_error)
        assertThat(results[2].season).isNull()

        coVerify { tvRepository.getDetailSeason("1", 1) }
        job.cancel()
    }

    @Test
    fun `getSeasonDetail should handle result error (Not Found)`() = runTest {
        /** Given */
        coEvery { tvRepository.getDetailSeason("1", 1) } returns Result.Error(404, "error")
        val results = arrayListOf<SeasonState>()

        val job = launch(UnconfinedTestDispatcher()) {
            seasonViewModel.seasonUIState.toList(results)
        }

        /** When */
        seasonViewModel.getSeasonDetail("1", 1)

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].error).isNull()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNotNull()
        assertThat(results[2].error?.errorMessage).isNotNull()
        assertThat(results[2].error?.displayTryAgainBtn).isFalse()
        assertThat(results[2].error?.notFoundError).isTrue()
        assertThat(results[2].error?.errorMessage).isEqualTo(R.string.not_found_error)
        assertThat(results[2].season).isNull()

        coVerify { tvRepository.getDetailSeason("1", 1) }
        job.cancel()
    }
}