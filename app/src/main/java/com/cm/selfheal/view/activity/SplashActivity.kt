package com.cm.selfheal.view.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.animation.DecelerateInterpolator
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)
    private val permissionRequestCode = 1240
    // List of all permissions for the app
    private var appPermissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setAnimation()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this

        if (checkAndRequestPermissions()) {
            // All permissions are granted already. Proceed ahead
            //   initApp();
            // Start intro animation.
            activityScope.launch {
                delay(2000)
                var intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            val slide = Slide()
            slide.slideEdge = Gravity.LEFT
            slide.duration = 400
            slide.interpolator = DecelerateInterpolator()
            window.exitTransition = slide
            window.enterTransition = slide
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        // Check which permissions are granted
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (perm in appPermissions) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    perm
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("appPermissions","######### $perm")
                listPermissionsNeeded.add(perm)
            }
        }

        // Ask for non-granted permissions
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                permissionRequestCode
            )
            return false
        }

        // App has all permissions. Proceed ahead
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String?>,
        @NonNull grantResults: IntArray
    ) {
        if (requestCode == permissionRequestCode) {
            val permissionResults: HashMap<String?, Int> = HashMap()
            var deniedCount = 0

            // Gather permission grant results
            for (i in grantResults.indices) {
                // Add only permissions which are denied
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults[permissions[i]] = grantResults[i]
                    deniedCount++
                }
            }

            // Check if all permissions are granted
            if (deniedCount == 0) {
                // Proceed ahead with the app
                //   initApp()
                // Start intro animation.
                activityScope.launch {
                    delay(2000)
                    var intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                for ((permName, permResult) in permissionResults.entries) {

                    // permission is denied (this is the first time, when "never ask again" is not checked)
                    // so ask again explaining the usage of permission
                    // shouldShowRequestPermissionRationale will return true
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            permName.toString()
                        )
                    ) {
                        // Show dialog of explanation
                        showDialog(
                            "",
                            "This app needs Location permissions to work without any issues and problems.",
                            "Yes, Grant permissions",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                checkAndRequestPermissions()
                            },
                            "No, Exit app",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                finish()
                            },
                            false
                        )
                    } else {
                        // Ask user to go to settings and manually allow permissions
                        showDialog(
                            "",
                            "You have denied some permissions to the app. Please allow all permissions at [Setting] > [Permissions] screen",
                            "Go to Settings",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                // Go to app settings
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", packageName, null)
                                )
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            },
                            "No, Exit app",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                finish()
                            },
                            false
                        )
                        break
                    }
                }
            }
        }
    }

    private fun showDialog(
        title: String?, msg: String?, positiveLabel: String?,
        positiveOnClick: DialogInterface.OnClickListener?,
        negativeLabel: String?, negativeOnClick: DialogInterface.OnClickListener?,
        isCancelAble: Boolean
    ): AlertDialog? {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setCancelable(isCancelAble)
        builder.setMessage(msg)
        builder.setPositiveButton(positiveLabel, positiveOnClick)
        builder.setNegativeButton(negativeLabel, negativeOnClick)
        val alert: AlertDialog = builder.create()
        alert.show()
        return alert
    }
}