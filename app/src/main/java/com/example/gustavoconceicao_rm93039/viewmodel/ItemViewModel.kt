package com.example.gustavoconceicao_rm93039.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import androidx.room.Room
import com.example.gustavoconceicao_rm93039.data.ItemDao
import com.example.gustavoconceicao_rm93039.data.ItemDatabase
import com.example.gustavoconceicao_rm93039.model.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.invoke.TypeDescriptor

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val itemDao: ItemDao
    val itemsLiveData: LiveData<List<ItemModel>>

    init {
        val database = Room.databaseBuilder(
            getApplication(),
            ItemDatabase::class.java,
            "dica_database"
        ).build()

        itemDao = database.itemDao()
        itemsLiveData = itemDao.getAll()
    }

    fun insert(titulo: String, descricao: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = ItemModel(titulo = titulo, descricao = descricao)
            itemDao.insert(newItem)
        }
    }

    fun search(query: String): LiveData<List<ItemModel>> {
        val searchQuery = "%$query%"
        return itemDao.search(searchQuery)
    }

}