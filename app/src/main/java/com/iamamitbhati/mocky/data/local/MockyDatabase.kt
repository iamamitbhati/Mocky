package com.iamamitbhati.mocky.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iamamitbhati.mocky.data.local.dao.ProductDao
import com.iamamitbhati.mocky.model.ProductDetail

@Database(
  entities = [ProductDetail::class],
  version = 1,
  exportSchema = true
)
abstract class MockyDatabase : RoomDatabase() {

  abstract fun productDao(): ProductDao

}
