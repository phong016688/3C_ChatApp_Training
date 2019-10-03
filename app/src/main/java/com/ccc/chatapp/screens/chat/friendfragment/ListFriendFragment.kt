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
import kotlinx.android.synthetic.main.fragment_account.view.*
import javax.inject.Inject

class ListFriendFragment : Fragment(), ListFriendFragmentView,
    ItemClickListener {

    @Inject
    lateinit var mSchedulerProvider: SchedulerProvider
    @Inject
    lateinit var mUserRepository: UserRepository

    private lateinit var mPresenter: ListFriendFragmentPresenterImpl
    private var mListFriend = ArrayList<User>()
    private var mAdapter: AdapterFriendList? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        ((activity as ChatActivity).application as Application).getAppComponent().inject(this)
        mPresenter = ListFriendFragmentPresenterImpl(this, mSchedulerProvider, mUserRepository)
        mPresenter.getListFriend()
        super.onCreate(savedInstanceState)
    }

    override fun updateListFriend(user: User) {
        mAdapter?.addUser(user)
    }

    override fun onClick(view: View?, position: Int) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_account, container, false)
        setUpRecyclerView(view)
        mAdapter?.setItemClickListener(this)
        return view
    }

    private fun setUpRecyclerView(view: View) {
        mAdapter = AdapterFriendList(context, mListFriend)
        view.friendRecyclerView.adapter = mAdapter
        view.friendRecyclerView.layoutManager = LinearLayoutManager(context)
        view.friendRecyclerView.setHasFixedSize(true)
        view.friendRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    companion object {
        const val key_args = "KEY ARGS"
        fun getInstance(): ListFriendFragment {
            return ListFriendFragment()
        }
    }
}
