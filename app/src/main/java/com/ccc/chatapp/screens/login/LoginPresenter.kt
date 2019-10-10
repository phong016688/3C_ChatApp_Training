package com.ccc.chatapp.screens.login

import android.util.Log
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class LoginPresenterImpl(
    private var view: LoginView?,
    private val schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : LoginPresenter {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onStart() {
        if (userRepository.isUserLogged()) {
            val disposable = userRepository.setUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view?.onLoginSuccess()
                }, {
                    view?.onLoginFailed()
                })
            mCompositeDisposable.add(disposable)
        }
    }

    override fun onStop() {
        //no-op
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        view = null
    }

    override fun signIn() {
        view?.onSignIn()
    }

    override fun login(username: String, password: String) {
        val disposable = userRepository.login(username, password)
            .timeout(5000, TimeUnit.MILLISECONDS)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                view?.onLoginSuccess()
            }, {
                view?.onLoginFailed()
                Log.e(this::class.java.simpleName, it.message)
            })
        mCompositeDisposable.add(disposable)
    }
}
