package com.bikcodeh.mubi.data.mappers

import com.bikcodeh.mubi.data.local.entity.TvShowEntity
import com.bikcodeh.mubi.domain.common.Mapper
import com.bikcodeh.mubi.domain.model.TVShow

class TvShowMapper : Mapper<TVShow, TvShowEntity> {

    override fun map(input: TVShow): TvShowEntity {
        return with(input) {
            TvShowEntity(
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