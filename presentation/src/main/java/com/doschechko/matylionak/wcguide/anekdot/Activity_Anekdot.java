package com.doschechko.matylionak.wcguide.anekdot;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.base.BaseFragment;
import com.doschechko.matylionak.wcguide.databinding.ActivityAnekdotBinding;

import static com.doschechko.matylionak.wcguide.CONSTANTS.TEXT_KEY;

public class Activity_Anekdot extends BaseFragment {
    private String text;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            text = bundle.getString(TEXT_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Activity_AnekdotViewModel viewModel = new Activity_AnekdotViewModel();
        this.viewModel = viewModel;
        viewModel.setActivity(getActivity());

        ActivityAnekdotBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.activity_anekdot, container, false);
        View view = binding.getRoot();

        binding.setViewModel(viewModel);
        binding.recycleViewAndekdot.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycleViewAndekdot.setAdapter(viewModel.adaptor);
        viewModel.setBinding(binding);

        return view;
    }

    public static Activity_Anekdot newInstance(FragmentManager fragmentManager, String text) {
        Fragment fragment = fragmentManager
                .findFragmentByTag(Activity_Anekdot.class.getName());

        Activity_Anekdot activity_anekdot;
        if (fragment != null && fragment instanceof Activity_Anekdot) {
            activity_anekdot = (Activity_Anekdot) fragment;
        } else {
            activity_anekdot = new Activity_Anekdot();
            Bundle bundle = new Bundle();
            bundle.putString(TEXT_KEY, text);
            activity_anekdot.setArguments(bundle);
        }

        return activity_anekdot;
    }




}
