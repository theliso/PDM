package com.g02.yama.recycler_views

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.g02.yama.domain.Team

class TeamsRecyclerView() :
        androidx.recyclerview.widget.RecyclerView.Adapter<TeamsRecyclerView.TeamsViewHolder>() {

    lateinit var teams: LiveData<Array<Team>>
    lateinit var owner: LifecycleOwner
    lateinit var clickListener: (Int) -> Unit

    constructor(cycle: LifecycleOwner, teams: LiveData<Array<Team>>, clickListener: (Int) -> Unit) : this() {
        this.teams = teams
        owner = cycle
        this.clickListener = clickListener
        teams.observe(cycle, Observer {
            notifyDataSetChanged()
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val textView = TextView(parent.context)
        textView.textSize = 21F
        return TeamsViewHolder(textView)
    }

    override fun getItemCount(): Int = teams.value!!.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.viewHolder.text = teams.value!![position].name
        holder.viewHolder.setOnClickListener {
            clickListener(teams.value!![position].id)
        }
    }

    class TeamsViewHolder(val viewHolder: TextView) : androidx.recyclerview.widget.RecyclerView.ViewHolder(viewHolder)

}