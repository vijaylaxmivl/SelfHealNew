package coresecuritylib.util

import android.content.Context
import java.io.File

class DeviceRootedCheckUtils {
    object DeviceUtils {
        fun isDeviceRooted(context: Context?): Boolean {
            return isRooted1 || isRooted2
        }

        private val isRooted1: Boolean
            get() {
                val file = File("/system/app/Superuser.apk")
                return file.exists()
            }

        // try executing commands
        private val isRooted2: Boolean
            get() = (canExecuteCommand("/system/xbin/which su")
                    || canExecuteCommand("/system/bin/which su")
                    || canExecuteCommand("which su"))

        private fun canExecuteCommand(command: String): Boolean {
            return try {
                Runtime.getRuntime().exec(command)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}