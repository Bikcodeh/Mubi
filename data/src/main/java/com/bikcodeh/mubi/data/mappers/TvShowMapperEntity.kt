package com.bikcodeh.mubi.data.mappers

import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.common.Mapper
import com.bikcodeh.mubi.domain.model.TVShow

class TvShowMapperEntity : Mapper<TvShowEntity, TVShow> {

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
                category
            )
        }
    }
}