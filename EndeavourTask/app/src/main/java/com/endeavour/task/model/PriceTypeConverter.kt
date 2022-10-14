package com.endeavour.task.model

import androidx.room.TypeConverter
import org.json.JSONObject

class PriceTypeConverter {

    @TypeConverter
    fun fromSource(prices: ArrayList<Price>): String {
        val price = prices[0]
        return JSONObject().apply {
            put("message", price.message)
            put("value", price.value)
            put("isOfferPrice", price.isOfferPrice)
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): ArrayList<Price> {
        val json = JSONObject(source)
        val list = ArrayList<Price>()
        list.add(Price(json.getString("message"),
            json.getDouble("value").toFloat(),
            json.getBoolean("isOfferPrice")))
        return list
    }
}