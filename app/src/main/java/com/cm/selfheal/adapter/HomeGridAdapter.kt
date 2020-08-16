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
import com.cm.selfheal.adapter.HomeGridAdapter.DataViewHolder
import com.cm.selfheal.databinding.ItemDataBinding
import com.cm.selfheal.model.HomeGridModel
import com.cm.selfheal.view.activity.DetailActivity
import com.cm.selfheal.viewmodel.DataItemViewModel
import java.util.*

class HomeGridAdapter (context: Context) : RecyclerView.Adapter<DataViewHolder>() {
    private var context:Context = context
    var onItemClick: ((HomeGridModel) -> Unit)? = null
    private val data: MutableList<HomeGridModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_data,
            FrameLayout(parent.context), false
        )
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel = data[position]
        holder.setViewModel(DataItemViewModel(dataModel))
        holder.binding?.rLayoutClick?.setOnClickListener(View.OnClickListener { view ->
            var intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("PROTECTION_MODULE",dataModel.protectionTitle)
            intent.putExtra("PROTECTION_MODULE_ID",dataModel.id.toString())
            context.startActivity(intent)
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

    fun updateData(data: List<HomeGridModel>?) {
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
        var binding: ItemDataBinding? = null

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
        fun setViewModel(viewModel: DataItemViewModel?) {
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