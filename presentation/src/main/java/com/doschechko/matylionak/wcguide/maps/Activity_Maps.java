package com.doschechko.matylionak.wcguide.maps;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.base.BaseFragment;
import com.doschechko.matylionak.wcguide.databinding.ActivityMapsBinding;

import static com.doschechko.matylionak.wcguide.CONSTANTS.TEXT_KEY;

public class Activity_Maps extends BaseFragment {

    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Activity_MapsViewModel viewModel = new Activity_MapsViewModel();
        this.viewModel = viewModel;

        // viewModel.setFragmentManager(fragmentManager);
        ActivityMapsBinding binding = DataBindingUtil.inflate(inflater, R.layout.activity_maps, container, false);
        View mView = binding.getRoot();
        binding.setViewModel(viewModel);
        viewModel.setActivity(this);
        //
        viewModel.setManager(getActivity().getSupportFragmentManager());


        return mView;
    }


    public static Activity_Maps newInstance(FragmentManager fragmentManager, String text) {
        Fragment fragment = fragmentManager
                .findFragmentByTag(Activity_Maps.class.getName());

        Activity_Maps activity_maps;
        if (fragment != null && fragment instanceof Activity_Maps) {
            activity_maps = (Activity_Maps) fragment;
        } else {
            activity_maps = new Activity_Maps();
            Bundle bundle = new Bundle();
            bundle.putString(TEXT_KEY, text);
            activity_maps.setArguments(bundle);
        }

        return activity_maps;
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
