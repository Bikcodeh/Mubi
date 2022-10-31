package com.bikcodeh.mubi.data.mappers

import com.bikcodeh.mubi.domain.common.Mapper
import com.bikcodeh.mubi.domain.entity.SeasonEntity
import com.bikcodeh.mubi.domain.model.Season
import javax.inject.Inject

class SeasonMapperEntity @Inject constructor(): Mapper<SeasonEntity, Season> {
    override fun map(input: SeasonEntity): Season {
        return with(input) {
            Season(id, name, posterPath, totalEpisodes, overview, seasonNumber)
        }
    }
}