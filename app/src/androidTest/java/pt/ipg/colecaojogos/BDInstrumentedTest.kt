package pt.ipg.colecaojogos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
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
        val bd = openHelper.writableDatabase //conseguir escrever na base de dados
        return bd
    }

    private fun insereCategoria(
        bd: SQLiteDatabase,
        categoria: Categoria
    ) {
        val id = TabelaCategorias(bd).insere(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }

    private fun insereJogo(bd: SQLiteDatabase, jogo: Jogo) {
        jogo.id = TabelaJogos(bd).insere(jogo.toContentValues())
        assertNotEquals(-1, jogo.id)
    }

    @Before
    fun apagaNaseDados() {
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
    fun consegueInserirJogo() {
        val bd = getWritableDataBase()

        val categoria = Categoria("Terror", 16, "Test")
        insereCategoria(bd, categoria)

        val jogo1 = Jogo("Alien", "Desenvolvedor", "Janeiro 2022", 10.50, categoria.id)
        insereJogo(bd, jogo1)

        val jogo2 = Jogo("FNAT", "Desenvolvedor", "Fevereiro 2022", 19.99, categoria.id)
        insereJogo(bd, jogo2)
    }
}