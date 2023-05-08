package pt.ipg.colecaojogos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val NOME_BASE_DADOS = "jogos.db"

private const val VERSAO_BASE_DADOS = 1

class BDcolecaoJogosOpenHelper(
    context: Context?
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS) {
    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, p2: Int) {
    }
}