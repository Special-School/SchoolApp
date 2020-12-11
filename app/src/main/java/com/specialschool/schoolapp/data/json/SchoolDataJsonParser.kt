package com.specialschool.schoolapp.data.json

import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.JsonReader
import com.specialschool.schoolapp.model.*
import java.io.InputStream
import java.time.LocalDate
import kotlin.jvm.Throws

object SchoolDataJsonParser {

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun parseJsonData(unprocessedData: InputStream): SchoolData {
        val jsonReader = JsonReader(unprocessedData.reader())

        val gson = GsonBuilder()
            .registerTypeAdapter(SchoolTemp::class.java, SchoolDeserializer())
            .create()

        val tempData: SchoolDataTemp = gson.fromJson(jsonReader, SchoolDataTemp::class.java)

        return normalize(tempData)
    }

    private fun normalize(data: SchoolDataTemp): SchoolData {
        val schools = mutableListOf<School>()

        // TODO: LocalDate.parse -> MIN_SDK >= 26(oreo) ...
        data.schools.forEach { school: SchoolTemp ->
            val item = School(
                id = "school",
                province = school.region,
                type = SchoolType.fromString(school.type),
                name = school.name,
                category = Disability.fromString(school.category),
                principalName = school.principalName,
                authDate = LocalDate.parse(school.authDate),
                openDate = LocalDate.parse(school.openDate),
                contact = Contact(
                    school.principalNumber,
                    school.adminNumber,
                    school.staffRoomNumber
                ),
                faxNumber = school.faxNumber,
                zipCode = school.zipCode,
                address = school.address,
                website = school.website,
                coordinate = Coordinate(school.latitude, school.longitude)
            )
            schools.add(item)
        }

        return SchoolData(
            schools = schools,
            version = data.version
        )
    }
}

data class SchoolDataTemp(
    val schools: List<SchoolTemp>,
    val version: Int
)
