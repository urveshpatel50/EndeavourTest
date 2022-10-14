package com.endeavour.task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.endeavour.task.model.PriceTypeConverter
import com.endeavour.task.model.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(PriceTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
