package com.ccc.chatapp.screens.chat

import com.ccc.chatapp.screens.BaseNavigator

interface ChatNavigator {
    fun finishActivity()
}

class ChatNavigatorImpl(activity: ChatActivity) : BaseNavigator(activity), ChatNavigator {
    override fun finishActivity() {
        activity.finish()
    }
}
