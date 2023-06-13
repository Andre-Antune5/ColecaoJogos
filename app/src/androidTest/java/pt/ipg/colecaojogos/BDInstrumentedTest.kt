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
import java.util.*

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
        //getAppContext().deleteDatabase(BDJogosOpenHelper.NOME_BASE_DADOS)
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

        val data1 = Calendar.getInstance()
        data1.set(2022, 1, 1)
        val jogo1 = Jogo("Alien", categoria, "Desenvolvedor", data1, 10.50, categoria.id)
        insereJogo(bd, jogo1)

        val data2 = Calendar.getInstance()
        data2.set(2022, 2, 1)
        val jogo2 = Jogo("FNAT", categoria, "Desenvolvedor", data2, 19.99, categoria.id)
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

        val cursorTodasCategorias = tabelaCategorias.consulta(TabelaCategorias.CAMPOS, null, null, null, null, TabelaCategorias.CAMPO_DESCRICAO)

        assert(cursorTodasCategorias.count > 1)
    }

    @Test
    fun consegueLerJogos() {
        val bd = getWritableDataBase()

        val categoria = Categoria("FPS", 16, "Test")
        insereCategoria(bd, categoria)

        val data1 = Calendar.getInstance()
        data1.set(2022, 3, 1)
        val jogo1 = Jogo("Fortnite", categoria, "Desenvolvedor", data1, 4.99)
        insereJogo(bd, jogo1)

        val data2 = Calendar.getInstance()
        data2.set(2022, 4, 1)
        val jogo2 = Jogo("Valorant", categoria, "Desenvolvedor", data2, 4.99)
        insereJogo(bd, jogo2)

        val tabelaJogos = TabelaJogos(bd)
        val cursor = tabelaJogos.consulta(
            TabelaJogos.CAMPOS,
            "${TabelaJogos.CAMPO_ID}=?",
            arrayOf(jogo1.id.toString()),
            null, null, null)

        assert(cursor.moveToNext()) //move cursor para o primeiro registo

        val jogoBD = Jogo.fromCursor(cursor)

        assertEquals(jogo1, jogoBD)

        val cursorTodosJogos = tabelaJogos.consulta(
            TabelaJogos.CAMPOS,
            null, null, null, null,
            TabelaJogos.CAMPO_NOME)

        assert(cursorTodosJogos.count > 1)
    }

    @Test
    fun consegueAlterarCategorias() {
        val bd = getWritableDataBase()

        val categoria = Categoria("...", 20, "Test")
        insereCategoria(bd, categoria)

        categoria.descricao = "Parkour"
        val registosAlterados = TabelaCategorias(bd).altera(categoria.toContentValues(), "${BaseColumns._ID}=?", arrayOf(categoria.id.toString()))

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueAlterarJogos() {
        val bd = getWritableDataBase()

        val categoriaMiniJogos = Categoria("Mini-Jogos", 20, "Test")
        insereCategoria(bd, categoriaMiniJogos)

        val categoriaFactory = Categoria("Factory", 13, "Test")
        insereCategoria(bd, categoriaFactory)

        val data = Calendar.getInstance()
        data.set(2022, 3, 1)
        val jogo = Jogo("...", categoriaFactory, "Desenvolvedor", data, 1.99, categoriaFactory.id)
        insereJogo(bd, jogo)

        val novaData = Calendar.getInstance()
        novaData.set(2017, 1, 1)
        jogo.nome = "Epic-Mini-Games"
        jogo.desenvolvedor = "Dev"
        jogo.data = novaData
        jogo.preco = 0.99

        val registosAlterados = TabelaJogos(bd).altera(jogo.toContentValues(), "${BaseColumns._ID}=?", arrayOf(jogo.id.toString()))

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueApagarCategorias() {
        val bd = getWritableDataBase()

        val categoria = Categoria("...", 20, "Test")
        insereCategoria(bd, categoria)

        categoria.descricao = "Parkour"
        val registosEliminados = TabelaCategorias(bd).elimina("${BaseColumns._ID}=?", arrayOf(categoria.id.toString()))

        assertEquals(1, registosEliminados)
    }

    @Test
    fun consegueApagarJogos() {
        val bd = getWritableDataBase()

        val categoria = Categoria("Tycoon", 7, "Test")
        insereCategoria(bd, categoria)

        val data = Calendar.getInstance()
        data.set(2022, 3, 1)
        val jogo = Jogo("...", categoria, "Desenvolvedor", data, 1.99, categoria.id)
        insereJogo(bd, jogo)

        val registosEliminados = TabelaJogos(bd).elimina("${BaseColumns._ID}=?", arrayOf(jogo.id.toString()))

        assertEquals(1, registosEliminados)
    }
}