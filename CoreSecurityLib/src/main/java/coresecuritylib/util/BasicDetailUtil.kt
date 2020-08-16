package coresecuritylib.util
import android.content.Context
import android.os.Build
import android.provider.Settings


class BasicDetailUtil {
    companion object {
        @JvmStatic
        fun getPhoneDeviceName(): String {
            return Build.MODEL
        }

        @JvmStatic
        fun getPhoneManufacturer(): String {
            return Build.MANUFACTURER
        }

        @JvmStatic
        fun getPhoneProduct(): String {
            return Build.PRODUCT
        }

        @JvmStatic
        fun getPhoneDeviceBrand(): String {
            return Build.BRAND
        }

        @JvmStatic
        fun getPhoneDisplay(): String {
            return Build.DISPLAY
        }

        @JvmStatic
        fun getPhoneDevice(): String {
            return Build.DEVICE
        }

        @JvmStatic
        fun getPhoneDeviceId(): String {
            return Build.ID
        }

        @JvmStatic
        fun getPhoneUser(): String {
            return Build.USER
        }

        @JvmStatic
        fun getPhoneVersion(): String {
            return Build.VERSION.RELEASE.toString()
        }

        @JvmStatic
        fun getPhoneBaseOs(): String {
            return Build.VERSION.BASE_OS.toString()
        }

        @JvmStatic
        fun getPhoneCodeName(): String {
            return Build.VERSION.CODENAME.toString()
        }

        @JvmStatic
        fun getPhoneIncremental(): String {
            return Build.VERSION.INCREMENTAL.toString()
        }

        @JvmStatic
        fun getPhoneSecurityPatch(): String {
            return Build.VERSION.SECURITY_PATCH.toString()
        }

        @JvmStatic
        fun getPhonePreviewSdkInt(): String {
            return Build.VERSION.PREVIEW_SDK_INT.toString()
        }
        @JvmStatic
        fun getPhoneTime(context:Context): String {

            return   return Build.TIME.toString()
        }

        @JvmStatic
        fun getPhonePreviewSE(): String {
            return "SERIAL: " + Build.SERIAL + "\n" +
                    "MODEL: " + Build.MODEL + "\n" +
                    "ID: " + Build.ID + "\n" +
                    "Manufacture: " + Build.MANUFACTURER + "\n" +
                    "Brand: " + Build.BRAND + "\n" +
                    "Type: " + Build.TYPE + "\n" +
                    "User: " + Build.USER + "\n" +
                    "BASE: " + Build.VERSION_CODES.BASE + "\n" +
                    "INCREMENTAL: " + Build.VERSION.INCREMENTAL + "\n" +
                    "SDK:  " + Build.VERSION.SDK + "\n" +
                    "BOARD: " + Build.BOARD + "\n" +
                    "BRAND: " + Build.BRAND + "\n" +
                    "HOST: " + Build.HOST + "\n" +
                    "FINGERPRINT: " + Build.FINGERPRINT + "\n" +
                    "Version Code: " + Build.VERSION.RELEASE + "\n" +
                    "Security Patch: " + Build.VERSION.SECURITY_PATCH + "\n" +
                    "Code Name: " + Build.VERSION.CODENAME + "\n" +
                    "Preview SDK: " + Build.VERSION.PREVIEW_SDK_INT + "\n" +
                    "Base Os: " + Build.VERSION.BASE_OS + "\n" +
                    "Device: " + Build.DEVICE + "\n" +
                    "Product : " + Build.PRODUCT + "\n" +
                    "Time : " + Build.TIME

        }

        @JvmStatic
        fun getPhonePreviewSE_(): String {
            return """
                VERSION.RELEASE : ${Build.VERSION.RELEASE}
                VERSION.INCREMENTAL : ${Build.VERSION.INCREMENTAL}
                VERSION.SDK.NUMBER : ${Build.VERSION.SDK_INT}
                BOARD : ${Build.BOARD}
                BOOTLOADER : ${Build.BOOTLOADER}
                BRAND : ${Build.BRAND}
                CPU_ABI : ${Build.CPU_ABI}
                CPU_ABI2 : ${Build.CPU_ABI2}
                DISPLAY : ${Build.DISPLAY}
                FINGERPRINT : ${Build.FINGERPRINT}
                HARDWARE : ${Build.HARDWARE}
                HOST : ${Build.HOST}
                ID : ${Build.ID}
                MANUFACTURER : ${Build.MANUFACTURER}
                MODEL : ${Build.MODEL}
                PRODUCT : ${Build.PRODUCT}
                SERIAL : ${Build.SERIAL}
                TAGS : ${Build.TAGS}
                TIME : ${Build.TIME}
                TYPE : ${Build.TYPE}
                UNKNOWN : ${Build.UNKNOWN}
                USER : ${Build.USER}
                """.trimIndent()
        }

        @JvmStatic
        fun getPhoneNickName(context: Context): String {
           return ""
        }
    }

}