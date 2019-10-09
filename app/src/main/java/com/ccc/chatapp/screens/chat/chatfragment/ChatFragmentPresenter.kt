package com.ccc.chatapp.screens.chat.chatfragment

import android.view.View
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider

class ChatFragmentPresenterImpl(
    view: View?,
    schedulerProvider: SchedulerProvider,
    userRepository: UserRepository
) : ChatFragmentPresenter {

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {

    }

}
