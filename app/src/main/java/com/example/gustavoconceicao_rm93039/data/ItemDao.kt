package com.example.gustavoconceicao_rm93039.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gustavoconceicao_rm93039.model.ItemModel

@Dao
interface ItemDao {

    @Query("SELECT * FROM ItemModel")
    fun getAll(): LiveData<List<ItemModel>>

    @Insert
    fun insert(item: ItemModel)

    @Query("SELECT * FROM ItemModel WHERE titulo LIKE :searchQuery")
    fun search(searchQuery: String): LiveData<List<ItemModel>>

}