package com.ccc.chatapp.screens.chat.chatfragment

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccc.chatapp.R
import com.ccc.chatapp.data.model.Message
import kotlinx.android.synthetic.main.item_message.view.*

class AdapterMessage(context: Context?, list: ArrayList<Message>) :
    RecyclerView.Adapter<AdapterMessage.MessageHolder>() {
    private var mContext = context
    private var mListMessage = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false)
        return MessageHolder(view)
    }

    override fun getItemCount(): Int {
        return mListMessage.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.onBind(position)
    }

    fun addMessage(message: Message) {
        mListMessage.add(0, message)
        notifyItemInserted(0)
    }

    inner class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mTimeTextView = itemView.timeTextView
        private var mMessageTextView = itemView.messageTextView
        fun onBind(position: Int) {
            if (mListMessage[position].isSend) {
                mMessageTextView.gravity = Gravity.RIGHT
                mTimeTextView.gravity = Gravity.RIGHT
            } else {
                mMessageTextView.gravity = Gravity.LEFT
                mTimeTextView.gravity = Gravity.LEFT
            }
            mMessageTextView.text = mListMessage[position].message
            mTimeTextView.text = mListMessage[position].time.toDate().toString()
        }
    }
}
