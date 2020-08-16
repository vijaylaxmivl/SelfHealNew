package com.cm.selfheal.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.selfheal.R
import com.cm.selfheal.adapter.AppsOverviewAdapter
import com.cm.selfheal.adapter.DeviceOverviewAdapter
import com.cm.selfheal.adapter.NetworkOverviewAdapter
import com.cm.selfheal.adapter.PhishingOverviewAdapter
import com.cm.selfheal.databinding.FragmentOverviewBinding
import com.cm.selfheal.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var deviceOverviewAdapter:DeviceOverviewAdapter
            //= DeviceOverviewAdapter(arrayListOf())
    private lateinit var networkOverviewListAdapter:NetworkOverviewAdapter
            //= NetworkOverviewAdapter(arrayListOf())
    private lateinit var appsOverviewAdapter:AppsOverviewAdapter
    //= AppsOverviewAdapter(arrayListOf())
  private lateinit var phishingOverviewListAdapter :PhishingOverviewAdapter
            //= PhishingOverviewAdapter(arrayListOf())
    private lateinit var binding: FragmentOverviewBinding

    override fun onResume() {
        super.onResume()
        viewModel.dataCollection()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(DetailViewModel::class.java)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        binding.setLifecycleOwner(this)

        viewModel.text.observe(this, Observer<String> {
            // binding.sectionLabel.text = it
        }).apply {
            binding.lifecycleOwner = viewLifecycleOwner
            viewModel =
                ViewModelProviders.of(activity!!)[DetailViewModel::class.java]   // Attach your coresecuritylib.view model here
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        overviewList.apply {
            layoutManager = LinearLayoutManager(context)
            if (viewModel.currentProtectionFlowId.value.toString() == "1") {
                deviceOverviewAdapter=DeviceOverviewAdapter(context,arrayListOf())
                adapter = deviceOverviewAdapter
            } else if (viewModel.currentProtectionFlowId.value.toString() == "2") {
                networkOverviewListAdapter=NetworkOverviewAdapter(arrayListOf())
                adapter = networkOverviewListAdapter
            } else if (viewModel.currentProtectionFlowId.value.toString() == "3") {
                appsOverviewAdapter=AppsOverviewAdapter(arrayListOf())
                adapter = appsOverviewAdapter
            } else if (viewModel.currentProtectionFlowId.value.toString() == "4") {
                phishingOverviewListAdapter=PhishingOverviewAdapter(arrayListOf())
                adapter = phishingOverviewListAdapter
            }

        }

        observerViewModel()

    }

    private fun observerViewModel() {

        viewModel._index.observe(this, Observer { index ->
            index?.let {

                overviewList.visibility = View.VISIBLE
            }
        })

        viewModel.detailModelList.observe(this, Observer { t ->
            binding.recommendation.text = viewModel.recommendations.value
            if (viewModel.currentProtectionFlowId.value.toString() == "1") {
                deviceOverviewAdapter.updateOverviewList(t.get(0).overviewModel.overviewListModel)
            } else if (viewModel.currentProtectionFlowId.value.toString() == "2") {
                networkOverviewListAdapter.updateOverviewList(t.get(0).overviewModel.overviewListModel)
            } else if (viewModel.currentProtectionFlowId.value.toString() == "3") {
                appsOverviewAdapter.updateOverviewList(t.get(0).overviewModel.overviewListModel)
            } else if (viewModel.currentProtectionFlowId.value.toString() == "4") {
                phishingOverviewListAdapter.updateOverviewList(t.get(0).overviewModel.overviewListModel)
            }

        })
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): OverviewFragment {
            return OverviewFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}