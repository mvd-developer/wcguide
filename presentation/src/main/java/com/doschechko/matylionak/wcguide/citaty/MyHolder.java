package com.doschechko.matylionak.wcguide.citaty;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.doschechko.matylionak.wcguide.databinding.ItemQuoteBinding;


public class MyHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener {
    public ItemQuoteBinding binding;
    private ItemClickListener itemClickListener;


    public MyHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        itemView.setOnLongClickListener(this);

    }


    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    @Override
    public boolean onLongClick(View v) {
        itemClickListener.OnClick(v, getAdapterPosition());
        return false;
    }
}
