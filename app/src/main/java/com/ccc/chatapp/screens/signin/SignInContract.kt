package com.ccc.chatapp.screens.signin

import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.screens.BasePresenter

interface SigInView {
    fun onSignInSuccess()
    fun onSIgnInFailed()
}

interface SignInPresenter : BasePresenter {
    fun signIn(user: User, password: String)
}
