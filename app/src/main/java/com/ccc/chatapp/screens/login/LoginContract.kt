package com.ccc.chatapp.screens.login

import com.ccc.chatapp.screens.BasePresenter

interface LoginView {
    fun onLoginSuccess()
    fun onLoginFailed()
    fun onSignIn()
}

interface LoginPresenter : BasePresenter {
    fun login(username: String, password: String)
    fun signIn()
}
