package com.doschechko.matylionak.wcguide.citaty;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.doschechko.matylionak.wcguide.databinding.ItemAuthorQuoteBinding;

public class MyGridHolder extends RecyclerView.ViewHolder {

    public ItemAuthorQuoteBinding binding;

    public MyGridHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);

    }
}
