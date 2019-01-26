package com.example.igonzalez.airlinescheduleapp.dagger.module

import android.app.Application
import android.content.Context
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseView
import dagger.Module
import dagger.Provides

@Module
class ContextModule {

    @Provides
    internal fun provideContext(baseView: BaseView): Context {
        return baseView.getContext()
    }

    @Provides
    internal fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }
}