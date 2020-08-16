package com.cm.selfheal.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cm.selfheal.R
import com.cm.selfheal.databinding.ItemDetailThreatBinding
import com.cm.selfheal.model.ThreatDetailModel
import com.cm.selfheal.viewmodel.ThreatDetailItemViewModel

import java.util.*

class ThreatDetailAdapter (context: Context) : RecyclerView.Adapter<ThreatDetailAdapter.DataViewHolder>() {
    private var context:Context = context
    var onItemClick: ((ThreatDetailModel) -> Unit)? = null
    private val data: MutableList<ThreatDetailModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_detail_threat,
            FrameLayout(parent.context), false
        )
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel = data[position]
        holder.setViewModel(ThreatDetailItemViewModel(dataModel))
        holder.binding?.cardView?.setOnClickListener(View.OnClickListener { view ->
           /* var intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("PROTECTION_MODULE",dataModel.protectionTitle)
            intent.putExtra("PROTECTION_MODULE_ID",dataModel.id.toString())
            context.startActivity(intent)*/
//Toast.makeText(Applications.applicationContext(),"CLICKED${coresecuritylib.view.tag}",Toast.LENGTH_SHORT).show()
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onViewAttachedToWindow(holder: DataViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: DataViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind()
    }

    fun updateData(data: List<ThreatDetailModel>?) {
        if (data == null || data.isEmpty()) {
            this.data.clear()
        } else {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }

    /* package */
   inner class DataViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        /* package */
        var binding: ItemDetailThreatBinding? = null

        /* package */
        fun bind() {
            if (binding == null) {
                itemView.setOnClickListener {
                    onItemClick?.invoke(data[adapterPosition])
                }
                binding = DataBindingUtil.bind(itemView)
            }
        }

        /* package */
        fun unbind() {
            if (binding != null) {
                binding!!.unbind() // Don't forget to unbind
            }
        }

        /* package */
        fun setViewModel(viewModel: ThreatDetailItemViewModel?) {
            if (binding != null) {
                binding!!.viewModel = viewModel
            }
        }

        /* package */
        init {
            bind()
        }
    }

    companion object {
        private const val TAG = "DataAdapter"
    }

    init {
        data = ArrayList()
    }
}