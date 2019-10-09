package com.ccc.chatapp.screens.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ccc.chatapp.Application
import com.ccc.chatapp.R
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.screens.chat.chatfragment.ChatFragment
import com.ccc.chatapp.screens.chat.friendfragment.ListFriendFragment
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

        mChatPresenter = ChatPresenterImpl(this, mSchedulerProvider, mUserRepository)
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
        if (item?.itemId == R.id.item_logout) {
            onLogoutClick()
        }
        return true
    }

    override fun logout() {
        mChatNavigator.goToLoginScreen()
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

    override fun moveFragment(position: Int) {
        fragment_ViewPager.currentItem = position
    }

    private fun setSupportActionBar() {
        setSupportActionBar(toolBar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun handleEvent() {
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_friend -> {
                    toolBar.title = getString(R.string.title_friend)
                    mChatPresenter.toFragmentFriend()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_messenger -> {
                    toolBar.title = getString(R.string.title_mess)
                    mChatPresenter.toFragmentMessage()
                    return@setOnNavigationItemSelectedListener  true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun onLogoutClick() {
        mChatPresenter.logout()
    }

    private fun setViewPagerAdapter() {
        mFragmentList = ArrayList()
        mFragmentList.add(ListFriendFragment.getInstance())
        mFragmentList.add(ChatFragment.getInstance())
        fragment_ViewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager, 2) {
            override fun getItem(position: Int): Fragment {
                return mFragmentList[position]
            }

            override fun getCount(): Int {
                return mFragmentList.size
            }
        }
        fragment_ViewPager.currentItem = Fragment_Friend
    }

    companion object {
        const val Fragment_Friend = 0
        const val Fragment_Message = 1
        fun getInstance(context: Context): Intent {
            return Intent(context, ChatActivity::class.java)
        }
    }
}
