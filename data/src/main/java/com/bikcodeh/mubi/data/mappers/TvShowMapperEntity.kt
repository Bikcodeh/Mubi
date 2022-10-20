package com.bikcodeh.mubi.data.mappers

import com.bikcodeh.mubi.data.local.entity.TvShowEntity
import com.bikcodeh.mubi.domain.common.Mapper
import com.bikcodeh.mubi.domain.model.TVShow

class TvShowMapperEntity : Mapper<TvShowEntity, TVShow> {

    override fun map(input: TvShowEntity): TVShow {
        return with(input) {
            TVShow(
                backdropPath,
                firstAirDate,
                genreIds,
                id,
                name,
                originCountry,
                originalLanguage,
                originalName,
                overview,
                popularity,
                posterPath,
                voteAverage,
                voteCount,
                isFavorite
            )
        }
    }
}