package com.g02.yama.recycler_views

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.g02.yama.domain.Member

class TeamMembersRecycleView() :
        RecyclerView.Adapter<TeamMembersRecycleView.MembersViewHolder>() {

    lateinit var members: LiveData<Array<Member>>
    lateinit var clickListener: (String) -> Unit
    lateinit var owner: LifecycleOwner

    constructor(lifeCycle: LifecycleOwner, data: LiveData<Array<Member>>, clickListener: (String) -> Unit) : this() {
        this.members = data
        owner = lifeCycle
        this.clickListener = clickListener
        members.observe(lifeCycle, Observer {
            notifyDataSetChanged()
        })
    }


    class MembersViewHolder(val viewHolder: TextView) : RecyclerView.ViewHolder(viewHolder)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TeamMembersRecycleView.MembersViewHolder {
        val view = TextView(parent.context)
        view.textSize = 18F
        return MembersViewHolder(view)
    }

    override fun getItemCount(): Int = members.value!!.size

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        holder.viewHolder.text = members.value!![position].toString()
        holder.viewHolder.setOnClickListener{
            clickListener(members.value!![position].login)
        }

    }






}