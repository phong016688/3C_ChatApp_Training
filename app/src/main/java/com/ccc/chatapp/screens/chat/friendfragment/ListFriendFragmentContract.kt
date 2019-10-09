package com.ccc.chatapp.screens.chat.friendfragment

import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.screens.BasePresenter

interface ListFriendFragmentView {
    fun updateListFriend(user: User)
}

interface ListFriendFragmentPresenter : BasePresenter {
    fun getListFriend()
    fun goToChatFragment(user: User)
}
