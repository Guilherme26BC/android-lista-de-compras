package guilherme26bc.com.github.android_lista_de_compras.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import guilherme26bc.com.github.android_lista_de_compras.model.ItemModel
@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemModel")
    fun getAll(): LiveData<List<ItemModel>>
    @Insert
    fun insert(item: ItemModel)
    @Delete
    fun delete(item: ItemModel)
}