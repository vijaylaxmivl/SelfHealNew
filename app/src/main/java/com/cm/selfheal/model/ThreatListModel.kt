package com.cm.selfheal.model

import java.util.*

data class ThreatListModel(
    var id: Int = 0,
    var name: String = "",
    var desc: String = "",
    var date: String = "",
    var time: String = "",
    var dateTime: Date? = null,
    var flag: Boolean = false
) {

}


