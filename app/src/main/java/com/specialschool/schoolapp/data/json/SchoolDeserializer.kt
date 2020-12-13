package com.specialschool.schoolapp.data.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class SchoolDeserializer : JsonDeserializer<SchoolTemp> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SchoolTemp {
        val obj = json?.asJsonObject!!

        return SchoolTemp(
            region = obj.get("region").asString,
            type = obj.get("type").asString,
            name = obj.get("name").asString,
            category = obj.get("category").asString,
            principalName = obj.get("principalName").asString,
            authDate = obj.get("authDate").asString,
            openDate = obj.get("openDate").asString,
            principalNumber = obj.get("principalNumber").asString,
            adminNumber = obj.get("adminNumber").asString,
            staffRoomNumber = obj.get("staffRoomNumber").asString,
            faxNumber = obj.get("faxNumber").asString,
            zipCode = obj.get("zipCode").asInt,
            address = obj.get("address").asString,
            website = obj.get("website").asString,
            latitude = obj.get("latitude").asDouble,
            longitude = obj.get("longitude").asDouble
        )
    }
}
