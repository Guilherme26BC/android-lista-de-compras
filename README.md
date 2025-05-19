
# Checkpoint 3

## Android lista de compras

Aluno Guilherme Bezerra Carvalho

RM 550282

### Descrição do projeto

Esse projeto consiste em um simulador de um carrinho de compras utilizando recyclerview e banco de dados para a persistência dos dados.

### Ferramentas utilizadas

- Kotlin

- Gradle

- Dependências

- RecyclerView

- Appcompat

- Room

- Coroutines

-

## Estrutura do projeto

```text

app/src/androidTest/java/guilherme26bc/com/github/android_lista_de_compras

├── model/

│ └── ItemModel.kt

├── data/

│ ├── ItemDao.kt

│ └── ItemDataBase.kt

├── ItemView/

│ ├──ItemsAdapter.kt

│ ├──ItemsViewModel.kt

│ └──ItemsViewModelFactory.kt

└── MainActivity.kt

```

## Ordem de explicação

* [ItemModel.kt](#PacoteDataClasseItemModel)

* [ItemDao.kt](#PacoteDataItemDao)

* [ItemDataBase.kt](#PacotedataItemDataBase)

* [ItemAdapter.kt](#PacoteViewModelItemAdapter)

* [ItemsViewModel.kt](#PacoteViewModel:ItemsViewModel)

* [ItemViewModelFactory.kt](#PacoteViewModel:ItemViewModelFactory)

* [MainActivity.kt](#MainActivity)

## Pacote Data Classe ItemModel

### Descrição

Classe do objeto ItemModel, também responsável por ser a nossa tabela no banco de dados

```

@Entity

data class  ItemModel (

@PrimaryKey(autoGenerate = true)

val id: Int = 0,

val  name: String

)

```

- @Entity - é responsável por indicar ao Room que essa classe representa uma entidade (tabela)

- A classe foi criada como "data class" que já cria alguns métodos mais utilizados como por exemplo o método toString()

- Além disso temos os atributos da classe id do tipo Int iniciado em 0, e name do tipo String

- @PrimaryKey(autoGenerate = true) - indica que o atributo é a primary  key da tabela com geração automática

## Pacote Data ItemDao

### Descricao

A classe ItemDao é uma interface responsável pela criação dos métodos que servirão de script para o banco de dados.

```

@Dao

interface ItemDao {

@Query("SELECT * FROM ItemModel")

fun  getAll(): LiveData<List<ItemModel>>

@Insert

fun  insert(item: ItemModel)

@Delete

fun delete(item: ItemModel)

}

```

- @Dao - anotation que indica que essa classe serve como dao

- É uma interface, ou seja, foi feita para ser implementada em outras classes

- @Query - anotation que indica a query, recebe como assinatura a query desejada, que será executada, no caso select * from  ItemModel"

- O método dela retorna um LiveData de uma lista de ItemModel

- @Insert e @Delete - são anotations de querys já prontas de inserção e remoção, respectivamente.

- métodos que pedem como parâmetro o item que será inserido ou removido.

## Pacote data ItemDataBase

### Descrição

Classe que representa o branco de dados em si e faz a sua conexão com a aplicação.

```

@Database(entities = [ItemModel::class], version = 1)

abstract class  ItemDatabase : RoomDatabase(){

abstract fun  itemDao():ItemDao

}

```

- @Database( entities....) - essa anotation representa que a classe é uma representação do banco de dados, e passamos como assinatura as entites, que no nosso caso é a penas a classe ItemModel e a versão do banco de dados.

- A classe é criada de forma abstrata pois não será instanciada

- Os métodos dessa classe são os pontos de acesso com as classes, no nosso caso é o ponto de acesso com a classe ItemDao.

## Pacote ViewModel  ItemAdapter

### Descrição

Essa classe serve como um adaptador do recycler  view ao nosso código

```

CLASSE COMPLETA

class  ItemsAdapter (private  val  onItemRemoved: (ItemModel) -> Unit):

RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

private var items = listOf<ItemModel>()

override  fun  onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ItemViewHolder {

val  view = LayoutInflater.from(parent.context)

.inflate(R.layout.item_layout, parent, false)

// Cria e retorna um novo ViewHolder.

return  ItemViewHolder(view)

}

override  fun  onBindViewHolder(holder: ItemsAdapter.ItemViewHolder, position: Int) {

val item = items[position]

holder.bind(item)

}

override  fun  getItemCount(): Int = items.size

inner  class  ItemViewHolder(view: View): RecyclerView.ViewHolder(view){

val  textView = view.findViewById<TextView>(R.id.textViewItem)

val  button = view.findViewById<ImageButton>(R.id.imageButton)

fun  bind(item: ItemModel){

textView.text = item.name

button.setOnClickListener(){

onItemRemoved(item)

}

}

}

fun  updateItems(newItems: List<ItemModel>) {

items = newItems

notifyDataSetChanged()

}

}

```

```

class  ItemsAdapter (private  val  onItemRemoved: (ItemModel) -> Unit):

RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

private var items = listOf<ItemModel>()

```

- A classe herda de recyclerview.adapter e usao o itemsAdapter.ItemViewHolder, que é uma classe interna.

- A classe também possui como parâmetro em seu construtor a função onItemRemoved que recebe um ItemModel e não retorna nada, para quando um item for removido.

- nesse trecho também foi criado o atributo items que é uma lista de itemModel

```

override  fun  onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ItemViewHolder {

val  view = LayoutInflater.from(parent.context)

.inflate(R.layout.item_layout, parent, false)

return  ItemViewHolder(view)

}

```

- A classe adapter possui alguns métodos de implementação obrigatória, como o onCreate

- Aqui temos a criação de um view, utilizando o LayoutInflater, para inflar/ alimentar o viewHolder utilizando o itemLayout, que foi recuperado por um parse.

- O método retorna uma chamada do ItemViewHolder passando como parametro o view criado.

```

override  fun  onBindViewHolder(holder: ItemsAdapter.ItemViewHolder, position: Int) {

val item = items[position]

holder.bind(item)

}

```

- Outro método de implementação obrigatória é o Bind, toda vez que atualiza o viewHolder o bind é chamado

- criamos uma variavel item que recebe o item da posição atual da lista

- o método atualiza o viewholder com o item da lista.

```

override  fun  getItemCount(): Int = items.size

```

- Último método de implementação obrigatória, trás o itemCount no caso ele retorna o tamanho da nossa lista criada no começo da classe.

```

inner  class  ItemViewHolder(view: View): RecyclerView.ViewHolder(view){

val  textView = view.findViewById<TextView>(R.id.textViewItem)

val  button = view.findViewById<ImageButton>(R.id.imageButton)

fun  bind(item: ItemModel){

textView.text = item.name

button.setOnClickListener(){

onItemRemoved(item)

}

}

}

```

- Em seguida fizemos a criação da classe interna ItemViewHolder que recebe uma view em seu construtor e herda de recycerView.ViewHolder

- Dentro dessa classe criamos duas variáveis que recebem objetos do xml após passarem por um parse, um textView e um botão

- Também criamos a função bind que recebe um itemModel

- No método atribuímos o texto do textView com o nome do item recebido

- Em seguida adicionamos um listener para o botão para quando ele for clicado,

- Quando o listener é ativado ele chama o onItemRemoved e passa o item a ser removido como parâmetro

```

fun  updateItems(newItems: List<ItemModel>) {

items = newItems

notifyDataSetChanged()

}

```

- A classe principal é encerrada com o método updateItems que recebe como parâmetro uma lista de ItemModel

- em seguida atualizamos a nossa lista de itens criada no começo da classe com a lista recebida como parâmetro no método

- Pra finalizar chamamos o método notifyDataChanged() que avisa ao recyclerView que aconteceu uma mudança de dados e a função bind precisa ser chamado.

## Pacote ViewModel: ItemsViewModel

### Descrição

Gerência e prepara os dados para uma interface gráfica e para o banco de dados.

```

CODIGO COMPLETO

class  ItemsViewModel(application: Application) : AndroidViewModel(application) {

private  val  itemDao: ItemDao

val  itemsLiveData: LiveData<List<ItemModel>>

init {

val  database = Room.databaseBuilder(

getApplication(),

ItemDatabase::class.java,

"items_database"

).build()

itemDao = database.itemDao()

itemsLiveData = itemDao.getAll()

}

fun  addItem(item: String) {

viewModelScope.launch(Dispatchers.IO) {

val  newItem = ItemModel(name = item)

itemDao.insert(newItem)

}

}

fun  removeItem(item: ItemModel) {

viewModelScope.launch(Dispatchers.IO) {

itemDao.delete(item)

}

}

}

```

- A classe começa passando como parâmetro pra superclasse um Applicatione herda de AndroidViewModel

- Instanciamos um ItemDao para ser utilizado na classe e também um LiveData de uma lista de ItemModel

```

CÓDIGO EM PARTES

init {

val  database = Room.databaseBuilder(

getApplication(),

ItemDatabase::class.java,

"items_database"

).build()

itemDao = database.itemDao()

itemsLiveData = itemDao.getAll()

}

```

- Em seguida usamos Init para inicializar as variáveis

- criamos um objeto dataBase que recebe um DataBase.builder do Room

- Dentro do builder do dataBase passamos um getApplication() que retorna um Application (contexto), passamos também a nossa classe de banco de dados ItemsDataBase e passamos o nome do banco

- Finalizando com o .build

- também inicializamos o nosso ItemDao passando o itemDao do banco de dados

- e pra finalizar o Init, inicializamos o LiveData passando o retorno do ItemDao.getAll

```

fun  addItem(item: String) {

viewModelScope.launch(Dispatchers.IO) {

val  newItem = ItemModel(name = item)

itemDao.insert(newItem)

}

}

```

- Também criamos o método addItem que recebe uma String de parâmetro

- Em seguida abrimos um bloco do coroutines o viewModelScope.launch passando Dispatcher.IO como parâmetro, que é muito usado em rotinas de entrada e saída

- Criamos um objeto newItem do tipo ItemModel e atribuindo o atributo nome do objeto a String recebida.

- Para finalizar chamamos o itemDao e inserimos o item criado.

```

fun  removeItem(item: ItemModel) {

viewModelScope.launch(Dispatchers.IO) {

itemDao.delete(item)

}

}

```

- A última função da classe é a removeItem que recebe um ItemModel de parâmetro

- Assim como no método de adicionar também abrimos um bloco viewModelScope igual ao do adicionar

- dentro do bloco chamamos o itemDao.delete passando o item recebido como parâmetro.

## Pacote ViewModel: ItemViewModelFactory

### Descrição

```

class  ItemsViewModelFactory(private  val  application: Application): ViewModelProvider.Factory {

override  fun <T : ViewModel> create(modelClass: Class<T>): T {

if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {

@Suppress("UNCHECKED_CAST")

return  ItemsViewModel(application) as T

}

throw  IllegalArgumentException("Unknown  ViewModel  class")

}

}

```

- Criamos a nossa classe recebendo um application como parâmetro, porque nossa ItemViewModel precisa dele como parâmetro. A classe também implementa a interface ViewModelProvider.Factory para criar a nossa viewModel.

- A função declarada, é a que será chamada para criar um ViewModel passando um T viewModel genérico.

- Em seguida temos um "if" que verifica se o objeto que estamos tentando criar é do tipo ItemViewModel, se sim, ele cria o objeto passando o application e o retorna usando o as T para previnir erro.

- para finalizar lançamos uma exceção de "viewModel desconhecida"

## MainActivity

### Descrição

Classe principal da aplicação que juntamos todas as outras classes e executamos.

```

class  MainActivity : AppCompatActivity() {

private  lateinit var viewModel:ItemsViewModel

override  fun  onCreate(savedInstanceState: Bundle?) {

super.onCreate(savedInstanceState)

setContentView(R.layout.activity_main)

val toolbar: Toolbar = findViewById(R.id.toolbar)

setSupportActionBar(toolbar)

// Define o título da barra de ação.

supportActionBar?.title = "Lista de Compras"

val  recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

val  itemsAdapter = ItemsAdapter { item ->

viewModel.removeItem(item)

}

recyclerView.adapter= itemsAdapter

val  button = findViewById<Button>(R.id.button)

val  editText = findViewById<EditText>(R.id.editText)

button.setOnClickListener{

if(editText.text.isEmpty()){

editText.error ="Preencha um valor" //esse error é um icone no canto

return@setOnClickListener //retorna para o listner

}

viewModel.addItem(editText.text.toString())

editText.text.clear()

}

val  viewModelFactory = ItemsViewModelFactory(application)

viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

viewModel.itemsLiveData.observe(this) { items ->

itemsAdapter.updateItems(items)

}

}

}

```

- Primeiro trocamos o extend para o AppCompatActivity

- Depois criamos um objeto lateinit (cria uma instância, mas fica aguardando a primeira chamada da instância) viewModel do tipo ItemViewModel

- Em seguida iniciamos a função onCreate e também linkamos a mainActivity com o layout xml.

- Dentro do método criamos a uma variável toolBar que recebe um objeto toolbar do xml, e também configuramos ele

- também criamos um RecyclerView que também passa do xml par o kotlin pelo parse do FindViewById

- também instanciamos um ItemAdapter e abrimos um bloco para poder excluir um item

- Em seguida atribuímos esse adapter ao recyclerView

- Também criamos o editText e botão, ambos sendo buscados pelo findById

- Abrimos um listener  on click pro botão

- Dentro do listner temos um if() que verifica se o editText está vazio, se sim ele lança um erro e volta para o listner

- Se o if não for verdadeiro, chamamos o nosso viewModel e usamos o método addItem e atribuimos o valor do editText

- Em seguida limpamos o editText, fechando o listner

- Após o listener instanciamos o viewModelFactory passando a Application

- Também chamamos o nosso viewModel e atribuimos o ViewModelProvider e passamos o viewModelFactory e passamos o ItemViewModel para criar o viewModel

- Finalizando o código o viewModel .itensLiveData.observe, para observar o liveData, para armazenar o status e abrimos um bloco que recebe usa o udateItems do ItemsAdapter.

## Imagem do funcionamento

![imgAntes.png](images%2FimgAntes.png)
![imgDepois.png](images%2FimgDepois.png)
![imgExcluir.png](images%2FimgExcluir.png)