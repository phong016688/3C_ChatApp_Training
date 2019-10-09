package com.ccc.chatapp.screens.chat.chatfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ccc.chatapp.R
import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.screens.chat.ItemClickListener

class ChatFragment : Fragment(),
    ItemClickListener {
    override fun onClick(view: View?, user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messenger, container, false)
    }

    companion object{
        fun getInstance() : ChatFragment{
            return ChatFragment()
        }
    }

}
