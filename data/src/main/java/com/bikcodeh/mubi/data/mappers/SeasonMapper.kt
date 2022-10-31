package com.bikcodeh.mubi.data.mappers

import com.bikcodeh.mubi.domain.common.Mapper
import com.bikcodeh.mubi.domain.entity.SeasonEntity
import com.bikcodeh.mubi.domain.model.Season
import javax.inject.Inject

class SeasonMapper @Inject constructor(): Mapper<Season, SeasonEntity> {
    override fun map(input: Season): SeasonEntity {
        return with(input) {
            SeasonEntity(id, name, posterPath, totalEpisodes, overview, seasonNumber)
        }
    }
}