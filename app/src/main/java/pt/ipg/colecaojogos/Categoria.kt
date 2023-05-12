package pt.ipg.colecaojogos

import android.content.ContentValues
import androidx.core.content.contentValuesOf

data class Categoria(var nome: String, var idadeMin: Int, var maisVendido: String, var id: Long = -1) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCategorias.CAMPO_NOME, nome)
        valores.put(TabelaCategorias.CAMPO_IDADE_MIN, idadeMin)
        valores.put(TabelaCategorias.CAMPO_MAIS_VENDIDO, maisVendido)

        return valores
    }
}