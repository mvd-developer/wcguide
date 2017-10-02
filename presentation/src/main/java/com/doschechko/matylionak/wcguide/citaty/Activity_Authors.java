package com.doschechko.matylionak.wcguide.citaty;

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
import com.doschechko.matylionak.wcguide.databinding.ActivityAllAuthorsBinding;

import static com.doschechko.matylionak.wcguide.CONSTANTS.TEXT_KEY;

/**
 * Фрагмент со всеми авторами, цитатами которых мы располагаем
 */

public class Activity_Authors extends BaseFragment {

    public static String AUTHOR_NAME = "AUTHOR_NAME";
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Activity_AuthorsViewModel viewModel = new Activity_AuthorsViewModel();
        this.viewModel = viewModel;

        ActivityAllAuthorsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.activity_all_authors, container, false);
        View view = binding.getRoot();


        //передаем необходимые переменные во вью модель
        viewModel.setActivity(getActivity());
        viewModel.setBinding(binding);


        //нам нужен фрагмент менеджер
        FragmentManager manager = getFragmentManager();
        viewModel.setManager(manager);
        binding.setViewModel(viewModel);


        return view;
    }

    public static Activity_Authors newInstance(FragmentManager fragmentManager, String text) {
        Fragment fragment = fragmentManager
                .findFragmentByTag(Activity_Authors.class.getName());

        Activity_Authors activity_authors;
        if (fragment != null && fragment instanceof Activity_Authors) {
            activity_authors = (Activity_Authors) fragment;
        } else {
            activity_authors = new Activity_Authors();
            Bundle bundle = new Bundle();
            bundle.putString(TEXT_KEY, text);
            activity_authors.setArguments(bundle);
        }

        return activity_authors;
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
