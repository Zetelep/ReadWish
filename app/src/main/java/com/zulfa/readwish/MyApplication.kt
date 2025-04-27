package com.zulfa.readwish

import android.app.Application
import com.zulfa.readwish.core.di.databaseModule
import com.zulfa.readwish.core.di.networkModule
import com.zulfa.readwish.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
//                    useCaseModule, not on Di core
//                    viewModelModule
                )
            )
        }
    }
}