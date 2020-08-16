package com.cm.selfheal.util

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.*

/**
 * Apply font for TextView, EditText, Button, CheckBox, RadioButton, ViewGroup and its child coresecuritylib.view
 */
class FontUtil private constructor() {
    companion object {
        /**
         * Get typeface of Roboto-Regular
         *
         * @param context
         * @return
         */
        fun getTypceFaceRobotoRegular(context: Context): Typeface {
            return Typeface.createFromAsset(context.assets, "fonts/Roboto-Regular.ttf")
        }

        /**
         * Get typeface of Roboto-Medium_0
         *
         * @param context
         * @return
         */
        fun getTypceFaceRobotoMedium_0(context: Context): Typeface {
            return Typeface.createFromAsset(context.assets, "fonts/Roboto-Medium.ttf")
        }

        fun getTypceFaceRobotoBold(context: Context): Typeface {
            return Typeface.createFromAsset(context.assets, "fonts/Roboto-Bold.ttf")
        }

        fun getTypceFaceRobotoBlack(context: Context): Typeface {
            return Typeface.createFromAsset(context.assets, "fonts/Roboto-Black.ttf")
        }
        /**
         * Get typeface of Roboto-Regular
         *
         * @param context
         * @return
         */
        fun getTypceFaceRobotoLight(context: Context): Typeface {
            return Typeface.createFromAsset(context.assets, "fonts/Roboto-Light.ttf")
        }

        /**
         * Apply typeface on control
         *
         * @param view
         * @param typeface
         */
        fun applyTypeface(view: View?, typeface: Typeface?) {
            try {
                if (view is ViewGroup) {
                    val vg = view
                    for (i in 0 until vg.childCount) {
                        val child = vg.getChildAt(i)
                        applyTypeface(child, typeface)
                    }
                } else if (view is TextView) {
                    view.typeface = typeface
                } else if (view is EditText) {
                    view.typeface = typeface
                } else if (view is Button) {
                    view.typeface = typeface
                } else if (view is CheckBox) {
                    view.typeface = typeface
                } else if (view is RadioButton) {
                    view.typeface = typeface
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    init {
        throw Error("Do not need instantiate!")
    }
}