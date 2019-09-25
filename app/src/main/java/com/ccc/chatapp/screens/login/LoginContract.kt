package com.ccc.chatapp.screens.login

import com.ccc.chatapp.screens.BasePresenter

interface LoginView {
    fun onLoginSuccess()
}

interface LoginPresenter: BasePresenter {
    fun login(username: String, password: String)
}
