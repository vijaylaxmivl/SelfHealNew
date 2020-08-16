package com.cm.selfheal.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ItemThreatBinding

import com.cm.selfheal.model.ThreatListModel
import com.cm.selfheal.view.activity.ThreatDetailActivity

class ThreatListAdapter(val threatList: ArrayList<ThreatListModel>) :
    RecyclerView.Adapter<ThreatListAdapter.UserViewHolder>() {
    lateinit var context:Context
    fun updateThreatList(newThreatList: ArrayList<ThreatListModel>) {
        threatList.clear()
        threatList.addAll(newThreatList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        context = parent.getContext();
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemThreatBinding>(
                inflater,
                R.layout.item_threat,
                parent,
                false
            )

        return UserViewHolder(view)
    }

    override fun getItemCount() = threatList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.view.threat = threatList[position]
        holder.view?.cardView?.setOnClickListener(View.OnClickListener { view ->
            var intent = Intent(context, ThreatDetailActivity::class.java)
            intent.putExtra("THREAT_ID", threatList.get(position).id.toString())
            intent.putExtra("THREAT_NAME", threatList.get(position).name)
            context.startActivity(intent)

        })

    }

    class UserViewHolder(var view: ItemThreatBinding) : RecyclerView.ViewHolder(view.root)

}