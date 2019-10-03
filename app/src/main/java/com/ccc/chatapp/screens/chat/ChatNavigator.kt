package com.ccc.chatapp.screens.chat

import com.ccc.chatapp.screens.BaseNavigator
import com.ccc.chatapp.screens.login.LoginActivity

interface ChatNavigator {
    fun goToLoginScreen()
    fun finishActivity()
}

class ChatNavigatorImpl(activity: ChatActivity) : BaseNavigator(activity), ChatNavigator {
    override fun goToLoginScreen() {
        val intent = LoginActivity.getInstance(activity, true)
        activity.startActivity(intent)
    }

    override fun finishActivity() {
        activity.finish()
    }
}
