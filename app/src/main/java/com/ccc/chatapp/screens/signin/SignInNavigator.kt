package com.ccc.chatapp.screens.signin

import androidx.appcompat.app.AppCompatActivity
import com.ccc.chatapp.screens.BaseNavigator
import com.ccc.chatapp.screens.chat.ChatActivity

interface SignInNavigator {
    fun finishActivity()
    fun gotoChatActivity()
}

class SignInNavigatorImpl(activity: AppCompatActivity) : SignInNavigator, BaseNavigator(activity) {
    override fun finishActivity() {
        activity.finish()
    }

    override fun gotoChatActivity() {
        activity.startActivity(ChatActivity.getInstance(activity))
    }
}
