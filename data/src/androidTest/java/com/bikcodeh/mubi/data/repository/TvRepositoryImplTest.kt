package com.bikcodeh.mubi.data.repository

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bikcodeh.mubi.core_test.util.CoroutineRule
import com.bikcodeh.mubi.data.local.db.TvShowDatabase
import com.bikcodeh.mubi.data.local.db.dao.TvShowDao
import com.bikcodeh.mubi.data.mappers.SeasonMapper
import com.bikcodeh.mubi.data.mappers.SeasonMapperEntity
import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.mappers.TvShowMapperEntity
import com.bikcodeh.mubi.data.pagination.TvShowRemoteMediator
import com.bikcodeh.mubi.data.remote.response.SeasonDTO
import com.bikcodeh.mubi.data.remote.response.TVResponseDTO
import com.bikcodeh.mubi.data.remote.response.TVShowDTO
import com.bikcodeh.mubi.data.remote.service.TVApi
import com.bikcodeh.mubi.domain.entity.SeasonEntity
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.repository.TvRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import java.io.IOException

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class TvRepositoryImplTest {

    private val seasonEntity = SeasonEntity(
        id = 1,
        name = "season test",
        posterPath = null,
        totalEpisodes = null,
        overview = "test overview",
        seasonNumber = 1
    )

    private val tvShowEntity = TvShowEntity(
        backdropPath = "",
        firstAirDate = "",
        id = "1",
        name = "test name",
        originalLanguage = "",
        originalName = "",
        overview = "overview show",
        popularity = 4.0,
        posterPath = "",
        voteAverage = 2.0,
        voteCount = 10,
        isFavorite = false,
        category = "popular",
        seasons = listOf(seasonEntity)
    )

    private val seasonDto = SeasonDTO(
        id = 1,
        name = "season test",
        posterPath = null,
        totalEpisodes = null,
        overview = "test overview",
        seasonNumber = 1
    )

    private val tvShowDto = TVShowDTO(
        backdropPath = "",
        firstAirDate = "",
        id = 1,
        name = "test name",
        originalLanguage = "",
        originalName = "",
        overview = "overview show",
        popularity = 4.0,
        posterPath = "",
        voteAverage = 2.0,
        voteCount = 10,
        seasons = listOf(seasonDto)
    )

    private val mockTvShows = listOf(tvShowEntity, tvShowEntity.copy(name = "second", id = "2"))

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var tvApi: TVApi

    private lateinit var tvShowDao: TvShowDao

    private val seasonMapper = SeasonMapper()

    private val tvShowMapper = TvShowMapper(seasonMapper)

    private val seasonMapperEntity = SeasonMapperEntity()

    private val tvShowMapperEntity = TvShowMapperEntity(seasonMapperEntity)

    private lateinit var tvShowDatabase: TvShowDatabase

    private lateinit var tvRepository: TvRepository

   /* @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        tvShowDatabase = Room.inMemoryDatabaseBuilder(
            context,
            TvShowDatabase::class.java
        ).build()
        tvShowDao = tvShowDatabase.tvShowDao()
    }*/

   /* @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val response = mockk<Response<TVResponseDTO>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns TVResponseDTO(
            page = 1, results = listOf(tvShowDto), totalPages = 1
        )
        coEvery { tvApi.getPopularTvShows(1) } returns response

        val remoteMediator = TvShowRemoteMediator(
            tvShowDatabase,
            tvApi,
            TvShowType.POPULAR,
            tvShowMapper
        )

        val pagingState = PagingState<Int, TvShowEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertThat(result is RemoteMediator.MediatorResult.Success).isFalse()
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isFalse()
    }
*/
    @Test
    fun test_data() {
        assertFalse(true)
    }


   /* @After
    @Throws(IOException::class)
    fun closeDb() {
        tvShowDatabase.close()
    }*/
}