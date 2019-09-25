package com.ccc.chatapp.screens.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccc.chatapp.Application
import com.ccc.chatapp.R
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginView {

    @Inject
    internal lateinit var mSchedulerProvider: SchedulerProvider
    @Inject
    internal lateinit var mUserRepository: UserRepository

    private lateinit var mPresenter: LoginPresenter
    private lateinit var mNavigator: LoginNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (application as Application).getAppComponent().inject(this)

        mPresenter = LoginPresenterImpl(this, mSchedulerProvider, mUserRepository)
        mNavigator = LoginNavigatorImpl(this)

        handleEvents()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun onStop() {
        mPresenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onLoginSuccess() {
        //TODO Handle when user login success
    }

    private fun handleEvents() {
        loginButton.setOnClickListener { onLoginClick() }
    }

    //TODO get username and password from user input
    private fun onLoginClick() {
        mPresenter.login("userA", "12345")
    }

    fun getInstance(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }
}
