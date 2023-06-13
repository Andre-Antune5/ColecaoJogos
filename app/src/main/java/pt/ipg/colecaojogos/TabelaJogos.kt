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

        val tabelaJogosAlias = "j"
        val tabelaCategoriasAlias = "c"
        val sql = SQLiteQueryBuilder()

        sql.tables = "$NOME_TABELA $tabelaJogosAlias INNER JOIN ${TabelaCategorias.NOME_TABELA} $tabelaCategoriasAlias ON $tabelaCategoriasAlias.${TabelaCategorias.CAMPO_ID}=$tabelaJogosAlias.$CAMPO_FK_CATEGORIA"
        //sql.tables = "$NOME_TABELA INNER JOIN ${TabelaCategorias.NOME_TABELA} ON ${TabelaCategorias.CAMPO_ID}=$CAMPO_FK_CATEGORIA"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)

        /*
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA AS j INNER JOIN ${TabelaCategorias.NOME_TABELA} AS c ON c.${TabelaCategorias.CAMPO_ID}=j.$CAMPO_FK_CATEGORIA"

        val projectionMap = HashMap<String, String>()
        for (coluna in colunas) {
            val colunaAlias = when {
                coluna == CAMPO_ID -> "j._id"
                coluna.startsWith(CAMPO_NOME) -> coluna.replace(CAMPO_NOME, "j.$CAMPO_NOME")
                coluna.startsWith(TabelaCategorias.CAMPO_NOME) -> coluna.replace(TabelaCategorias.CAMPO_NOME, "c.${TabelaCategorias.CAMPO_NOME}")
                else -> coluna
            }
            projectionMap[coluna] = colunaAlias
        }
        sql.setProjectionMap(projectionMap)
        */
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
        const val CAMPO_NOME_CATEGORIA = TabelaCategorias.CAMPO_NOME
        const val CAMPO_IDADE_MIN_CATEGORIA = TabelaCategorias.CAMPO_IDADE_MIN
        const val CAMPO_MAIS_VENDIDO_CATEGORIA = TabelaCategorias.CAMPO_MAIS_VENDIDO


        /*
        const val NOME_TABELA = "jogos"
        const val CAMPO_ID = "j.${BaseColumns._ID}"
        const val CAMPO_NOME = "j.nome"
        const val CAMPO_DESENVOLVEDOR = "j.desenvolvedor"
        const val CAMPO_DATA = "j.data"
        const val CAMPO_PRECO = "j.preco"
        const val CAMPO_FK_CATEGORIA = "j.id_categoria"
        const val CAMPO_NOME_CATEGORIA = "c.${TabelaCategorias.CAMPO_NOME}"
        const val CAMPO_IDADE_MIN_CATEGORIA = "c.${TabelaCategorias.CAMPO_IDADE_MIN}"
        const val CAMPO_MAIS_VENDIDO_CATEGORIA = "c.${TabelaCategorias.CAMPO_MAIS_VENDIDO}"
        */
        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_DESENVOLVEDOR, CAMPO_DATA, CAMPO_PRECO, CAMPO_FK_CATEGORIA, CAMPO_NOME_CATEGORIA, CAMPO_IDADE_MIN_CATEGORIA, CAMPO_MAIS_VENDIDO_CATEGORIA)
    }
}