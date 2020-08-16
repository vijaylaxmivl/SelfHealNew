package com.cm.selfheal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ItemOverviewNetworkBinding
import com.cm.selfheal.model.OverviewListModel

class NetworkOverviewAdapter(val overviewList: ArrayList<OverviewListModel>) :
    RecyclerView.Adapter<NetworkOverviewAdapter.UserViewHolder>() {

    fun updateOverviewList(newOverviewList: List<OverviewListModel>) {
        overviewList.clear()
        overviewList.addAll(newOverviewList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemOverviewNetworkBinding>(inflater, R.layout.item_overview_network, parent, false)

        return UserViewHolder(view)
    }

    override fun getItemCount() = overviewList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.view.overview = overviewList[position]

    }

    class UserViewHolder(var view: ItemOverviewNetworkBinding) : RecyclerView.ViewHolder(view.root)

}