package lavsam.gb.profiaslesson5.application

import android.app.Application
import lavsam.gb.profiaslesson5.di.application
import lavsam.gb.profiaslesson5.di.historyScreen
import lavsam.gb.profiaslesson5.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    application, mainScreen, historyScreen
                )
            )
        }
    }
}