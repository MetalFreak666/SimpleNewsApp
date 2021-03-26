package com.example.simplenewsapp.database

import androidx.room.TypeConverter
import com.example.simplenewsapp.data.models.Source

/**
 * Converter class used to convert source entity to room
 */
class Converters {

    @TypeConverter
    fun toSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun fromSource(name: String): Source {
        return Source(name, name)
    }
}