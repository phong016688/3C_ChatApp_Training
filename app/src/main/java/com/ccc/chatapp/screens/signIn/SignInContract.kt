package com.ccc.chatapp.screens.signin

import android.net.Uri
import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.screens.BasePresenter

interface SigInView {
    fun onSignInSuccess()
    fun onSIgnInFailed()
    fun updateAvatar(url : String)
}

interface SignInPresenter : BasePresenter {
    fun signIn(user: User, password: String)
    fun getUrlAvatar(uri: Uri)
}
