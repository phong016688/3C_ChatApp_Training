package com.ccc.chatapp

import com.ccc.chatapp.data.source.RepositoryModule
import com.ccc.chatapp.screens.chat.ChatActivity
import com.ccc.chatapp.screens.login.LoginActivity
import com.ccc.chatapp.screens.signin.SignInActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(chatActivity: ChatActivity)

    fun inject(signInActivity: SignInActivity)
}
