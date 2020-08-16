package com.cm.selfheal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cm.selfheal.applications.Applications
import com.cm.selfheal.model.DetailModel
import com.cm.selfheal.model.OverviewListModel
import com.cm.selfheal.model.OverviewModel
import com.cm.selfheal.model.ThreatListModel
import coresecuritylib.util.BasicDetailUtil
import coresecuritylib.util.PhoneSettingDetailUtil
import coresecuritylib.util.PhoneSettingDetailUtil.Companion.isSEAvailable
import coresecuritylib.util.RootUtil.Companion.isDeviceRootEnabled
import coresecuritylib.util.WifiDetailUtil
import coresecuritylib.util.WifiDetailUtil.Companion.wifiBSSID
import coresecuritylib.util.WifiDetailUtil.Companion.wifiIPAddress
import coresecuritylib.util.WifiDetailUtil.Companion.wifiMAC
import coresecuritylib.util.WifiDetailUtil.Companion.wifiSSID

class DetailViewModel : ViewModel() {
    var currentProtectionFlow = MutableLiveData<String>()
    var currentProtectionFlowId = MutableLiveData<String>()
    var threatNo = MutableLiveData<String>()
    var recommendations = MutableLiveData<String>()
    var detailModelList = MutableLiveData<ArrayList<DetailModel>>()
    var currentProtectionFlowFlag = MutableLiveData<Boolean>()
    val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    init {
        //  dataCollection()
        threatNo.value = "5"
    }


    fun dataCollection() {
        var deviceProtectionFlag = true
        var detailModel = DetailModel()
        var overviewArrayList = ArrayList<OverviewListModel>()
        var overviewModel = OverviewModel()
        // Log.e("overviewListModel", "############ " + currentProtectionFlowId.value.toString())
        if (currentProtectionFlowId.value.toString() == "1") {
            var overviewListModel1 = OverviewListModel()
            overviewListModel1.id = 1
            overviewListModel1.name = "Android Version"
            overviewListModel1.value1 = BasicDetailUtil.getPhoneVersion()
            overviewArrayList.add(overviewListModel1)
            var overviewListModel2 = OverviewListModel()
            overviewListModel2.id = 2
            overviewListModel2.name = "Security Patch Level"
            overviewListModel2.value1 = BasicDetailUtil.getPhoneSecurityPatch()
            overviewArrayList.add(overviewListModel2)
            var overviewListModel3 = OverviewListModel()
            overviewListModel3.id = 3
            overviewListModel3.name = "SE for Android Status"
            if(isSEAvailable(Applications.applicationContext())) {
                overviewListModel3.value1 = "Enforcing"
            }else{
                overviewListModel3.value1 = "Permissive"
            }
            overviewArrayList.add(overviewListModel3)
            var overviewListModel4 = OverviewListModel()
            overviewListModel4.id = 4
            overviewListModel4.name = "Device Rooted?"
            overviewListModel4.value1 = "No"
            overviewArrayList.add(overviewListModel4)
            var overviewListModel5 = OverviewListModel()
            overviewListModel5.id = 5
            overviewListModel5.name = "App Download Source"
            if (PhoneSettingDetailUtil.isDeviceUnknownSourceEnabled(Applications.applicationContext())) {
                overviewListModel5.value1 = "Trusted"
                overviewListModel5.flag = false
            } else {
                deviceProtectionFlag = false
                overviewListModel5.flag = true
                overviewListModel5.value1 = "Not trusted"
            }
            overviewArrayList.add(overviewListModel5)
            var overviewListModel6 = OverviewListModel()
            overviewListModel6.id = 6
            overviewListModel6.name = "Developer Option"
            if (isDeviceRootEnabled(Applications.applicationContext())) {
                overviewListModel6.flag = true
                overviewListModel6.value1 = "Enabled"
            } else {
                deviceProtectionFlag = false
                overviewListModel6.flag = false
                overviewListModel6.value1 = "Disabled"
            }
            overviewArrayList.add(overviewListModel6)
            var overviewListModel7 = OverviewListModel()
            overviewListModel7.id = 7
            overviewListModel7.name = "USB Debugging"
            if (PhoneSettingDetailUtil.isDeviceDebugEnabled(Applications.applicationContext())) {
                overviewListModel7.flag = true
                overviewListModel7.value1 = "Enabled"
            } else {
                deviceProtectionFlag = false
                overviewListModel7.flag = false
                overviewListModel7.value1 = "Disabled"
            }
            overviewArrayList.add(overviewListModel7)

            var overviewListModel8 = OverviewListModel()
            overviewListModel8.id = 8
            overviewListModel8.name = "Device Lock"
            if (PhoneSettingDetailUtil.isDeviceScreenLocked(Applications.applicationContext())) {
                overviewListModel8.flag = false
                overviewListModel8.value1 = "Enabled"
            } else {
                deviceProtectionFlag = false
                overviewListModel8.flag = true
                overviewListModel8.value1 = "Disabled"
            }
            overviewArrayList.add(overviewListModel8)

            var overviewListModel9 = OverviewListModel()
            overviewListModel9.id = 9
            overviewListModel9.name = "No. of Issues"
            overviewListModel9.value1 = "5"
            overviewArrayList.add(overviewListModel9)
            if (!deviceProtectionFlag) {
                currentProtectionFlowFlag.value=false
                currentProtectionFlow.value= "Device Protection is Disabled"
            }else{
                currentProtectionFlowFlag.value=true
                currentProtectionFlow.value= "Device Protection is Enabled"
            }

        } else if (currentProtectionFlowId.value.toString() == "2") {
            WifiDetailUtil.setContext(Applications.applicationContext())
            WifiDetailUtil.wifiDetail()
         /*   Log.e("####", "wifiSSID: " + WifiDetailUtil.wifiManager.wifiSSID())
            Log.e("####", "wifiBSSID: " + WifiDetailUtil.wifiManager.wifiBSSID())
            Log.e("####", "wifiMAC: " + WifiDetailUtil.wifiManager.wifiMAC())
            Log.e("####", "wifiIPAddress: " + WifiDetailUtil.wifiManager.wifiIPAddress())*/

            var overviewListModel1 = OverviewListModel()
            overviewListModel1.id = 1
            overviewListModel1.name = "IP"
            overviewListModel1.value1 = WifiDetailUtil.wifiManager.wifiIPAddress()
            overviewArrayList.add(overviewListModel1)

            var overviewListModel2 = OverviewListModel()
            overviewListModel2.id = 2
            overviewListModel2.name = "SSID"
            overviewListModel2.value1 = WifiDetailUtil.wifiManager.wifiSSID()
            overviewArrayList.add(overviewListModel2)

            var overviewListModel3 = OverviewListModel()
            overviewListModel3.id = 3
            overviewListModel3.name = "BSSID"
            overviewListModel3.value1 = WifiDetailUtil.wifiManager.wifiBSSID()
            overviewArrayList.add(overviewListModel3)

            var overviewListModel4 = OverviewListModel()
            overviewListModel4.id = 4
            overviewListModel4.name = "MAC"
            overviewListModel4.value1 = WifiDetailUtil.wifiManager.wifiMAC()
            overviewArrayList.add(overviewListModel4)

            var overviewListModel5 = OverviewListModel()
            overviewListModel5.id = 5
            overviewListModel5.name = "No. Of Issues"
            overviewListModel5.value1 = "5"
            overviewArrayList.add(overviewListModel5)


        } else if (currentProtectionFlowId.value.toString() == "3") {
            val builder = StringBuilder()
            var overviewListModel1 = OverviewListModel()
            overviewListModel1.id = 1
            overviewListModel1.name = "Apps Scanned"
            overviewListModel1.date = "06 Aug 20"
            overviewListModel1.time = "10:20"
            var arrayList0: ArrayList<String> = ArrayList<String>()
            arrayList0.add("12")
            builder.clear()
            for (details in arrayList0) {
                builder.append(details + "\n")
            }
            overviewListModel1.value1 = builder.toString()
            overviewArrayList.add(overviewListModel1)

            var overviewListModel2 = OverviewListModel()
            overviewListModel2.id = 2
            overviewListModel2.name = "Malicious Apps"
            var arrayList: ArrayList<String> = ArrayList<String>()
            arrayList.add("CamScanner")
            arrayList.add("Tiktok")
            overviewListModel2.listValue = arrayList
            builder.clear()
            for (details in arrayList) {
                builder.append(details + "\n")
            }
            overviewListModel2.value1 = builder.toString()
            overviewArrayList.add(overviewListModel2)

            var overviewListModel3 = OverviewListModel()
            overviewListModel3.id = 2
            overviewListModel3.name = "Leaky Apps"
            var arrayList1: ArrayList<String> = ArrayList<String>()
            arrayList1.add("ShareIt")
            arrayList1.add("US Browser")
            overviewListModel2.listValue = arrayList1
            builder.clear()
            for (details in arrayList1) {
                builder.append(details + "\n")
            }
            overviewListModel3.value1 = builder.toString()
            overviewArrayList.add(overviewListModel3)

            var overviewListModel4 = OverviewListModel()
            overviewListModel4.id = 2
            overviewListModel4.name = "Malicious Profile"
            var arrayList2: ArrayList<String> = ArrayList<String>()
            arrayList2.add("None")
            overviewListModel2.listValue = arrayList2
            builder.clear()
            for (details in arrayList2) {
                builder.append(details + "\n")
            }
            overviewListModel4.value1 = builder.toString()
            overviewArrayList.add(overviewListModel4)


        } else if (currentProtectionFlowId.value.toString() == "4") {
            val builder = StringBuilder()
            var overviewListModel1 = OverviewListModel()
            overviewListModel1.id = 1
            overviewListModel1.name = "Phishing Attempts"
            var arrayList1: ArrayList<String> = ArrayList<String>()
            arrayList1.add("02")
            overviewListModel1.listValue = arrayList1
            overviewListModel1.value2 = ""
            builder.clear()
            for (details in arrayList1) {
                builder.append(details + "\n")
            }
            overviewListModel1.value1 = builder.toString()
            overviewArrayList.add(overviewListModel1)

            var overviewListModel2 = OverviewListModel()
            overviewListModel2.id = 1
            overviewListModel2.name = "Attack Modes"
            var arrayList2: ArrayList<String> = ArrayList<String>()
            arrayList2.add("Web")
            arrayList2.add("Email")
            overviewListModel2.listValue = arrayList2
            overviewListModel2.value2 = "Detected & Prevented"
            builder.clear()
            for (details in arrayList2) {
                builder.append(details + "\n")
            }
            overviewListModel2.value1 = builder.toString()
            overviewArrayList.add(overviewListModel2)
        }


        var threatArrayList = ArrayList<ThreatListModel>()
        if (currentProtectionFlowId.value.toString() == "1") {
            var threatModel = ThreatListModel()
            threatModel.id = 1
            threatModel.name = "DEVICE ROOTING"
            threatModel.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel.date = "04-Jul 20"
            threatModel.time = "10:13"
            threatModel.flag = true
            threatArrayList.add(threatModel)
            var threatModel1 = ThreatListModel()
            threatModel1.id = 2
            threatModel1.name = "USB DEBUGGING"
            threatModel1.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel1.date = "04-Jul 20"
            threatModel1.time = "10:12"
            threatModel1.flag = false
            threatArrayList.add(threatModel1)
            var threatModel2 = ThreatListModel()
            threatModel2.id = 3
            threatModel2.name = "UNTRUSTED APP SOURCE"
            threatModel2.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel2.date = "04-Jul 20"
            threatModel2.time = "10:10"
            threatModel2.flag = false
            threatArrayList.add(threatModel2)

        } else if (currentProtectionFlowId.value.toString() == "2") {
            var threatModel = ThreatListModel()
            threatModel.id = 1
            threatModel.name = "NETWORK ATTACK"
            threatModel.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel.date = "04-Jul 20"
            threatModel.time = "10:13"
            threatModel.flag = true
            threatArrayList.add(threatModel)
            var threatModel1 = ThreatListModel()
            threatModel1.id = 2
            threatModel1.name = "MITM ATTACK"
            threatModel1.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel1.date = "04-Jul 20"
            threatModel1.time = "10:12"
            threatModel1.flag = true
            threatArrayList.add(threatModel1)
            var threatModel2 = ThreatListModel()
            threatModel2.id = 3
            threatModel2.name = "SCAN (wifi-1)"
            threatModel2.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel2.date = "04-Jul 20"
            threatModel2.time = "10:10"
            threatModel2.flag = false
            threatArrayList.add(threatModel2)

        } else if (currentProtectionFlowId.value.toString() == "3") {
            var threatModel = ThreatListModel()
            threatModel.id = 1
            threatModel.name = "MALICIOUS APP"
            threatModel.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel.date = "04-Jul 20"
            threatModel.time = "10:13"
            threatModel.flag = true
            threatArrayList.add(threatModel)
            var threatModel1 = ThreatListModel()
            threatModel1.id = 2
            threatModel1.name = "LEAKY APP"
            threatModel1.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel1.date = "04-Jul 20"
            threatModel1.time = "10:12"
            threatModel1.flag = false
            threatArrayList.add(threatModel1)
            var threatModel2 = ThreatListModel()
            threatModel2.id = 3
            threatModel2.name = "MALICIOUS PROFILE"
            threatModel2.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel2.date = "04-Jul 20"
            threatModel2.time = "10:10"
            threatModel2.flag = false
            threatArrayList.add(threatModel2)

        } else if (currentProtectionFlowId.value.toString() == "4") {
            var threatModel = ThreatListModel()
            threatModel.id = 1
            threatModel.name = "FAKE BANKING SITE"
            threatModel.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel.date = "04-Jul 20"
            threatModel.time = "10:13"
            threatModel.flag = true
            threatArrayList.add(threatModel)
            var threatModel1 = ThreatListModel()
            threatModel1.id = 2
            threatModel1.name = "FAKE WEBSITE"
            threatModel1.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel1.date = "04-Jul 20"
            threatModel1.time = "10:12"
            threatModel1.flag = false
            threatArrayList.add(threatModel1)
            var threatModel2 = ThreatListModel()
            threatModel2.id = 3
            threatModel2.name = "FAKE SOCIAL MEDIA SITE"
            threatModel2.desc =
                "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
            threatModel2.date = "04-Jul 20"
            threatModel2.time = "10:10"
            threatModel2.flag = false
            threatArrayList.add(threatModel2)

        }

        overviewModel.id = 1
        overviewModel.recommendations =
            "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
        overviewModel.overviewListModel.addAll(overviewArrayList)
        recommendations.value =
            "Protect data on portable devices, smartphones, tablets, and personal computers, and the network connected to those devices with Mobile device security"
        detailModel.overviewModel = overviewModel

        detailModel.id = 1
        detailModel.category = currentProtectionFlow.value.toString()
        detailModel.noOfThreat = 5
        detailModel.threatListModel.addAll(threatArrayList)


        var tempArrayList = ArrayList<DetailModel>()
        tempArrayList.add(detailModel)
        detailModelList.value = tempArrayList


    }

}