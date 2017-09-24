package com.doschechko.matylionak.wcguide.anekdot;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.doschechko.matylionak.wcguide.databinding.ItemAnekdotBinding;



public class MyHolder extends RecyclerView.ViewHolder {

    public ItemAnekdotBinding binding;

    public MyHolder(View itemView) {
        super(itemView);

        binding = DataBindingUtil.bind(itemView);


    }
}
