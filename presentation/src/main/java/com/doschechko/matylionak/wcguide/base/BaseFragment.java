package com.doschechko.matylionak.wcguide.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {
    protected BaseFragmentViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
        // viewModel.init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.init();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.release();
    }


}
