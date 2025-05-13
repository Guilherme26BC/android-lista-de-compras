package guilherme26bc.com.github.android_lista_de_compras.viewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import guilherme26bc.com.github.android_lista_de_compras.R
import guilherme26bc.com.github.android_lista_de_compras.model.ItemModel

class ItemsAdapter (private val onItemRemoved: (ItemModel) -> Unit):
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

        //lista de itens que serão exibidos no recycler view
        private var items = listOf<ItemModel>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
            // Cria e retorna um novo ViewHolder.
            return ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemsAdapter.ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView = view.findViewById<TextView>(R.id.textViewItem)
        val button = view.findViewById<ImageButton>(R.id.imageButton)

        fun bind(item: ItemModel){
            textView.text = item.name
            button.setOnClickListener(){
                onItemRemoved(item)
            }
        }

    }
    fun updateItems(newItems: List<ItemModel>) {
        // Atualiza a lista de itens.
        items = newItems
        // Notifica o RecyclerView que os dados mudaram.
        notifyDataSetChanged()
    }
}