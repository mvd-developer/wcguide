package com.doschechko.matylionak.wcguide.about;


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
import com.doschechko.matylionak.wcguide.databinding.ActivityAboutBinding;

import static com.doschechko.matylionak.wcguide.CONSTANTS.TEXT_KEY;


public class AboutFragment extends BaseFragment {
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AboutFragmentViewModel viewModel = new AboutFragmentViewModel();
        this.viewModel = viewModel;

        ActivityAboutBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.activity_about, container, false);
        binding.setViewModel(viewModel);
        View view = binding.getRoot();
        viewModel.setBinding(binding);
        viewModel.setActivity(getActivity());
        return view;
    }


    public static AboutFragment newInstance(FragmentManager fragmentManager, String text) {
        Fragment fragment = fragmentManager
                .findFragmentByTag(AboutFragment.class.getName());

        AboutFragment aboutFragment;
        if (fragment != null && fragment instanceof AboutFragment) {
            aboutFragment = (AboutFragment) fragment;
        } else {
            aboutFragment = new AboutFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TEXT_KEY, text);
            aboutFragment.setArguments(bundle);
        }

        return aboutFragment;
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