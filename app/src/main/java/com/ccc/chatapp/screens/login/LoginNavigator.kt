package com.ccc.chatapp.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.ccc.chatapp.screens.BaseNavigator
import com.ccc.chatapp.screens.chat.ChatActivity

interface LoginNavigator {
    fun finishActivity()
    fun goToChatScreen()
}

class LoginNavigatorImpl(activity: AppCompatActivity) : BaseNavigator(activity), LoginNavigator {
    override fun goToChatScreen() {
        val intent: Intent = ChatActivity.getInstance(activity)
        activity.startActivity(intent)
    }

    override fun finishActivity() {
        activity.finish()
    }
}
