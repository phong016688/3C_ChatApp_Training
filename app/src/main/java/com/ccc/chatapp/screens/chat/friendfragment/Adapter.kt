package com.ccc.chatapp.screens.chat.friendfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccc.chatapp.R
import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.extension.loadImageUser
import com.ccc.chatapp.screens.chat.ItemClickListener
import kotlinx.android.synthetic.main.item_user.view.*

class AdapterFriendList(context: Context?, listFriend: ArrayList<User>) :
    RecyclerView.Adapter<AdapterFriendList.FriendHolder>() {
    private var mListFriend = listFriend
    private var mItemClickListener: ItemClickListener? = null
    private var mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false)
        return FriendHolder(view)
    }

    override fun getItemCount(): Int {
        return mListFriend.size
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        holder.onBind(position)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    fun addUser(user: User) {
        mListFriend.add(0, user)
        this.notifyItemInserted(0)
    }

    fun clear() {
        mListFriend.clear()
        this.notifyDataSetChanged()
    }

    inner class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var mItemAvatarImageView = itemView.itemAvatarImageView
        private var mItemUserNameText = itemView.itemUserNameText

        override fun onClick(p0: View?) {
            mItemClickListener?.onClick(p0, mListFriend[adapterPosition])
        }

        fun onBind(position: Int) {
            mItemAvatarImageView.loadImageUser(mListFriend[position].avatar)
            mItemUserNameText.text = mListFriend[position].userName
            itemView.setOnClickListener(this)
        }
    }
}
