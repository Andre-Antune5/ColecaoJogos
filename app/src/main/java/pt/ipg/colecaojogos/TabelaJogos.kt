package pt.ipg.colecaojogos

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaJogos(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DESENVOLVEDOR TEXT, $CAMPO_DATA TEXT, $CAMPO_PRECO REAL, $CAMPO_FK_CATEGORIA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_CATEGORIA) REFERENCES ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME_TABELA = "jogos"
        const val CAMPO_NOME = "nome"
        const val CAMPO_DESENVOLVEDOR = "desenvolvedor"
        const val CAMPO_DATA = "data"
        const val CAMPO_PRECO = "preco"
        const val CAMPO_FK_CATEGORIA = "id_categoria"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_DESENVOLVEDOR,CAMPO_DATA, CAMPO_PRECO, CAMPO_FK_CATEGORIA)
    }
}