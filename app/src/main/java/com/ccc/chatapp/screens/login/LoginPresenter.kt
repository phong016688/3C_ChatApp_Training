package com.ccc.chatapp.screens.login

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
            view?.onLoginSuccess()
        }
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        view = null
    }

    override fun signIn() {
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
            })
        mCompositeDisposable.add(disposable)
    }
}
