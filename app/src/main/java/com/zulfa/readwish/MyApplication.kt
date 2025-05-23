package com.zulfa.readwish

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.zulfa.readwish.core.di.databaseModule
import com.zulfa.readwish.core.di.networkModule
import com.zulfa.readwish.core.di.repositoryModule
import com.zulfa.readwish.presentation.di.useCaseModule
import com.zulfa.readwish.presentation.di.viewModelModule
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
                    useCaseModule,
                    viewModelModule
                )
            )
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }
}