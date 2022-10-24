package com.bikcodeh.mubi.data.mappers

import com.bikcodeh.mubi.domain.common.Mapper
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.TVShow
import javax.inject.Inject

class TvShowMapperEntity @Inject constructor(
    private val seasonMapperEntity: SeasonMapperEntity
) : Mapper<TvShowEntity, TVShow> {

    override fun map(input: TvShowEntity): TVShow {
        return with(input) {
            TVShow(
                backdropPath,
                firstAirDate,
                id,
                name,
                originalLanguage,
                originalName,
                overview,
                popularity,
                posterPath,
                voteAverage,
                voteCount,
                isFavorite,
                category,
                seasons?.map { seasonEntity -> seasonMapperEntity.map(seasonEntity) }
            )
        }
    }
}