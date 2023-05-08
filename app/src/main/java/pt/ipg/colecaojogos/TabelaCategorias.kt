package pt.ipg.colecaojogos

import android.database.sqlite.SQLiteDatabase

class TabelaCategorias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        
    }

    companion object {
        const val NOME_TABELA = "categorias"
    }
}