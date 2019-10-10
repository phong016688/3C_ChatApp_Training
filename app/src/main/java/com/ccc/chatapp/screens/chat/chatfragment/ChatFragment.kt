package com.ccc.chatapp.screens.chat.chatfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccc.chatapp.Application
import com.ccc.chatapp.R
import com.ccc.chatapp.data.model.Message
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.utils.rx.SchedulerProvider
import kotlinx.android.synthetic.main.fragment_messenger.view.*
import javax.inject.Inject

class ChatFragment : Fragment(), ChatFragmentView {

    @Inject
    lateinit var mSchedulerProvider: SchedulerProvider
    @Inject
    lateinit var mUserRepository: UserRepository
    private lateinit var mAdapter: AdapterMessage
    private lateinit var mPresenter: ChatFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as Application).getAppComponent().inject(this)
        mPresenter = ChatFragmentPresenterImpl(this, mSchedulerProvider, mUserRepository)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_messenger, container, false)
        setUpRecyclerView(view)
        return view
    }

    override fun updateListMessage(message: Message) {
        mAdapter.addMessage(message)
    }

    private fun setUpRecyclerView(view: View) {
        mAdapter = AdapterMessage(context, ArrayList())
        view.messengerRecyclerView.layoutManager = LinearLayoutManager(context).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        view.messengerRecyclerView.setHasFixedSize(true)
        view.messengerRecyclerView.itemAnimator = DefaultItemAnimator()
        view.messengerRecyclerView.adapter = mAdapter
    }

    override fun getListMessage() {
        mPresenter.getListMessage()
    }

    companion object {
        fun getInstance(): ChatFragment {
            return ChatFragment()
        }
    }
}
