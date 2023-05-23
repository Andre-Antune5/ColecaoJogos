package pt.ipg.colecaojogos

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

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

        val tabela = when (uriMatcher().match(uri)) {
            URI_CATEGORIAS -> TabelaCategorias(bd)
            URI_JOGOS -> TabelaJogos(bd)
            else -> null
        }
        return tabela?.consulta(projection as Array<String>, selection, selectionArgs as Array<String>?, null, null, sortOrder)
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
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
        private const val URI_JOGOS = 200
        const val CATEGORIAS = "categorias"
        const val JOGOS = "jogos"


        fun uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, CATEGORIAS, URI_CATEGORIAS) // content://pt.ipg.colecaojogos/categorias
            addURI(AUTORIDADE, JOGOS, URI_JOGOS) //content://pt.ipg.colecaojogos/jogos
        }
    }
}