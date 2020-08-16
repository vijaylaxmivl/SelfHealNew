package com.cm.selfheal.viewmodel

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.cm.selfheal.BR
import com.cm.selfheal.R
import com.cm.selfheal.adapter.ThreatDetailAdapter
import com.cm.selfheal.applications.Applications
import com.cm.selfheal.model.ThreatDetailModel
import java.util.*

class ThreatDetailViewModel(context: Context) : BaseObservable() {
    lateinit var context: Context
    var flag: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var threatName: MutableLiveData<String> = MutableLiveData<String>()
    var threatDate: MutableLiveData<String> = MutableLiveData<String>()
    var threatTime: MutableLiveData<String> = MutableLiveData<String>()

    @get:Bindable
    val adapter: ThreatDetailAdapter
    private val data: MutableList<ThreatDetailModel>
    fun setUp() {
        // perform set up tasks, such as adding listeners, data population, etc.
        populateData()
        threatDate.value="04-Jul 20"

        threatTime.value="10:20"

    }

    fun tearDown() {
        // perform tear down tasks, such as removing listeners
    }

    @Bindable
    fun getData(): List<ThreatDetailModel> {
        return data
    }


    private fun populateData() {
        // populate the data from the source, such as the database.
        val dataModel1 = ThreatDetailModel()
        dataModel1.id = 1
        dataModel1.protectionTitle = "Description"
        dataModel1.protectionTitleDesc =
            "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
        dataModel1.isProtectionFlag = true
        data.add(dataModel1)

        val dataModel2 = ThreatDetailModel()
        dataModel2.id = 2
        dataModel2.protectionTitle = "Implications"
        dataModel2.protectionTitleDesc =
            "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
        dataModel2.isProtectionFlag = false
        data.add(dataModel2)

        val dataModel3 = ThreatDetailModel()
        dataModel3.id = 3
        dataModel3.protectionTitle = "Recommendations"
        dataModel3.protectionTitleDesc =
            "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
        dataModel3.isProtectionFlag = true
        data.add(dataModel3)


        notifyPropertyChanged(BR.data)
    }

    companion object {
        private const val TAG = "DataViewModel"
    }

    init {
        this.context = context
        data = ArrayList()
        adapter = ThreatDetailAdapter(context)
    }
}