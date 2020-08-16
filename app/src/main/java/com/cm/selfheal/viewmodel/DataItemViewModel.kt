package com.cm.selfheal.viewmodel

import android.graphics.drawable.Drawable
import android.text.TextUtils
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.cm.selfheal.model.HomeGridModel

class DataItemViewModel(private val homeGridModel: HomeGridModel) : BaseObservable() {
    fun setUp() {
        // perform set up tasks, such as adding listeners
    }

    fun tearDown() {
        // perform tear down tasks, such as removing listeners
    }
    @get:Bindable
    val id: Int?
        get() = homeGridModel.id

    @get:Bindable
    val protectionTitle: String?
        get() = if (!TextUtils.isEmpty(homeGridModel.protectionTitle)) homeGridModel.protectionTitle else ""

    @get:Bindable
    val protectionTitleDesc: String?
        get() = if (!TextUtils.isEmpty(homeGridModel.protectionTitleDesc)) homeGridModel.protectionTitleDesc else ""

    @get:Bindable
    val protectionImage: Drawable?
        get() = homeGridModel.protectionImage

    @get:Bindable
    val isProtectionFlag: Boolean
        get() = homeGridModel.isProtectionFlag


}