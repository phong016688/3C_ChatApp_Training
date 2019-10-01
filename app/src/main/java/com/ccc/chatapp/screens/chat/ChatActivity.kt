package com.ccc.chatapp.screens.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ccc.chatapp.Application
import com.ccc.chatapp.R
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider
import kotlinx.android.synthetic.main.activity_chat.*
import javax.inject.Inject

class ChatActivity : AppCompatActivity(), ChatView {

    @Inject
    internal lateinit var mSchedulerProvider: SchedulerProvider
    @Inject
    internal lateinit var mUserRepository: UserRepository

    private lateinit var mChatPresenter: ChatPresenter

    private lateinit var mChatNavigator: ChatNavigator

    private lateinit var mFragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        (applicationContext as Application).getAppComponent().inject(this)

        mChatPresenter = ChatPresenterIplm(this, mSchedulerProvider, mUserRepository)
        mChatNavigator = ChatNavigatorImpl(this)

        setSupportActionBar()
        setViewPagerAdapter()
        handleEvent()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_logout -> onLogoutClick()
        }
        return true
    }

    override fun logout() {
        mChatNavigator.finishActivity()
    }

    override fun onStart() {
        super.onStart()
        mChatPresenter.onStart()
    }

    override fun onStop() {
        mChatPresenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mChatPresenter.onDestroy()
        super.onDestroy()
    }

    override fun logoutSuccess() {
        mChatNavigator.finishActivity()
    }

    private fun setSupportActionBar() {
        setSupportActionBar(toolBar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun handleEvent() {
    }

    private fun onLogoutClick() {
        mChatPresenter.logout()
    }

    private fun setViewPagerAdapter() {
        mFragmentList = ArrayList()
        mFragmentList.add(Fragment())
        fragment_ViewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager, 2) {
            override fun getItem(position: Int): Fragment {
                return mFragmentList[position]
            }

            override fun getCount(): Int {
                return mFragmentList.size
            }
        }
        fragment_ViewPager.currentItem = 0

    }

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, ChatActivity::class.java)
        }
    }
}
