package com.example.gustavoconceicao_rm93039

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.gustavoconceicao_rm93039.viewmodel.ItemAdapter
import com.example.gustavoconceicao_rm93039.viewmodel.ItemViewModel
import com.example.gustavoconceicao_rm93039.viewmodel.itemViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dicas de Economia de Energia"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = ItemAdapter(emptyList())
        recyclerView.adapter = itemsAdapter

        val button = findViewById<Button>(R.id.button)
        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val editTextDesc = findViewById<EditText>(R.id.editTextDescricao)

        button.setOnClickListener {
            if (editTextTitulo.text.isEmpty()) {
                editTextTitulo.error = "Titulo não pode ser vazio"
                return@setOnClickListener
            }
            if (editTextDesc.text.isEmpty()) {
                editTextDesc.error = "Descrição não pode ser vazia"
                return@setOnClickListener
            }

            val novoTitulo = editTextTitulo.text.toString()
            val novaDesc = editTextDesc.text.toString()

            viewModel.insert(novoTitulo, novaDesc)

            editTextTitulo.text.clear()
            editTextDesc.text.clear()
        }

        val viewModelFactory = itemViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemViewModel::class.java)


        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.search(it).observe(this@MainActivity) { dicas ->
                        itemsAdapter.updateItems(dicas)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.search(it).observe(this@MainActivity) { dicas ->
                        itemsAdapter.updateItems(dicas)
                    }
                }
                return false
            }
        })

        viewModel.itemsLiveData.observe(this) {items ->
            itemsAdapter.updateItems(items)
        }
    }
}