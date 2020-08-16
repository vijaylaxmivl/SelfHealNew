package coresecuritylib.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.util.Log
import java.math.BigInteger
import java.net.InetAddress

class WifiDetailUtil(appContext: Context) {

    companion object {
        val TAG: String = "WifiDetailUtil"
        lateinit var wifiManager: WifiManager
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }

        fun wifiDetail() {
            //scan wifi
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            wifiManager = context.applicationContext.getSystemService(
                Context.WIFI_SERVICE
            ) as WifiManager
            var networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    Log.e(TAG, "onLost: networkcallback called from onLost")
                    //record wi-fi disconnect event
                }

                override fun onUnavailable() {
                    Log.e(TAG, "onUnavailable: networkcallback called from onUnvailable")
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    Log.e(TAG, "onLosing: networkcallback called from onLosing")
                }

                override fun onAvailable(network: Network) {
                    Log.e(TAG, "onAvailable: NetworkCallback network called from onAvailable ")
                    //record wi-fi connect event
                    wifiManager = context.applicationContext.getSystemService(
                        Context.WIFI_SERVICE
                    ) as WifiManager

                  /*  Log.e(TAG, "wifiSSID: " + wifiManager.wifiSSID())
                    Log.e(TAG, "wifiBSSID: " + wifiManager.wifiBSSID())
                    Log.e(TAG, "wifiMAC: " + wifiManager.wifiMAC())
                    Log.e(TAG, "wifiIPAddress: " + wifiManager.wifiIPAddress())*/

                    /*
                    * SSID (b) BSSID (c) MAC (d) IP Addr of Wifi Hotspot*/
                }
            }
            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
            try { //To be safe and efficient the network callback is unregistered before re-registering
                connectivityManager.unregisterNetworkCallback(networkCallback)
            } catch (e: Exception) {
                Log.e(
                    "TAG",
                    " NetworkCallback for Wi-fi was not registered or already unregistered"
                )
            }
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        }

        fun WifiManager.wifiSSID(): String = connectionInfo.ssid.run {
            if (this.contains("<unknown ssid>")) "UNKNOWN"
            else this
        }

        fun WifiManager.wifiBSSID(): String = connectionInfo.bssid.run {
            if (this.contains("<unknown bssid>")) "UNKNOWN"
            else this
        }

        fun WifiManager.wifiMAC(): String = connectionInfo.macAddress.run {
            if (this.contains("<unknown macAddress>")) "UNKNOWN"
            else this
        }

        fun WifiManager.wifiIPAddress(): String = connectionInfo.ipAddress.run {

            val ipAddress: ByteArray = BigInteger.valueOf(this.toLong()).toByteArray()
            val myaddr: InetAddress = InetAddress.getByAddress(ipAddress)
            val hostaddr: String = myaddr.getHostAddress()
            return hostaddr
        }
    }


}