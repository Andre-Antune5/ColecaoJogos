package pt.ipg.colecaojogos

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class JogosContentProvider : ContentProvider() {
    private var bdOpenHelper : BDJogosOpenHelper? = null
    override fun onCreate(): Boolean {
        bdOpenHelper = BDJogosOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri, //URI = universal Resource Identifier
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdOpenHelper!!.readableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_CATEGORIAS, URI_CATEGORIA_ID -> TabelaCategorias(bd)
            URI_JOGOS, URI_JOGOS_ID -> TabelaJogos(bd)
            else -> null
        }

        val id = uri.lastPathSegment // content://pt.ipg.colecaojogos/categorias/5 , lastPathSegment = 5 neste caso, que corresponde ao ID
        val (selecao, argsSel) = when (endereco) { //content://pt.ipg.colecaojogos/jogos/5 -> selection = "_id = ?" , selectionArgs = {'5'}
            URI_CATEGORIA_ID, URI_JOGOS_ID -> Pair("${BaseColumns._ID}=?", arrayOf(id))
            else -> Pair(selection, selectionArgs)
        } //selection = "nome LIKE '?%' , selectionArgs = { 'a' } -> para pesquisar por um jogo em que o nome começe por 'a'

        return tabela?.consulta(projection as Array<String>, selecao, argsSel as Array<String>?, null, null, sortOrder)
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdOpenHelper!!.writableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_CATEGORIAS -> TabelaCategorias(bd)
            URI_JOGOS -> TabelaJogos(bd)
            else -> return null
        }

        val id = tabela.insere(values!!)
        if (id == -1L) {
            return null
        }

        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }


    companion object {
        private const val AUTORIDADE = "pt.ipg.colecaojogos"
        private const val URI_CATEGORIAS = 100
        private const val URI_CATEGORIA_ID = 101
        private const val URI_JOGOS = 200
        private const val URI_JOGOS_ID = 201
        const val CATEGORIAS = "categorias"
        const val JOGOS = "jogos"


        fun uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, CATEGORIAS, URI_CATEGORIAS) // content://pt.ipg.colecaojogos/categorias -> 100
            addURI(AUTORIDADE, "$CATEGORIAS/#", URI_CATEGORIA_ID) // content://pt.ipg.colecaojogos/categorias/243 -> 101 , # = id de uma categoria em especifico
            addURI(AUTORIDADE, JOGOS, URI_JOGOS) //content://pt.ipg.colecaojogos/jogos -> 200
            addURI(AUTORIDADE, "$JOGOS/#", URI_JOGOS_ID) //content://pt.ipg.colecaojogos/jogos/ -> 201 , # = id de um jogo em especifico
        }
    }
}