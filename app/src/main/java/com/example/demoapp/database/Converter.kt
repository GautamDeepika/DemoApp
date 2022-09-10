package com.example.demoapp.database

import androidx.room.TypeConverter
import com.example.demoapp.data.models.Priority

//this class converts prioirty to string (when writing to DB)and String to Priority (when reading from DB)
class Converter {

    //    converts prioirty to string (when writing to DB)
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    //String to Priority (when reading from DB)
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}