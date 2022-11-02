package com.bikcodeh.mubi.presentation.screens.detail

import com.bikcodeh.mubi.core_test.util.CoroutineRule
import com.bikcodeh.mubi.domain.R
import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.usecase.GetDetailTvShowUC
import com.bikcodeh.mubi.domain.usecase.SetAsFavoriteUC
import com.bikcodeh.mubi.domain.usecase.UpdateTvShowUC
import com.google.common.truth.Truth.assertThat
import io.mockk.slot
import io.mockk.coVerify
import io.mockk.coEvery
import io.mockk.just
import io.mockk.runs
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
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var getDetailTvShowUC: GetDetailTvShowUC

    @RelaxedMockK
    private lateinit var setAsFavoriteUC: SetAsFavoriteUC

    @RelaxedMockK
    private lateinit var updateTvShowUC: UpdateTvShowUC

    private lateinit var detailViewModel: DetailViewModel

    private val slot = slot<String>()

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(
            getDetailTvShowUC,
            setAsFavoriteUC,
            updateTvShowUC,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getDetailState() {
        assertThat(detailViewModel.detailState.value).isInstanceOf(DetailState::class.java)
    }

    @Test
    fun `getDetailState initial state`() {
        assertThat(detailViewModel.detailState.value).isEqualTo(DetailState())
        assertThat(detailViewModel.detailState.value.tvShow).isNull()
        assertThat(detailViewModel.detailState.value.error).isNull()
        assertThat(detailViewModel.detailState.value.isLoading).isFalse()
    }

    @Test
    fun `getDetailById should be successful`() = runTest {
        /** Given */
        val tvShow = TVShow(
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
        coEvery { getDetailTvShowUC(capture(slot)) } returns Result.Success(tvShow)
        val results = arrayListOf<DetailState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.detailState.toList(results)
        }

        /** When */
        detailViewModel.getDetailById("1", TvShowType.POPULAR.tvName, isFavorite = false)

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].error).isNull()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNull()
        assertThat(results[2].tvShow).isNotNull()
        assertThat(results[2].tvShow?.name).isEqualTo("test")

        coVerify { getDetailTvShowUC("1") }
        coVerify { updateTvShowUC(tvShow) }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Http exception)`() = runTest {
        /** Given */
        coEvery { getDetailTvShowUC(capture(slot)) } returns Result.Error(401, "error")
        val results = arrayListOf<DetailState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.detailState.toList(results)
        }

        /** When */
        detailViewModel.getDetailById("1", TvShowType.POPULAR.tvName, isFavorite = false)

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
        assertThat(results[2].tvShow).isNull()

        coVerify { getDetailTvShowUC("1") }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Connectivity)`() = runTest {
        /** Given */
        coEvery { getDetailTvShowUC(capture(slot)) } returns Result.Exception(java.io.IOException("error"))
        val results = arrayListOf<DetailState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.detailState.toList(results)
        }

        /** When */
        detailViewModel.getDetailById("1", TvShowType.POPULAR.tvName, isFavorite = false)

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
        assertThat(results[2].tvShow).isNull()

        coVerify { getDetailTvShowUC("1") }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Unknown)`() = runTest {
        /** Given */
        coEvery { getDetailTvShowUC(capture(slot)) } returns Result.Exception(
            IllegalAccessException(
                "error"
            )
        )
        val results = arrayListOf<DetailState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.detailState.toList(results)
        }

        /** When */
        detailViewModel.getDetailById("1", TvShowType.POPULAR.tvName, isFavorite = false)

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
        assertThat(results[2].tvShow).isNull()

        coVerify { getDetailTvShowUC("1") }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Internet connection)`() = runTest {
        /** Given */
        coEvery { getDetailTvShowUC(capture(slot)) } returns Result.Exception(UnknownHostException("error"))
        val results = arrayListOf<DetailState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.detailState.toList(results)
        }

        /** When */
        detailViewModel.getDetailById("1", TvShowType.POPULAR.tvName, isFavorite = false)

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
        assertThat(results[2].tvShow).isNull()

        coVerify { getDetailTvShowUC("1") }
        job.cancel()
    }

    @Test
    fun `getDetailById should handle result error (Not Found)`() = runTest {
        /** Given */
        coEvery { getDetailTvShowUC(capture(slot)) } returns Result.Error(404, "error")
        val results = arrayListOf<DetailState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.detailState.toList(results)
        }

        /** When */
        detailViewModel.getDetailById("1", TvShowType.POPULAR.tvName, isFavorite = false)

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
        assertThat(results[2].tvShow).isNull()

        coVerify { getDetailTvShowUC("1") }
        job.cancel()
    }

    @Test
    fun setIsFavorite() = runTest {
        /** Given */
        coEvery { setAsFavoriteUC(tvShowId = "1", isFavorite = true) } just runs

        /** When */
        detailViewModel.setIsFavorite(tvShowId = "1", isFavorite = true)

        /** Then */
        coVerify { setAsFavoriteUC(tvShowId = "1", isFavorite = true) }
    }
}