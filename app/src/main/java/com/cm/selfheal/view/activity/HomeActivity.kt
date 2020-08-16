package com.cm.selfheal.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import coresecuritylib.util.BasicDetailUtil.Companion.getPhonePreviewSE
import coresecuritylib.util.PhoneSettingDetailUtil
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ActivityHomeBinding
import com.cm.selfheal.viewmodel.DataViewModel
import coresecuritylib.util.BasicDetailUtil.Companion.getPhoneNickName
import coresecuritylib.util.BiometricUtil.biometricInfo
import coresecuritylib.util.WifiDetailUtil
import coresecuritylib.util.WifiDetailUtil.Companion.wifiBSSID
import coresecuritylib.util.WifiDetailUtil.Companion.wifiIPAddress
import coresecuritylib.util.WifiDetailUtil.Companion.wifiMAC
import coresecuritylib.util.WifiDetailUtil.Companion.wifiSSID


class HomeActivity : AppCompatActivity() {
    var dataViewModel: DataViewModel? = null
    lateinit var binding: ActivityHomeBinding
    private val READ_REQUEST_CODE = 101
    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        dataViewModel?.tearDown()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.e("####", "" + getPhonePreviewSE())
        //Log.e("####", "" + biometricInfo(this))

        val view: View = bind()
        initRecyclerView(view)

        dataViewModel?.setUp()
        // setupPermissions()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i("TAG", "Permission has been denied by user")
                } else {
                    Log.i("TAG", "Permission has been granted by user")
                }
            }
        }
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    open fun bind(): View {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        dataViewModel = DataViewModel(this)
        binding.setViewModel(dataViewModel)

        return binding.getRoot()
    }

    open fun initRecyclerView(view: View) {

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        val divider = DividerItemDecoration(this@HomeActivity, DividerItemDecoration.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                this@HomeActivity,
                R.drawable.custom_divider
            )!!
        )
        binding.recyclerView.addItemDecoration(divider)
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_PHONE_STATE),
            READ_REQUEST_CODE
        )
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_PHONE_STATE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                )
            ) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Permission to access the:")
                    .setTitle("Permission required")

                builder.setPositiveButton(
                    "OK"
                ) { dialog, id ->
                    Log.i("TAG", "Clicked")
                    makeRequest()
                }

                val dialog = builder.create()
                dialog.show()

            } else {
                makeRequest()
            }
        }
    }
}
