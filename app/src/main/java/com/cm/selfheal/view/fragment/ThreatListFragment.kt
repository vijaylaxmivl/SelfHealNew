package com.cm.selfheal.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.selfheal.R
import com.cm.selfheal.adapter.ThreatListAdapter
import com.cm.selfheal.applications.Applications

import com.cm.selfheal.databinding.FragmentThreatListBinding
import com.cm.selfheal.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_threat_list.*

class ThreatListFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel

    private val threatListAdapter =
        ThreatListAdapter(arrayListOf())
    private lateinit var binding: FragmentThreatListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(DetailViewModel::class.java).apply {
                /*  setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)*/
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_threat_list, container, false)
        binding.setLifecycleOwner(this)

        binding.threatNo.text = viewModel.threatNo.value
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        threatList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = threatListAdapter

        }

        observerViewModel()
    }


    fun observerViewModel() {
        viewModel._index.observe(this, Observer { index ->
            index?.let {
                threatList.visibility = View.VISIBLE

            }
        })
        viewModel.detailModelList.observe(this, Observer { t ->

            threatListAdapter.updateThreatList(t.get(0).threatListModel)

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
        fun newInstance(sectionNumber: Int): ThreatListFragment {
            return ThreatListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}