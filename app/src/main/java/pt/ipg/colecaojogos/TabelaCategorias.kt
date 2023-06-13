package pt.ipg.colecaojogos

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaCategorias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_Nome TEXT NOT NULL, $CAMPO_IDADE_MIN INTEGER NOT NULL, $CAMPO_MAIS_VENDIDO TEXT)")
    }

    companion object {
        const val NOME_TABELA = "categorias"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_Nome = "nome"
        const val CAMPO_IDADE_MIN = "idadeMin"
        const val CAMPO_MAIS_VENDIDO = "maisVendido"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_Nome, CAMPO_IDADE_MIN, CAMPO_MAIS_VENDIDO)
    }
}