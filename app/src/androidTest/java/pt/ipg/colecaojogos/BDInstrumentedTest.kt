package pt.ipg.colecaojogos

import android.content.Context
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
        val openHelper = BDJogosOpenHelper(getAppContext())
        val bd = openHelper.writableDatabase //conseguir escrever na base de dados

        //val categoria = Categoria("Terror")

        //TabelaCategorias(bd).insere()
    }
}