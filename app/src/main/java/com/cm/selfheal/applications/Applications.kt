package com.cm.selfheal.applications

import android.app.Application
import android.content.Context
import androidx.databinding.DataBindingUtil
import com.cm.selfheal.databinding.AppDataBindingComponent

class Applications: Application(){

    init {
        instance = this
    }

    companion object {
        private var instance: Application? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any
        DataBindingUtil.setDefaultComponent(AppDataBindingComponent())

    }
}