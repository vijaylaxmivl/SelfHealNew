package com.cm.selfheal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ItemOverviewPhishingBinding
import com.cm.selfheal.model.OverviewListModel

class PhishingOverviewAdapter(val overviewList: ArrayList<OverviewListModel>) :
    RecyclerView.Adapter<PhishingOverviewAdapter.UserViewHolder>() {

    fun updateOverviewList(newOverviewList: List<OverviewListModel>) {
        overviewList.clear()
        overviewList.addAll(newOverviewList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemOverviewPhishingBinding>(inflater, R.layout.item_overview_phishing, parent, false)

        return UserViewHolder(view)
    }

    override fun getItemCount() = overviewList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.view.overview = overviewList[position]

    }

    class UserViewHolder(var view: ItemOverviewPhishingBinding) : RecyclerView.ViewHolder(view.root)

}