package coresecuritylib.util

import android.content.Context
import android.provider.Settings
import java.io.File

class RootUtil {

    companion object {
        @JvmStatic
        fun isDeviceRootEnabled(context: Context): Boolean {

            return when {
                Integer.valueOf(android.os.Build.VERSION.SDK) == 16 -> {
                    Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0
                    ) != 0;
                }
                Integer.valueOf(android.os.Build.VERSION.SDK) >= 17 -> {
                    Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
                    ) != 0;
                }
                else -> false
            };


            return false
        }


        @JvmStatic
        fun checkForSuBinary(): Boolean {
            return checkForBinary("su")
        }

        @JvmStatic
        fun checkForBusyBoxBinary(): Boolean {
            return checkForBinary("busybox")
        }
        /**
         * @param filename - check for this existence of this
         * file("su","busybox")
         * @return true if exists
         */
        private fun checkForBinary(filename: String): Boolean {
            val binaryPaths = arrayOf(
                "/data/local/",
                "/data/local/bin/",
                "/data/local/xbin/",
                "/sbin/",
                "/su/bin/",
                "/system/bin/",
                "/system/bin/.ext/",
                "/system/bin/failsafe/",
                "/system/sd/xbin/",
                "/system/usr/we-need-root/",
                "/system/xbin/",
                "/system/app/Superuser.apk",
                "/cache",
                "/data",
                "/dev"
            )
            for (path in binaryPaths) {
                val f = File(path, filename)
                val fileExists: Boolean = f.exists()
                if (fileExists) {
                    return true
                }
            }
            return false
        }

    }
}