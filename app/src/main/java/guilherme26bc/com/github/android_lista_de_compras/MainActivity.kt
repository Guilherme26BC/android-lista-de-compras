package guilherme26bc.com.github.android_lista_de_compras

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import guilherme26bc.com.github.android_lista_de_compras.viewModel.ItemsAdapter
import guilherme26bc.com.github.android_lista_de_compras.viewModel.ItemsViewModel
import guilherme26bc.com.github.android_lista_de_compras.viewModel.ItemsViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel:ItemsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Define o título da barra de ação.
        supportActionBar?.title = "Lista de Compras"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter= itemsAdapter

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)

        button.setOnClickListener{
            if(editText.text.isEmpty()){
                editText.error ="Preencha um valor" //esse error é um icone no canto
                return@setOnClickListener //retorna para o listner
            }

            viewModel.addItem(editText.text.toString())
            editText.text.clear()
        }

        val viewModelFactory =  ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }
    }
}