package com.cm.selfheal.viewmodel

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.cm.selfheal.BR
import com.cm.selfheal.R
import com.cm.selfheal.adapter.HomeGridAdapter
import com.cm.selfheal.applications.Applications
import com.cm.selfheal.model.HomeGridModel
import coresecuritylib.util.BasicDetailUtil
import coresecuritylib.util.PhoneSettingDetailUtil
import java.util.*

class DataViewModel(context: Context) : BaseObservable() {
    lateinit var context:Context
    @get:Bindable
    val adapter: HomeGridAdapter
    private val data: MutableList<HomeGridModel>
    fun setUp() {
        // perform set up tasks, such as adding listeners, data population, etc.
        populateData()
    }

    fun tearDown() {
        // perform tear down tasks, such as removing listeners
    }

    @Bindable
    fun getData(): List<HomeGridModel> {
        return data
    }

    private fun populateData() {
        // populate the data from the source, such as the database.
        var drawable: Drawable? =
            ContextCompat.getDrawable(Applications.applicationContext(), R.drawable.smartphone)
        val dataModel1 = HomeGridModel()
        dataModel1.id=1
        dataModel1.protectionTitle = "Device Protection"
        dataModel1.protectionTitleDesc = BasicDetailUtil.getPhoneDeviceBrand()
        dataModel1.protectionImage
        dataModel1.protectionImage = drawable
        dataModel1.isProtectionFlag = PhoneSettingDetailUtil.isDeviceProtected(context)
        data.add(dataModel1)

        val dataModel2 = HomeGridModel()
        dataModel2.id=2
        dataModel2.protectionTitle = "Network Protection"
        dataModel2.protectionTitleDesc = "Wifi-1"
        drawable = ContextCompat.getDrawable(Applications.applicationContext(), R.drawable.wifi)
        dataModel2.protectionImage = drawable
        dataModel2.isProtectionFlag = true
        data.add(dataModel2)

        val dataModel3 = HomeGridModel()
        dataModel3.id=3
        dataModel3.protectionTitle = "Apps Protection"
        dataModel3.protectionTitleDesc = "Safe and Secure"
        drawable = ContextCompat.getDrawable(Applications.applicationContext(), R.drawable.app)
        dataModel3.protectionImage = drawable
        dataModel3.isProtectionFlag = true
        data.add(dataModel3)

        val dataModel4 = HomeGridModel()
        dataModel4.id=4
        dataModel4.protectionTitle = "Anti-Phishing"
        dataModel4.protectionTitleDesc = "Enabled"
        drawable = ContextCompat.getDrawable(Applications.applicationContext(), R.drawable.phishing)
        dataModel4.protectionImage = drawable
        dataModel4.isProtectionFlag = true
        data.add(dataModel4)

        notifyPropertyChanged(BR.data)
    }

    companion object {
        private const val TAG = "DataViewModel"
    }

    init {
        this.context=context
        data = ArrayList()
        adapter = HomeGridAdapter(context)
    }
}