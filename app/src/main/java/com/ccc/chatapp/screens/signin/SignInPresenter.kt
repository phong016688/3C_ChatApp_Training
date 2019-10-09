package com.ccc.chatapp.screens.signin

import android.net.Uri
import android.util.Log
import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.UploadUtils
import com.ccc.chatapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SignInPresenterImpl(
    private var view: SigInView?,
    private var schedulerProvider: SchedulerProvider,
    private var userRepository: UserRepository
) : SignInPresenter {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        view = null
    }

    override fun signIn(user: User, password: String) {
        val disposable = UploadUtils
            .getUrlAvatar(Uri.parse(user.avatar))
            .flatMap {
                user.avatar = it
                userRepository.signIn(user, password)
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                view?.onSignInSuccess()
            }, {
                view?.onSIgnInFailed()
                Log.e(this::class.java.simpleName, it.message)
            })
        mCompositeDisposable.add(disposable)
    }
}
