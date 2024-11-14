package com.example.gustavoconceicao_rm93039.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gustavoconceicao_rm93039.model.ItemModel

@Database(entities = [ItemModel::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}