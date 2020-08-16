package com.cm.selfheal.adapter

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ItemOverviewDeviceBinding
import com.cm.selfheal.model.OverviewListModel
import coresecuritylib.util.BasicDetailUtil
import coresecuritylib.util.PhoneSettingDetailUtil.Companion.openDeveloperOptionSetting
import coresecuritylib.util.PhoneSettingDetailUtil.Companion.openDeviceLockSetting
import coresecuritylib.util.PhoneSettingDetailUtil.Companion.openGooglePlayProtectSetting
import kotlinx.android.synthetic.main.item_overview_device.view.*


class DeviceOverviewAdapter(context: Context, val overviewList: ArrayList<OverviewListModel>) :
    RecyclerView.Adapter<DeviceOverviewAdapter.UserViewHolder>() {
     var context: Context=context
    fun updateOverviewList(newOverviewList: List<OverviewListModel>) {
        overviewList.clear()
        overviewList.addAll(newOverviewList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemOverviewDeviceBinding>(
                inflater,
                R.layout.item_overview_device,
                parent,
                false
            )

        return UserViewHolder(view)
    }

    override fun getItemCount() = overviewList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.view.overview = overviewList[position]

        holder.view.titleValue.setOnClickListener { view ->
            if (view.tag.toString()=="6" || view.tag.toString()=="7")
            {
                if(holder.view.titleValue.text.toString().toLowerCase()=="enabled") {

                    openDeveloperOptionSetting(context)
                }
            }
            if (view.tag.toString()=="8")
            {
                if(holder.view.titleValue.text.toString().toLowerCase()=="disabled") {

                    openDeviceLockSetting(context)
                }

            }
            if (view.tag.toString()=="5")
            {
                if(holder.view.titleValue.text.toString().toLowerCase()=="not trusted") {

                    openGooglePlayProtectSetting(context)
                }

            }

        }
    }

    class UserViewHolder(var view: ItemOverviewDeviceBinding) : RecyclerView.ViewHolder(view.root)

}