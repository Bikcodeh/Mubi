package com.bikcodeh.mubi.data.local.db.converter

import androidx.room.TypeConverter
import com.bikcodeh.mubi.domain.model.Episode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Collections

class EpisodeConverter {

    @TypeConverter
    fun fromEpisodeList(value: List<Episode>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Episode>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toEpisodeList(value: String?): List<Episode>? {
        if (value == null) {
            return Collections.emptyList()
        }
        val gson = Gson()
        val type = object : TypeToken<List<Episode>?>() {}.type
        return gson.fromJson(value, type)
    }
}