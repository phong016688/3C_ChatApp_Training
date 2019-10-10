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
        super.onCreate(savedInstanceState)
        (activity?.application as Application).getAppComponent().inject(this)
        mPresenter = ChatFragmentPresenterImpl(this, mSchedulerProvider, mUserRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_messenger, container, false)
        setUpRecyclerView(view)
        handleEvent(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    override fun onPause() {
        mPresenter.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        mPresenter.onDestroy()
        super.onDestroyView()
    }

    override fun clearListMessage() {
        mAdapter.clear()
    }

    override fun updateListMessage(message: Message) {
        mAdapter.addMessage(message)
    }

    override fun listenerListMessage() {
        mPresenter.listenerListMessage()
    }

    private fun handleEvent(view: View) {
        view.sendImageView.setOnClickListener {
            mPresenter.sendMessage(Message().apply {
                text = view.messengerEditText.text.toString()
                isMySend = true
                timeSend = System.currentTimeMillis().toString()
            })
        }
    }

    private fun setUpRecyclerView(view: View) {
        val messages = ArrayList<Message>()
        mAdapter = AdapterMessage(context, messages)
        view.messengerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
    }

    companion object {
        fun getInstance(): ChatFragment {
            return ChatFragment()
        }
    }
}
