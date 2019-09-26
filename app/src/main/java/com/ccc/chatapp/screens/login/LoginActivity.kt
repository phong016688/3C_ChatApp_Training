package com.ccc.chatapp.screens.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ccc.chatapp.Application
import com.ccc.chatapp.R
import com.ccc.chatapp.extension.gone
import com.ccc.chatapp.extension.show
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.Constant
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constant.KeySaveInstance.USERNAME, emailEditText.text.toString())
        outState.putString(Constant.KeySaveInstance.PASSWORD, passwordEditText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        emailEditText.setText(savedInstanceState?.getString(Constant.KeySaveInstance.USERNAME))
        passwordEditText.setText(savedInstanceState?.getString(Constant.KeySaveInstance.PASSWORD))
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
        loginProgress.gone()
        mNavigator.goToChatScreen()
    }

    override fun onLoginFailed() {
        loginProgress.gone()
        Toast.makeText(this, getString(R.string.text_login_failed), Toast.LENGTH_LONG).show()
    }

    private fun handleEvents() {
        loginButton.setOnClickListener { onLoginClick() }
        signInButton.setOnClickListener { onSingInClick() }
    }

    private fun onSingInClick() {
        mPresenter.signIn()
    }

    private fun onLoginClick() {
        val username = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty()) {
            mPresenter.login(username, password)
            loginProgress.show()
        } else {
            if (username.isEmpty()) {
                emailEditText.error = getString(R.string.text_warning_empty_email)
            }
            if (password.isEmpty()) {
                passwordEditText.error = getString(R.string.text_warning_empty_password)
            }
        }
    }

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
