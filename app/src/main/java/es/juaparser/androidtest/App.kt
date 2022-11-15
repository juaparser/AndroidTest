package es.juaparser.androidtest

import android.app.Application
import es.juaparser.androidtest.api.PlayerService
import es.juaparser.androidtest.database.AppDatabase
import es.juaparser.androidtest.database.PlayerRepository
import es.juaparser.androidtest.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


/**
 * Application class
 *
 * Declared to start Koin injection
 */
class App: Application() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule))
        }

    }

    private val appModule = module{

        single { PlayerService() }

        single { database.playerDao() }

        single { database.teamDao() }

        single { PlayerRepository(get(), get()) }

        single { MainViewModel(get()) }

    }
}