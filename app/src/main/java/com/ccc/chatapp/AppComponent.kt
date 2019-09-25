package com.ccc.chatapp

import android.content.Context
import com.ccc.chatapp.data.source.RepositoryModule
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.screens.login.LoginActivity
import com.ccc.chatapp.utils.rx.SchedulerProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class])
interface AppComponent {

    //============== Region for Repository ================//

    fun userRepository(): UserRepository

    //=============== Region for common ===============//

    fun applicationContext(): Context

    fun schedulerProvider(): SchedulerProvider


    fun inject(loginActivity: LoginActivity)
}
