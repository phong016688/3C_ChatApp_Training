package com.ccc.chatapp

import android.app.Application
import com.ccc.chatapp.data.source.RepositoryModule

class Application : Application() {

    private lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun getAppComponent(): AppComponent = mAppComponent

    private fun initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .repositoryModule(RepositoryModule())
            .build()
    }
}
