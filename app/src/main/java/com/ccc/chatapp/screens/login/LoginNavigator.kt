package com.ccc.chatapp.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.ccc.chatapp.screens.BaseNavigator
import com.ccc.chatapp.screens.chat.ChatActivity
import com.ccc.chatapp.screens.signin.SignInActivity

interface LoginNavigator {
    fun finishActivity()
    fun goToChatScreen()
    fun goToSignInScreen()
}

class LoginNavigatorImpl(activity: AppCompatActivity) : BaseNavigator(activity), LoginNavigator {
    override fun goToSignInScreen() {
        val intent: Intent = SignInActivity.getInstance(activity)
        activity.startActivity(intent)
    }

    override fun goToChatScreen() {
        val intent: Intent = ChatActivity.getInstance(activity)
        activity.startActivity(intent)
    }

    override fun finishActivity() {
        activity.finish()
    }
}
