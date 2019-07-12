package com.g02.yama.recycler_views

import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.g02.yama.repository.dataAccess.dtos.UserChatBoard

class ChatRecyclerView() : RecyclerView.Adapter<ChatRecyclerView.ChatViewHolder>() {

    private lateinit var messages: LiveData<ArrayList<UserChatBoard>>

    constructor(owner : LifecycleOwner, message: LiveData<ArrayList<UserChatBoard>>) : this() {
        this.messages = message
        messages.observe(owner, Observer {
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ChatRecyclerView.ChatViewHolder {
        val view = TextView(parent.context)
        view.textSize = 20F
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int = messages.value!!.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.viewHolder.text = messages.value?.get(position).toString()
    }

    class ChatViewHolder(val viewHolder : TextView) : RecyclerView.ViewHolder(viewHolder)



}