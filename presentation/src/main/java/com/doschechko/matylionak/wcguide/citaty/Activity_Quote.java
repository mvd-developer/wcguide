package com.doschechko.matylionak.wcguide.citaty;

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
import com.doschechko.matylionak.wcguide.databinding.ActivityQuoteBinding;

import static com.doschechko.matylionak.wcguide.CONSTANTS.TEXT_KEY;


public class Activity_Quote extends BaseFragment {
private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Activity_QuoteViewModel viewModel = new Activity_QuoteViewModel();
        this.viewModel = viewModel;
        viewModel.setActivity(getActivity());


        FragmentManager manager =getActivity().getSupportFragmentManager();
        ActivityQuoteBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.activity_quote, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);
        binding.recycleViewQuotes.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycleViewQuotes.setAdapter(viewModel.getAdaptor());
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



    public static Activity_Quote newInstance(FragmentManager fragmentManager, String text) {
        Fragment fragment = fragmentManager
                .findFragmentByTag(Activity_Quote.class.getName());

        Activity_Quote activity_quote;
        if (fragment != null && fragment instanceof Activity_Quote) {
            activity_quote = (Activity_Quote) fragment;
        } else {
            activity_quote = new Activity_Quote();
            Bundle bundle = new Bundle();
            bundle.putString(TEXT_KEY, text);
            activity_quote.setArguments(bundle);
        }

        return activity_quote;
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
