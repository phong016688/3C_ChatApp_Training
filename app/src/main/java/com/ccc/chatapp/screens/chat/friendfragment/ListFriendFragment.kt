package com.ccc.chatapp.screens.chat.friendfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccc.chatapp.Application
import com.ccc.chatapp.R
import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.screens.chat.ChatActivity
import com.ccc.chatapp.screens.chat.ItemClickListener
import com.ccc.chatapp.utils.rx.SchedulerProvider
import kotlinx.android.synthetic.main.fragment_friend.view.*
import javax.inject.Inject

class ListFriendFragment : Fragment(), ListFriendFragmentView,
    ItemClickListener {

    @Inject
    lateinit var mSchedulerProvider: SchedulerProvider
    @Inject
    lateinit var mUserRepository: UserRepository

    private lateinit var mPresenter: ListFriendFragmentPresenter
    private var mAdapter: AdapterFriendList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as ChatActivity).application as Application).getAppComponent().inject(this)
        mPresenter = ListFriendFragmentPresenterImpl(
            this,
            mSchedulerProvider,
            mUserRepository
        )
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun getListFriend() {
        mPresenter.getListFriend()
    }

    override fun updateListFriend(user: User) {
        mAdapter?.addUser(user)
    }

    override fun onClick(view: View?, user: User) {
        mPresenter.goToChatFragment(user)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_friend, container, false)
        setUpRecyclerView(view)
        return view
    }

    override fun goToChatFragment() {
        (activity as ChatActivity).moveFragment(ChatActivity.FRAGMENT_MESSAGE)
    }

    private fun setUpRecyclerView(view: View) {
        mAdapter = AdapterFriendList(context, ArrayList())
        view.friendRecyclerView.adapter = mAdapter
        view.friendRecyclerView.layoutManager = LinearLayoutManager(context)
        view.friendRecyclerView.setHasFixedSize(true)
        view.friendRecyclerView.itemAnimator = DefaultItemAnimator()
        mAdapter?.setItemClickListener(this)
    }

    companion object {
        fun getInstance(): ListFriendFragment {
            return ListFriendFragment()
        }
    }
}
