package coresecuritylib.util

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Context.KEYGUARD_SERVICE
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import com.google.android.gms.safetynet.SafetyNet
import coresecuritylib.util.RootUtil.Companion.isDeviceRootEnabled
import java.io.BufferedReader
import java.io.InputStreamReader


class PhoneSettingDetailUtil {

    companion object {
        @JvmStatic
        fun isDeviceProtected(context: Context): Boolean {

            if (!isDeviceRootEnabled(context)) {
                return false
            }
            if (!isDeviceDebugEnabled(context)) {
                return false
            }
            if (!isDeviceUnknownSourceEnabled(context) || !getSafetyNet(context)) {
                return false
            }
            if (!isDeviceScreenLocked(context)) {
                return false
            }
            return true
        }


        @JvmStatic
        fun isDeviceDebugEnabled(context: Context): Boolean {

            return when {
                Integer.valueOf(android.os.Build.VERSION.SDK) == 16 -> {
                    Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Secure.ADB_ENABLED,
                        0
                    ) != 0;
                }
                Integer.valueOf(android.os.Build.VERSION.SDK) >= 17 -> {
                    Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Global.ADB_ENABLED, 0
                    ) != 0;
                }
                else -> false
            };


            return false
        }

        @JvmStatic
        fun isDeviceUnknownSourceEnabled(context: Context): Boolean {

            return when {
                Integer.valueOf(android.os.Build.VERSION.SDK) <= 16 -> {
                    Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Secure.INSTALL_NON_MARKET_APPS,
                        0
                    ) != 0;
                }
                Integer.valueOf(android.os.Build.VERSION.SDK) >= 17 -> {
                    getSafetyNet(context)

                }
                else -> false
            };


            return false
        }

        fun getSafetyNet(context: Context): Boolean {
            var flag: Boolean = false
            try {
                SafetyNet.getClient(context).isVerifyAppsEnabled.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        if (result.isVerifyAppsEnabled) {
                            flag = true
                            Log.d(
                                "TAG",
                                "The Verify Apps feature is enabled."
                            )
                        } else {
                            flag = false
                            Log.d(
                                "TAG",
                                "The Verify Apps feature is disabled."
                            )
                        }
                    } else {
                        flag = false
                        Log.e("TAG", "A general error occurred.")
                    }
                }

            } catch (exc: Exception) {

            }
            return flag
        }


        fun isDeviceScreenLocked(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                isDeviceLocked(context)
            } else {
                isPatternSet(context) || isPassOrPinSet(context)
            }
        }

        /**
         * @return true if pattern set, false if not (or if an issue when checking)
         */
        private fun isPatternSet(context: Context): Boolean {
            val cr: ContentResolver = context.contentResolver
            return try {
                val lockPatternEnable = Settings.Secure.getInt(
                    cr,
                    Settings.Secure.LOCK_PATTERN_ENABLED
                )
                lockPatternEnable == 1

            } catch (e: SettingNotFoundException) {
                false
            }
        }

        /**
         * @return true if pass or pin set
         */
        private fun isPassOrPinSet(context: Context): Boolean {
            val keyguardManager =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager //api 16+

            return keyguardManager.isKeyguardSecure
        }

        /**
         * @return true if pass or pin or pattern locks screen
         */
        @TargetApi(23)
        private fun isDeviceLocked(context: Context): Boolean {
            val keyguardManager =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager //api 23+
            return keyguardManager.isDeviceSecure
        }

        fun getGrantedPermissions(context: Context, appPackage: String): List<String>? {
            val granted: MutableList<String> = ArrayList()
            try {
                val pi: PackageInfo = context.packageManager.getPackageInfo(
                    appPackage,
                    PackageManager.GET_PERMISSIONS
                )
                for (i in pi.requestedPermissions.indices) {
                    if (pi.requestedPermissionsFlags[i] and PackageInfo.REQUESTED_PERMISSION_GRANTED != 0) {
                        granted.add(pi.requestedPermissions[i])
                    }
                }
            } catch (e: java.lang.Exception) {
            }
            for (element in granted) {
                println(element)
            }
            return granted
        }

        @JvmStatic
        fun openDeveloperOptionSetting(context: Context) {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            context.startActivity(intent)
        }

        @JvmStatic
        fun openDeviceLockSetting(context: Context) {
            val intent =
                Intent(Settings.ACTION_SECURITY_SETTINGS)
            context.startActivity(intent)
        }

        @JvmStatic
        fun openGooglePlayProtectSetting(context: Context) {
            val intent = Intent()
            val GOOGLE_PLAY_SETTINGS_COMPONENT = "com.google.android.gms"
            val GOOGLE_PLAY_SETTINGS_ACTIVITY = ".security.settings.VerifyAppsSettingsActivity"
            intent.setClassName(
                GOOGLE_PLAY_SETTINGS_COMPONENT,
                GOOGLE_PLAY_SETTINGS_COMPONENT + GOOGLE_PLAY_SETTINGS_ACTIVITY
            )
            context.startActivity(intent)
        }

        @JvmStatic
        fun isBiometricsAvailable(context: Context): Boolean {
            var canAuthenticate = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT < 29) {
                    val keyguardManager: KeyguardManager =
                        context.getSystemService(KEYGUARD_SERVICE) as KeyguardManager
                    val packageManager: PackageManager = context.packageManager
                    if (!packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
                        Log.e("TAG", "checkForBiometrics, Fingerprint Sensor not supported")
                        canAuthenticate = false
                    }
                    if (!keyguardManager.isKeyguardSecure) {
                        Log.e(
                            "TAG",
                            "checkForBiometrics, Lock screen security not enabled in Settings"
                        )
                        canAuthenticate = false
                    }
                } else {
                    val biometricManager: BiometricManager =
                        context.getSystemService(BiometricManager::class.java)
                    if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {
                        Log.e("TAG", "checkForBiometrics, biometrics not supported")
                        canAuthenticate = false
                    }
                }
            } else {
                canAuthenticate = false
            }
            Log.e("TAG", "checkForBiometrics ended, canAuthenticate=$canAuthenticate ")
            return canAuthenticate
        }


        @JvmStatic
        fun isSEAvailable(context: Context): Boolean {
            val output = StringBuffer()
            val p: Process
            try {
                p = Runtime.getRuntime().exec("getenforce")
                p.waitFor()
                val reader = BufferedReader(InputStreamReader(p.inputStream))
                var line: String? = ""
                while (reader.readLine().also { line = it } != null) {
                    output.append(line)
                }
            } catch (e: java.lang.Exception) {
                Log.e("TAG", "OS does not support getenforce")
                // If getenforce is not available to the device, assume the device is not enforcing
                e.printStackTrace()
                return false
            }
            val response = output.toString()
            return if ("Enforcing" == response) {
                true
            } else if ("Permissive" == response) {
                false
            } else {
                Log.e(
                    "TAG",
                    "getenforce returned unexpected value, unable to determine selinux!${response}"
                )
                // If getenforce is modified on this device, assume the device is not enforcing
                false
            }
        }

    }


}