package com.cm.selfheal.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ActivityThreatDetailBinding
import com.cm.selfheal.viewmodel.ThreatDetailViewModel

class ThreatDetailActivity : AppCompatActivity() {
    var threatDetailViewModel: ThreatDetailViewModel? = null
    lateinit var binding: ActivityThreatDetailBinding
    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        threatDetailViewModel?.tearDown()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //actionbar

        val view: View = bind()
        initRecyclerView(view)
        threatDetailViewModel?.setUp()
        getIntentData()
    }

    open fun bind(): View {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_threat_detail)
        threatDetailViewModel = ThreatDetailViewModel(this)
        binding.viewModel = threatDetailViewModel

        return binding.root
    }

    open fun initRecyclerView(view: View) {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val divider =
            DividerItemDecoration(this@ThreatDetailActivity, DividerItemDecoration.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                this@ThreatDetailActivity,
                R.drawable.custom_divider
            )!!
        )
        binding.recyclerView.addItemDecoration(divider)
    }

    private fun getIntentData(): String? {
        var bundle: Bundle? = intent.extras
        var valueStr = bundle?.getString("THREAT_ID") // 1

        threatDetailViewModel?.threatName?.value = bundle?.getString("THREAT_NAME")
        //  binding.title.text=threatDetailViewModel?.threatName?.value
        val actionbar = supportActionBar
        actionbar!!.title = "Threat Detail"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        return valueStr
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}