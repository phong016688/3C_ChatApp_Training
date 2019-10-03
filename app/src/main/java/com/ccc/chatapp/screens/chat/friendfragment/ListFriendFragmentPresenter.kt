package com.ccc.chatapp.screens.chat.friendfragment

import android.util.Log
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ListFriendFragmentPresenterImpl(
    private var view: ListFriendFragmentView?,
    private var schedulerProvider: SchedulerProvider,
    private var userRepository: UserRepository
) : ListFriendFragmentPresenter {
    private var mCompositeDisposable = CompositeDisposable()

    override fun getListFriend() {
        val disposable = userRepository
            .getListFriend()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                view?.updateListFriend(it)
            }, {
                Log.d(this::class.java.simpleName, it.message)
            })
        mCompositeDisposable.add(disposable)
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

}
