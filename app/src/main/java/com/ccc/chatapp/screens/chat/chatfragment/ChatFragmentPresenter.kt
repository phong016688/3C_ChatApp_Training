package com.ccc.chatapp.screens.chat.chatfragment

import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider

class ChatFragmentPresenterImpl(
    var view: ChatFragment?,
    var schedulerProvider: SchedulerProvider,
    var userRepository: UserRepository
) : ChatFragmentPresenter {
    override fun getListMessage() {
        userRepository.getListMessage()
    }

    override fun onStart() {
        view?.getListMessage()
    }

    override fun onStop() {
        TODO("onStop Fragment")
    }

    override fun onDestroy() {
        TODO("onDestroy Fragment")
    }
}
