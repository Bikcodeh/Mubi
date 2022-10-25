package com.bikcodeh.mubi.data.local.db.converter

import androidx.room.TypeConverter
import com.bikcodeh.mubi.domain.entity.SeasonEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class SeasonConverter {

    @TypeConverter
    fun fromSeasonList(value: List<SeasonEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SeasonEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSeasonList(value: String?): List<SeasonEntity> {
        if (value == null) {
            return Collections.emptyList()
        }
        val gson = Gson()
        val type = object : TypeToken<List<SeasonEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}