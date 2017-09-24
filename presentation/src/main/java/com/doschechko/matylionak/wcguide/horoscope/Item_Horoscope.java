package com.doschechko.matylionak.wcguide.horoscope;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.doschechko.matylionak.wcguide.CONSTANTS;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.base.BaseFragment;
import com.doschechko.matylionak.wcguide.databinding.ItemHoroscopeBinding;


public class Item_Horoscope extends BaseFragment {
    private int index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt(CONSTANTS.HOROSCOPE_ITEM);
        }

        Item_HoroscopeViewModel viewModel = new Item_HoroscopeViewModel(index);
        this.viewModel = viewModel;
        ItemHoroscopeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.item_horoscope, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);
        viewModel.setActivity(getActivity());

        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
