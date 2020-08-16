package com.cm.selfheal.view.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cm.selfheal.R
import com.cm.selfheal.adapter.SectionsPagerAdapter
import com.cm.selfheal.databinding.ActivityDetailBinding
import com.cm.selfheal.viewmodel.DetailViewModel
import coresecuritylib.util.BasicDetailUtil

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    val viewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        getIntentData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.currentProtectionFlow.value = getIntentData()

        //actionbar
        val actionbar = supportActionBar
        actionbar!!.title = viewModel.currentProtectionFlow.value.toString()
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        viewModel.currentProtectionFlowFlag.observe(this, Observer {
            Log.e("####", "" + viewModel.currentProtectionFlow.value.toString())
            binding.title.text = viewModel.currentProtectionFlow.value.toString()
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager
        )

        binding.viewPager.adapter = sectionsPagerAdapter

        binding.tabs.setupWithViewPager(binding.viewPager)

        viewModel.setIndex(0)
        binding.viewPager.currentItem = 0
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                viewModel.setIndex(position)
            }

        })

    }

    private fun getIntentData(): String? {
        var bundle: Bundle? = intent.extras
        var valueStr = bundle?.getString("PROTECTION_MODULE") // 1

        viewModel.currentProtectionFlowId.value = bundle?.getString("PROTECTION_MODULE_ID")

        return valueStr
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}