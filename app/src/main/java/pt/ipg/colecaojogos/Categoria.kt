package pt.ipg.colecaojogos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Categoria(
    var nome: String,
    var idadeMin: Int,
    var maisVendido: String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCategorias.CAMPO_NOME, nome)
        valores.put(TabelaCategorias.CAMPO_IDADE_MIN, idadeMin)
        valores.put(TabelaCategorias.CAMPO_MAIS_VENDIDO, maisVendido)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Categoria {
            val posID = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaCategorias.CAMPO_NOME)
            val posIdade = cursor.getColumnIndex(TabelaCategorias.CAMPO_IDADE_MIN)
            val posVendido = cursor.getColumnIndex(TabelaCategorias.CAMPO_MAIS_VENDIDO)

            val id = cursor.getLong(posID)
            val nome = cursor.getString(posNome)
            val idadeMin = cursor.getInt(posIdade)
            val maisVendido = cursor.getString(posVendido)

            return Categoria(nome, idadeMin, maisVendido, id)
        }
    }
}