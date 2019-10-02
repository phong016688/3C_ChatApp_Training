package com.ccc.chatapp.screens.chat

import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider

class ChatPresenterIplm(
    private var view: ChatView?,
    private val schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : ChatPresenter {

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    override fun logout() {
        userRepository.logout()
        view?.logout()
    }
}
