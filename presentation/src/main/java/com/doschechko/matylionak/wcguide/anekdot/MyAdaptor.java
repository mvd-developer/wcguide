package com.doschechko.matylionak.wcguide.anekdot;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doschechko.matylionak.data.entity.AnekdotData;
import com.doschechko.matylionak.domain.entity.Anekdot;
import com.doschechko.matylionak.wcguide.databinding.ItemAnekdotBinding;

import java.util.ArrayList;
import java.util.List;

 class MyAdaptor extends RecyclerView.Adapter<MyHolder> {
    private ArrayList<Anekdot> arrayList = new ArrayList<>();

     void setList(List<Anekdot> list) {
        arrayList.clear();
        arrayList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ItemAnekdotBinding binding = ItemAnekdotBinding.inflate(inflater, parent, false);
        return new MyHolder(binding.getRoot());



    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        Item_AnekdotViewModel model = new Item_AnekdotViewModel();
        model.setBody(arrayList.get(position).getBody());
        holder.binding.setViewModel(model);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
