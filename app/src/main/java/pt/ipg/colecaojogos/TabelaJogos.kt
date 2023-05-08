package pt.ipg.colecaojogos

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaJogos(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {

    }

    companion object {
        const val NOME_TABELA = "jogos"
    }
}