package pt.ipg.colecaojogos

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaJogos(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DESENVOLVEDOR TEXT, $CAMPO_DATA INTEGER, $CAMPO_PRECO REAL, $CAMPO_FK_CATEGORIA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_CATEGORIA) REFERENCES ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaCategorias.NOME_TABELA} ON ${TabelaCategorias.CAMPO_ID}=$CAMPO_FK_CATEGORIA"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }

    companion object {
        const val NOME_TABELA = "jogos"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_DESENVOLVEDOR = "desenvolvedor"
        const val CAMPO_DATA = "data"
        const val CAMPO_PRECO = "preco"
        const val CAMPO_FK_CATEGORIA = "id_categoria"
        const val CAMPO_NOME_CATEGORIA = TabelaCategorias.CAMPO_DESCRICAO
        const val CAMPO_IDADE_MIN_CATEGORIA = TabelaCategorias.CAMPO_IDADE_MIN
        const val CAMPO_MAIS_VENDIDO_CATEGORIA = TabelaCategorias.CAMPO_MAIS_VENDIDO

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_DESENVOLVEDOR, CAMPO_DATA, CAMPO_PRECO, CAMPO_FK_CATEGORIA, CAMPO_NOME_CATEGORIA, CAMPO_IDADE_MIN_CATEGORIA, CAMPO_MAIS_VENDIDO_CATEGORIA)
    }
}