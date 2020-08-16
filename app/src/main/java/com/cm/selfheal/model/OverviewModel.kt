package com.cm.selfheal.model

import java.util.*
import kotlin.collections.ArrayList

data class OverviewModel(
    var id: Int = 0,
    var recommendations: String = "",
    var overviewListModel: ArrayList<OverviewListModel> = ArrayList<OverviewListModel>()
) {

}

data class OverviewListModel(
    var id: Int = 0,
    var name: String = "",
    var value1: String = "",
    var value2: String = "",
    var time: String = "",
    var dateTime: Date? = null,
    var date: String? = "",
    var flag:Boolean=false,
    var listValue: ArrayList<String> = ArrayList<String>()

) {

}