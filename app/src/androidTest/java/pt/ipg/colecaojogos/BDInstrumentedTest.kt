package pt.ipg.colecaojogos

import android.content.Context
//import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BDInstrumentedTest {
    private fun getAppContext(): Context = InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDataBase(): SQLiteDatabase {
        val openHelper = BDJogosOpenHelper(getAppContext())
        return openHelper.writableDatabase
    }

    private fun insereCategoria(
        bd: SQLiteDatabase,
        categoria: Categoria
    ) {
        categoria.id = TabelaCategorias(bd).insere(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }

    private fun insereJogo(bd: SQLiteDatabase, jogo: Jogo) {
        jogo.id = TabelaJogos(bd).insere(jogo.toContentValues())
        assertNotEquals(-1, jogo.id)
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BDJogosOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDJogosOpenHelper(getAppContext()) //permite abrir a base de dados
        val bd = openHelper.readableDatabase //conseguir ler na base de dados
        assert(bd.isOpen)

        // Context of the app under test.
        val appContext = getAppContext()
        assertEquals("pt.ipg.colecaojogos", appContext.packageName)
    }

    @Test
    fun consegueInserirCategorias() {
        val bd = getWritableDataBase()

        val categoria = Categoria("Terror", 16, "Test")
        insereCategoria(bd, categoria)
    }

    @Test
    fun consegueInserirJogos() {
        val bd = getWritableDataBase()

        val categoria = Categoria("Terror", 16, "Test")
        insereCategoria(bd, categoria)

        val jogo1 = Jogo("Alien", "Desenvolvedor", "Janeiro 2022", 10.50, categoria.id)
        insereJogo(bd, jogo1)

        val jogo2 = Jogo("FNAT", "Desenvolvedor", "Fevereiro 2022", 19.99, categoria.id)
        insereJogo(bd, jogo2)
    }

    @Test
    fun consegueLerCategorias() {
        val bd = getWritableDataBase()

        val categCorrida = Categoria("Corrida", 8, "Test")
        insereCategoria(bd, categCorrida)

        val categFPS = Categoria("FPS", 16, "Test")
        insereCategoria(bd, categFPS)

        val tabelaCategorias = TabelaCategorias(bd)
        val cursor = tabelaCategorias.consulta(TabelaCategorias.CAMPOS, "${BaseColumns._ID}=?", arrayOf(categFPS.id.toString()), null, null, null)

        assert(cursor.moveToNext()) //move cursor para o primeiro registo

        val categBD = Categoria.fromCursor(cursor)

        assertEquals(categFPS, categBD)

        val cursorTodasCategorias = tabelaCategorias.consulta(TabelaCategorias.CAMPOS, null, null, null, null, TabelaCategorias.CAMPO_NOME)

        assert(cursorTodasCategorias.count > 1)
    }

    @Test
    fun consegueLerJogos() {
        val bd = getWritableDataBase()

        val categoria = Categoria("FPS", 16, "Test")
        insereCategoria(bd, categoria)

        val jogo1 = Jogo("Fortnite", "Desenvolvedor", "Marco 2022", 4.99, categoria.id)
        insereJogo(bd, jogo1)

        val jogo2 = Jogo("Valorant", "Desenvolvedor", "Abril 2022", 4.99, categoria.id)
        insereJogo(bd, jogo2)

        val tabelaJogos = TabelaJogos(bd)
        val cursor = tabelaJogos.consulta(TabelaCategorias.CAMPOS, "${BaseColumns._ID}=?", arrayOf(jogo1.id.toString()), null, null, null)

        assert(cursor.moveToNext()) //move cursor para o primeiro registo

        val jogoBD = Jogo.fromCursor(cursor)

        assertEquals(jogo1, jogoBD)

        val cursorTodosJogos = tabelaJogos.consulta(TabelaJogos.CAMPOS, null, null, null, null, TabelaJogos.CAMPO_NOME)

        assert(cursorTodosJogos.count > 1)
    }
}