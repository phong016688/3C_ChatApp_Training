package com.ccc.chatapp.screens.chat

import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider

class ChatPresenterIplm(
    private var view: ChatView?,
    private val schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : ChatPresenter {

    override fun onStart() {
        // No-op
    }

    override fun onStop() {
        // No-op
    }

    override fun onDestroy() {
        // No-op
    }

    override fun logout() {
        userRepository.logout()
        view?.logout()
    }
}
