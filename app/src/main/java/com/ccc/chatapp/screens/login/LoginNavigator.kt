package com.ccc.chatapp.screens.login

import androidx.appcompat.app.AppCompatActivity
import com.ccc.chatapp.screens.BaseNavigator

interface LoginNavigator {
    fun finishActivity()
}

class LoginNavigatorImpl(activity: AppCompatActivity) : BaseNavigator(activity), LoginNavigator {
    override fun finishActivity() {
        activity.finish()
    }
}
