package com.cm.selfheal.model

data class DetailModel(
    var id:Int = 0,
    var category:String = "",
    var noOfThreat:Int = 0,
    var threatListModel: ArrayList<ThreatListModel> = ArrayList<ThreatListModel>(),
    var overviewModel:OverviewModel=OverviewModel()
){

}
