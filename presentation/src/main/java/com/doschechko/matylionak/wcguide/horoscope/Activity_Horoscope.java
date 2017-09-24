package com.doschechko.matylionak.wcguide.horoscope;

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
import com.doschechko.matylionak.wcguide.citaty.Activity_Authors;
import com.doschechko.matylionak.wcguide.databinding.ActivityHoroscopeBinding;

import static com.doschechko.matylionak.wcguide.CONSTANTS.TEXT_KEY;


public class Activity_Horoscope extends BaseFragment {
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Activity_HoroscopeViewModel viewModel = new Activity_HoroscopeViewModel();
        this.viewModel = viewModel;
        FragmentManager manager = getFragmentManager();
        viewModel.setManager(manager);
        viewModel.setActivity(getActivity());

        ActivityHoroscopeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.activity_horoscope, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);
        Activity_HoroscopeViewModel.ClickHandler buttonHandler = viewModel.new ClickHandler();
        binding.setButtonHandler(buttonHandler);


        return view;
    }


    public static Activity_Horoscope newInstance(FragmentManager fragmentManager, String text) {
        Fragment fragment = fragmentManager
                .findFragmentByTag(Activity_Horoscope.class.getName());

        Activity_Horoscope activity_horoscope;
        if (fragment != null && fragment instanceof Activity_Horoscope) {
            activity_horoscope = (Activity_Horoscope) fragment;
        } else {
            activity_horoscope = new Activity_Horoscope();
            Bundle bundle = new Bundle();
            bundle.putString(TEXT_KEY, text);
            activity_horoscope.setArguments(bundle);
        }

        return activity_horoscope;
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
