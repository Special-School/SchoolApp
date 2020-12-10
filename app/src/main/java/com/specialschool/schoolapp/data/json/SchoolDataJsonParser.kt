package com.specialschool.schoolapp.data.json

import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.specialschool.schoolapp.model.School
import java.io.InputStream
import kotlin.jvm.Throws

object SchoolDataJsonParser {

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun parseJsonData(unprocessedData: InputStream): List<School> {
        TODO("Not yet implemented")
    }

    fun normalize(data: List<SchoolTemp>): List<School> {
        TODO("Not yet implemented")
    }
}
