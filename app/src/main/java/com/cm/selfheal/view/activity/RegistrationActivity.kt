package com.cm.selfheal.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ActivityRegistrationBinding
import com.cm.selfheal.viewmodel.RegistrationViewModel


class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    val viewModel: RegistrationViewModel by lazy {
        ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.formValidMediator.observe(this, Observer { validationResult ->
            binding.btnSignIn.isEnabled = validationResult
            if (validationResult) {
                binding.btnSignIn.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.colorPrimary
                    )
                )
            } else {
                binding.btnSignIn.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.light_grey_color
                    )
                )
            }
        })
    }


    fun btnSignInClick(view: View) {
        val intent = Intent(this, SplashActivity::class.java)
        if (Build.VERSION.SDK_INT > 20) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
        finish()
    }


}