package com.doschechko.matylionak.wcguide.maps;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.base.BaseFragment;
import com.doschechko.matylionak.wcguide.databinding.ActivityItemWcBinding;

import static com.doschechko.matylionak.wcguide.CONSTANTS.TEXT_KEY;


public class Activity_Item_WC extends BaseFragment {

    private String text;
    public Activity_Item_WC() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String wc_id = getArguments().getString("bundle_item_wc");
        Log.e("bundle ", wc_id);
        Activity_Item_WC_ViewModel viewModel = new Activity_Item_WC_ViewModel();
        this.viewModel = viewModel;
        viewModel.setActivity(this);
        viewModel.setWcId(wc_id);
        ActivityItemWcBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.activity_item_wc, container, false);
        binding.setViewModel(viewModel);
        viewModel.setBinding(binding);
        View view = binding.getRoot();

        return view;
    }


    public static Activity_Item_WC newInstance(FragmentManager fragmentManager, String text) {
        Fragment fragment = fragmentManager
                .findFragmentByTag(Activity_Item_WC.class.getName());

        Activity_Item_WC activity_item_wc = null;
        if (fragment != null && fragment instanceof Activity_Item_WC) {
            try {
                activity_item_wc = (Activity_Item_WC) fragment;
            } catch (Exception e) {
                Log.e("FINALIZE", "EXEPTION IN ACTIVITY item WC");
            }

        } else {
            activity_item_wc = new Activity_Item_WC();
            Bundle bundle = new Bundle();
            bundle.putString(TEXT_KEY, text);
            activity_item_wc.setArguments(bundle);
        }
        return activity_item_wc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {

            text = bundle.getString(TEXT_KEY);
        }

    }

}
