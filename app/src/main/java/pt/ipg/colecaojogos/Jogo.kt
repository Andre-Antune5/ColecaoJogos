package pt.ipg.colecaojogos

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class Jogo (var nome: String, var desenvolvedor: String, var data: String, var preco: Double, var id_categoria: Long, var id: Long = -1) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaJogos.CAMPO_NOME, nome)
        valores.put(TabelaJogos.CAMPO_DESENVOLVEDOR, desenvolvedor)
        valores.put(TabelaJogos.CAMPO_DATA, data)
        valores.put(TabelaJogos.CAMPO_PRECO, preco)
        valores.put(TabelaJogos.CAMPO_FK_CATEGORIA, id_categoria)

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

            val id = cursor.getLong(posID)
            val nome = cursor.getString(posNome)
            val desenvolvedor = cursor.getString(posDesenvolvedor)
            val data = cursor.getString(posData)
            val preco = cursor.getDouble(posPreco)
            val categoriaID = cursor.getLong(posCategoriaFK)

            return Jogo(nome, desenvolvedor, data, preco, categoriaID, id)
        }
    }
}