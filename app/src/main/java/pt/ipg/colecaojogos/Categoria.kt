package pt.ipg.colecaojogos

import android.content.ContentValues
import androidx.core.content.contentValuesOf

data class Categoria(var nome: String, var id: Long = -1, var idadeMin: Int, var maisVendido: String) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCategorias.CAMPO_NOME, nome)
        
        return valores
    }
}