package com.example.yourpoints.data.database.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.room.TypeConverter
import com.example.yourpoints.domain.model.GenericoDomain
import com.example.yourpoints.domain.model.GenericoPlayerDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenericoPlayerConverter {
//
//    @TypeConverter
//    fun fromGenericoDomain(genericoPlayerDomain: List<GenericoPlayerDomain>): String {
//        return  Gson().toJson(genericoPlayerDomain)
//    }
//
//    @TypeConverter
//    fun toAddress(data: String): List<GenericoPlayerDomain> {
//        val gson = Gson()
//        val listType = object : TypeToken<List<GenericoPlayerDomain>>() {}.type
//        return gson.fromJson(data, listType)
//    }
}