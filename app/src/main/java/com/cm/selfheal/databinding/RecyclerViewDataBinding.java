package com.cm.selfheal.databinding;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.selfheal.adapter.HomeGridAdapter;
import com.cm.selfheal.adapter.ThreatDetailAdapter;
import com.cm.selfheal.model.HomeGridModel;
import com.cm.selfheal.model.ThreatDetailModel;

import java.util.List;

public class RecyclerViewDataBinding {
    @BindingAdapter({"app:adapter", "app:data"})
    public void bind(RecyclerView recyclerView, HomeGridAdapter adapter, List<HomeGridModel> data) {
        recyclerView.setAdapter(adapter);
        adapter.updateData(data);
    }
    @BindingAdapter({"app:adapter", "app:data"})
    public void bind(RecyclerView recyclerView, ThreatDetailAdapter adapter, List<ThreatDetailModel> data) {
        recyclerView.setAdapter(adapter);
        adapter.updateData(data);
    }

}
