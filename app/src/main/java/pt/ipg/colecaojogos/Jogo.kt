package pt.ipg.colecaojogos

import android.content.ContentValues

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
}