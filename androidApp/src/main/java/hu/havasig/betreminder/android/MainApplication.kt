package hu.havasig.betreminder.android

import android.app.Application
import hu.havasig.betreminder.data.DefaultData
import hu.havasig.betreminder.data.UserRepository
import hu.havasig.betreminder.di.appModule
import hu.havasig.betreminder.android.di.androidModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    private val userRepository : UserRepository by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule() + androidModule)
        }

        userRepository.addUsers(DefaultData.DEFAULT_USERS)
    }
}