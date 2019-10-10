package com.ccc.chatapp.screens.chat.chatfragment

import android.util.Log
import com.ccc.chatapp.data.model.Message
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ChatFragmentPresenterImpl(
    var view: ChatFragment?,
    var schedulerProvider: SchedulerProvider,
    var userRepository: UserRepository
) : ChatFragmentPresenter {
    var mCompositeDisposable = CompositeDisposable()

    override fun onStart() {
        // No-op
    }

    override fun onResume() {
        view?.clearListMessage()
        view?.listenerListMessage()
    }

    override fun onPause() {
        view?.clearListMessage()
        userRepository.removeListener()
    }

    override fun onStop() {
        // No-op
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
    }

    override fun listenerListMessage() {
        val disposable = userRepository.listenerListMessage()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ message ->
                view?.updateListMessage(message)
            }, {
                Log.e(this::class.java.simpleName, it.message)
            })
        mCompositeDisposable.add(disposable)
    }

    override fun sendMessage(message: Message) {
        val disposable = userRepository.sendMessage(message)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
            }, {
                Log.e(this::class.java.simpleName, it.message)
            })
        mCompositeDisposable.add(disposable)
    }
}
