package com.ccc.chatapp.screens.login

import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class LoginPresenterImpl(private var view: LoginView?,
                         private val schedulerProvider: SchedulerProvider,
                         private val userRepository: UserRepository) : LoginPresenter {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onStart() {
        //No-op
    }

    override fun onStop() {
        //No-op
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        view = null
    }

    override fun login(username: String, password: String) {
        val disposable = userRepository.login(username, password)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                view?.onLoginSuccess()
            }, {
                //TODO handle error
            })
        mCompositeDisposable.add(disposable)
    }
}
