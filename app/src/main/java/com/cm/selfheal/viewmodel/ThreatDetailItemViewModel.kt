package com.cm.selfheal.viewmodel

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.cm.selfheal.model.ThreatDetailModel

class ThreatDetailItemViewModel(private val threatDetailModel: ThreatDetailModel) : BaseObservable() {
    fun setUp() {
        // perform set up tasks, such as adding listeners
    }

    fun tearDown() {
        // perform tear down tasks, such as removing listeners
    }

    @get:Bindable
    val id: Int?
        get() = threatDetailModel.id

    @get:Bindable
    val protectionTitle: String?
        get() = if (!TextUtils.isEmpty(threatDetailModel.protectionTitle)) threatDetailModel.protectionTitle else ""

    @get:Bindable
    val protectionTitleDesc: String?
        get() = if (!TextUtils.isEmpty(threatDetailModel.protectionTitleDesc)) threatDetailModel.protectionTitleDesc else ""

    /*@get:Bindable
    val protectionImage: Drawable?
        get() = homeGridModel.protectionImage
*/
    @get:Bindable
    val isProtectionFlag: Boolean
        get() = threatDetailModel.isProtectionFlag

    fun getClick() {

    }


}