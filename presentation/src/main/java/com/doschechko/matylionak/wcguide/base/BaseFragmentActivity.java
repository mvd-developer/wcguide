package com.doschechko.matylionak.wcguide.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * The base abstract class for all Activities in this app that use the MVP pattern
 * just extend form this one and and set the ViewModel object
 */

public  abstract class BaseFragmentActivity extends FragmentActivity {

    protected BaseFragmentActivityViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    viewModel.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    viewModel.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    viewModel.release();
    }
}
