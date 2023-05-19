package pt.ipg.colecaojogos

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class JogosContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun query(
        p0: Uri, //URI = universal Resource Identifier
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
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
            addURI(AUTORIDADE, JOGOS, URI_JOGOS) //content://pt.ipg.colecaojogos/jogo
        }
    }
}