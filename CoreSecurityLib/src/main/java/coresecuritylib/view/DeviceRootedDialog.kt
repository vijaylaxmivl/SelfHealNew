package coresecuritylib.view

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.cm.coresecuritylib.R
import coresecuritylib.util.DeviceRootedCheckUtils
import kotlin.system.exitProcess

class DeviceRootedDialog {
    fun Activity.checkDeviceRoot(): Boolean {
        return if (DeviceRootedCheckUtils.DeviceUtils.isDeviceRooted(this)) {
            AlertDialog.Builder(this)
                .setMessage("Your device is rooted. you can not use this app into rooted device.")
                .setCancelable(false)
                .setPositiveButton("Ok") { _, _ ->
                    exitProcess(0)
                }.show()
            true
        } else {
            false
        }
    }
}