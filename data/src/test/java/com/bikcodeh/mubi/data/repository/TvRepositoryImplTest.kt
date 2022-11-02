package com.bikcodeh.mubi.data.repository

import androidx.paging.ExperimentalPagingApi
import com.bikcodeh.mubi.core_test.util.CoroutineRule
import com.bikcodeh.mubi.data.local.db.TvShowDatabase
import com.bikcodeh.mubi.data.local.db.dao.TvShowDao
import com.bikcodeh.mubi.data.mappers.SeasonMapper
import com.bikcodeh.mubi.data.mappers.SeasonMapperEntity
import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.mappers.TvShowMapperEntity
import com.bikcodeh.mubi.data.remote.response.SeasonDTO
import com.bikcodeh.mubi.data.remote.response.TVResponseDTO
import com.bikcodeh.mubi.data.remote.response.TVShowDTO
import com.bikcodeh.mubi.data.remote.service.TVApi
import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.entity.SeasonEntity
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.Season
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.repository.TvRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class TvRepositoryImplTest {

    private val season = Season(
        id = 1,
        name = "season test",
        posterPath = null,
        totalEpisodes = null,
        overview = "test overview",
        seasonNumber = 1
    )

    private val tvShow = TVShow(
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
        seasons = listOf(season)
    )

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

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var tvShowDao: TvShowDao

    @RelaxedMockK
    private lateinit var tvShowDatabase: TvShowDatabase

    @RelaxedMockK
    private lateinit var tvApi: TVApi

    private val seasonMapper = SeasonMapper()

    private val tvShowMapper = TvShowMapper(seasonMapper)

    private val seasonMapperEntity = SeasonMapperEntity()

    private val tvShowMapperEntity = TvShowMapperEntity(seasonMapperEntity)

    private val slot = slot<String>()

    private lateinit var tvRepository: TvRepository

    @Before
    fun setUp() {
        tvRepository = TvRepositoryImpl(
            tvShowDao, tvShowDatabase, tvApi, tvShowMapper, tvShowMapperEntity
        )
    }

    @Test
    fun getTvShows() {
    }

    @Test
    fun searchTvShows() = runTest {
        /** Given */
        coEvery { tvShowDao.searchTvShows("%test%") } returns flowOf(listOf(tvShowEntity))

        /** When */
        tvRepository.searchTvShows("test").collect { result ->

            /** Then */
            assertThat(result).isNotNull()
            assertThat(result.first().name).isEqualTo("test name")
            assertThat(result.first().id).isEqualTo("1")
            assertThat(result.first().seasons.count()).isEqualTo(1)
            assertThat(result.first().seasons).isNotEmpty()
            assertThat(result.first().seasons.first().name).isEqualTo("season test")
        }
        coVerify { tvShowDao.searchTvShows("%test%") }
    }

    @Test
    fun `searchTvShowsRemote should return a list of tv shows successfully`() = runTest {
        /** Given */
        val response = mockk<Response<TVResponseDTO>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns TVResponseDTO(
            page = 1, results = listOf(tvShowDto), totalPages = 2
        )
        coEvery { tvApi.searchTvShow(capture(slot)) } returns response

        /** When */
        val result = tvRepository.searchTvShowsRemote("test")

        /** Then */
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isNotNull()
        assertThat((result).data).isNotEmpty()
        assertThat((result).data.first().name).isEqualTo("test name")
        assertThat(slot.captured).isEqualTo("test")
        verifyAll {
            response.body()
            response.isSuccessful
        }
        coVerify { tvApi.searchTvShow("test") }
    }

    @Test
    fun `searchTvShowsRemote should return a result error`() = runTest {
        /** Given */
        val response = mockk<Response<TVResponseDTO>>()
        every { response.isSuccessful } returns false
        every { response.body() } returns null
        every { response.code() } returns 404
        every { response.message() } returns "error"
        every { response.body() } returns TVResponseDTO(
            page = 1, results = listOf(tvShowDto), totalPages = 2
        )
        coEvery { tvApi.searchTvShow(capture(slot)) } returns response

        /** When */
        val result = tvRepository.searchTvShowsRemote("test")

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).code).isEqualTo(404)
        assertThat((result).message).isEqualTo("error")
        assertThat(slot.captured).isEqualTo("test")
        verifyAll {
            response.body()
            response.isSuccessful
            response.code()
            response.message()
        }
        coVerify { tvApi.searchTvShow("test") }
    }

    @Test
    fun `searchTvShowsRemote should return a result exception`() = runTest {
        /** Given */
        val response = mockk<Response<TVResponseDTO>>()
        every { response.body() } returns TVResponseDTO(
            page = 1, results = listOf(tvShowDto), totalPages = 2
        )
        coEvery { tvApi.searchTvShow(capture(slot)) } throws Exception("error")

        /** When */
        val result = tvRepository.searchTvShowsRemote("test")

        /** Then */
        assertThat(slot.captured).isEqualTo("test")
        assertThat(result).isInstanceOf(Result.Exception::class.java)
        assertThat((result as Result.Exception).exception.message).isEqualTo("error")
        coVerify { tvApi.searchTvShow("test") }
    }

    @Test
    fun `getDetailTvShow should be successful`() = runTest {
        /** Given */
        val response = mockk<Response<TVShowDTO>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns tvShowDto
        coEvery { tvApi.getDetailTvShow(capture(slot)) } returns response

        /** When */
        val result = tvRepository.getDetailTvShow("1")

        /** Then */
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isNotNull()
        assertThat((result).data.name).isEqualTo("test name")
        assertThat((result).data.popularity).isEqualTo(4.0)
        assertThat(slot.captured).isEqualTo("1")
        verifyAll {
            response.body()
            response.isSuccessful
        }
        coVerify { tvApi.getDetailTvShow("1") }
    }

    @Test
    fun `getDetailTvShow should return a result error`() = runTest {
        /** Given */
        val response = mockk<Response<TVShowDTO>>()
        every { response.isSuccessful } returns false
        every { response.body() } returns null
        every { response.code() } returns 404
        every { response.message() } returns "error"
        coEvery { tvApi.getDetailTvShow(capture(slot)) } returns response

        /** When */
        val result = tvRepository.getDetailTvShow("1")

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).code).isEqualTo(404)
        assertThat((result).message).isEqualTo("error")
        assertThat(slot.captured).isEqualTo("1")
        verifyAll {
            response.body()
            response.isSuccessful
            response.code()
            response.message()
        }
        coVerify { tvApi.getDetailTvShow("1") }
    }

    @Test
    fun `getDetailTvShow should return a result exception`() = runTest {
        /** Given */
        val exception = UnknownHostException("error")
        coEvery { tvApi.getDetailTvShow(capture(slot)) } throws exception

        /** When */
        val result = tvRepository.getDetailTvShow("1")

        /** Then */
        assertThat(result).isInstanceOf(Result.Exception::class.java)
        assertThat((result as Result.Exception).exception.message).isEqualTo("error")
        assertThat(slot.captured).isEqualTo("1")
        coVerify { tvApi.getDetailTvShow("1") }
    }

    @Test
    fun setAsFavorite() = runTest {
        /** Given */
        coEvery { tvShowDao.setIsFavorite("1", true) } just runs

        /** When */
        tvRepository.setAsFavorite(true, "1")

        /** Then */
        coVerify { tvShowDao.setIsFavorite("1", true) }
    }

    @Test
    fun updateTvShow() = runTest {
        /** Given */
        coEvery { tvShowDao.updateTvShow(tvShowEntity) } just runs

        /** When */
        tvRepository.updateTvShow(tvShow)

        /** Then */
        coVerify { tvShowDao.updateTvShow(tvShowEntity) }
    }

    @Test
    fun getTvShowByIdLocal() = runTest {
        /** Given */
        coEvery { tvShowDao.getTvShowById("1") } returns tvShowEntity

        /** When */
        val result = tvRepository.getTvShowByIdLocal("1")

        /** Then */
        assertThat(result).isNotNull()
        assertThat(result?.name).isEqualTo("test name")
        assertThat(result?.id).isEqualTo("1")
        assertThat(result?.seasons?.count()).isEqualTo(1)
        assertThat(result?.seasons).isNotEmpty()
        assertThat(result?.seasons?.first()?.name).isEqualTo("season test")
        coVerify { tvShowDao.getTvShowById("1") }
    }

    @Test
    fun `getFavoritesTvShows should return a list of tv shows`() = runTest {
        /** Given */
        coEvery { tvShowDao.getFavorites() } returns listOf(tvShowEntity)

        /** When */
        val result = tvRepository.getFavoritesTvShows()

        /** Then */
        assertThat(result).isNotEmpty()
        assertThat(result).isNotNull()
        assertThat(result.count()).isEqualTo(1)
        assertThat(result.first().name).isEqualTo("test name")
        assertThat(result.first().seasons.count()).isEqualTo(1)
        assertThat(result.first().seasons).isNotEmpty()
        assertThat(result.first().seasons.first().name).isEqualTo("season test")
        coVerify { tvShowDao.getFavorites() }
    }

    @Test
    fun `getFavoritesTvShows should return a empty list of tv shows`() = runTest {
        /** Given */
        coEvery { tvShowDao.getFavorites() } returns listOf()

        /** When */
        val result = tvRepository.getFavoritesTvShows()

        /** Then */
        assertThat(result).isEmpty()
        coVerify { tvShowDao.getFavorites() }
    }

    @Test
    fun `getDetailSeason should return a season successfully`() = runTest {
        /** Given */
        val response = mockk<Response<SeasonDTO>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns seasonDto
        coEvery { tvApi.getDetailSeason("1", 2) } returns response

        /** When */
        val result = tvRepository.getDetailSeason("1", 2)

        /** Then */
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isNotNull()
        assertThat((result).data.name).isEqualTo("season test")
        assertThat((result).data.overview).isEqualTo("test overview")
        assertThat((result).data.seasonNumber).isEqualTo(1)
        verifyAll {
            response.body()
            response.isSuccessful
        }
        coVerify { tvApi.getDetailSeason("1", 2) }
    }

    @Test
    fun `getDetailSeason should return a result error`() = runTest {
        /** Given */
        val response = mockk<Response<SeasonDTO>>()
        every { response.isSuccessful } returns false
        every { response.body() } returns null
        every { response.code() } returns 404
        every { response.message() } returns "error"
        coEvery { tvApi.getDetailSeason("1", 2) } returns response

        /** When */
        val result = tvRepository.getDetailSeason("1", 2)

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).code).isEqualTo(404)
        assertThat((result).message).isEqualTo("error")
        verifyAll {
            response.body()
            response.isSuccessful
            response.code()
            response.message()
        }
        coVerify { tvApi.getDetailSeason("1", 2) }
    }

    @Test
    fun `getDetailSeason should return a result exception`() = runTest {
        /** Given */
        val exception = UnknownHostException("error")
        coEvery { tvApi.getDetailSeason("1", 2) } throws exception

        /** When */
        val result = tvRepository.getDetailSeason("1", 2)

        /** Then */
        assertThat(result).isInstanceOf(Result.Exception::class.java)
        assertThat((result as Result.Exception).exception.message).isEqualTo("error")
        coVerify { tvApi.getDetailSeason("1", 2) }
    }
}