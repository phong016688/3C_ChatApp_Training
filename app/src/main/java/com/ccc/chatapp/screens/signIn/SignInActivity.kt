package com.ccc.chatapp.screens.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ccc.chatapp.Application
import com.ccc.chatapp.R
import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.extension.gone
import com.ccc.chatapp.extension.show
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.Constant
import com.ccc.chatapp.utils.rx.SchedulerProvider
import kotlinx.android.synthetic.main.activity_signin.*
import javax.inject.Inject

class SignInActivity : AppCompatActivity(), SigInView {

    @Inject
    lateinit var mUserRepository: UserRepository
    @Inject
    lateinit var mSchedulerProvider: SchedulerProvider

    private lateinit var mPresenter: SignInPresenter
    private lateinit var mSignInNavigator: SignInNavigator
    private var mAvatar = Uri.parse(Constant.Path.BASE_PATH_IMAGE + R.drawable.default_avatar)
    private var mUser = User(
        "",
        "",
        "",
        ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        (application as Application).getAppComponent().inject(this)
        mPresenter = SignInPresenterImpl(this, mSchedulerProvider, mUserRepository)
        mSignInNavigator = SignInNavigatorImpl(this)

        handleEvent()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.RequestCode.PICK_PHOTO_FOR_AVATAR && intent != null) {
            mAvatar = intent.data
            avatarSignInImageView.setImageURI(mAvatar)
        }
    }

    override fun onSignInSuccess() {
        signInProgress.gone()
        mSignInNavigator.gotoChatActivity()
    }

    override fun updateAvatar(url : String) {
        mUser.avatar = url
    }

    override fun onSIgnInFailed() {
        signInProgress.gone()
        Toast.makeText(this, getString(R.string.text_signIn_failed), Toast.LENGTH_LONG).show()
    }


    private fun handleEvent() {
        signInAccountButton.setOnClickListener { onSignInClick() }
        avatarSignInImageView.setOnClickListener { pickImage() }
    }

    private fun onSignInClick() {
        when (null) {
            userSignInEditText.text -> {
                userSignInEditText.error = getString(R.string.text_warning_empty_email)
            }
            passwordSignInEditText.text -> {
                passwordSignInEditText.error = getString(R.string.text_warning_empty_password)
            }
            phoneSignInEditText.text -> {
                phoneSignInEditText.error = getString(R.string.text_warning_empty_phone)
            }
            fullNameSignInEditText.text -> {
                fullNameSignInEditText.error = getString(R.string.text_warning_empty_fullName)
            }
            else -> {
                mUser.username = userSignInEditText.text.toString()
                mUser.fullName = fullNameSignInEditText.text.toString()
                val password = passwordSignInEditText.text.toString()
                mUser.phone = phoneSignInEditText.text.toString()
                mPresenter.getUrlAvatar(mAvatar)
                mPresenter.signIn(mUser, password)
                signInProgress.show()
            }
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = Constant.IntentType.IMAGE
        startActivityForResult(intent, Constant.RequestCode.PICK_PHOTO_FOR_AVATAR)
    }

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }
}
