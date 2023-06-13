package pt.ipg.colecaojogos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Jogo (
    var nome: String,
    var categoria: Categoria,
    var desenvolvedor: String,
    var data: Calendar? = null,
    var preco: Double,
    var id: Long = -1) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaJogos.CAMPO_NOME, nome)
        valores.put(TabelaJogos.CAMPO_DESENVOLVEDOR, desenvolvedor)
        valores.put(TabelaJogos.CAMPO_DATA, data?.timeInMillis)
        valores.put(TabelaJogos.CAMPO_PRECO, preco)
        valores.put(TabelaJogos.CAMPO_FK_CATEGORIA, categoria.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Jogo {
            val posID = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaJogos.CAMPO_NOME)
            val posDesenvolvedor = cursor.getColumnIndex(TabelaJogos.CAMPO_DESENVOLVEDOR)
            val posData = cursor.getColumnIndex(TabelaJogos.CAMPO_DATA)
            val posPreco = cursor.getColumnIndex(TabelaJogos.CAMPO_PRECO)
            val posCategoriaFK = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_CATEGORIA)
            val posNomeCateg = cursor.getColumnIndex(TabelaJogos.CAMPO_NOME_CATEGORIA)
            val posIdadeCateg = cursor.getColumnIndex(TabelaJogos.CAMPO_IDADE_MIN_CATEGORIA)
            val posVendidoCateg = cursor.getColumnIndex(TabelaJogos.CAMPO_MAIS_VENDIDO_CATEGORIA)

            val id = cursor.getLong(posID)
            val nome = cursor.getString(posNome)
            val desenvolvedor = cursor.getString(posDesenvolvedor)
            var data: Calendar?
            if (cursor.isNull(posData)) {
                data = null
            } else {
                data = Calendar.getInstance()
                data.timeInMillis = cursor.getLong(posData)
            }
            val preco = cursor.getDouble(posPreco)
            val categoriaID = cursor.getLong(posCategoriaFK)
            val nomeCategoria = cursor.getString(posNomeCateg)
            val idadeCateg = cursor.getInt(posIdadeCateg)
            val vendidoCateg = cursor.getString(posVendidoCateg)

            return Jogo(nome, Categoria(nomeCategoria, idadeCateg, vendidoCateg, categoriaID), desenvolvedor, data, preco, id)
        }
    }
}