package com.doschechko.matylionak.wcguide.horoscope;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.doschechko.matylionak.wcguide.CONSTANTS;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.toolbar.ToolBarFragmentActivityViewModel;


public class Activity_HoroscopeViewModel implements BaseFragmentViewModel {
    private FragmentManager manager;
    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void init() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
    }

    @Override
    public void release() {

    }


    public void showItem(int index) {
        Item_Horoscope item_horoscope = new Item_Horoscope();
        Bundle bundle = new Bundle();
        bundle.putInt(CONSTANTS.HOROSCOPE_ITEM, index);
        item_horoscope.setArguments(bundle);
        ToolBarFragmentActivityViewModel.showFragment(manager, item_horoscope,true );//false
    }


    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }


    public class ClickHandler {
        public void onClickButton(View view) {
            switch (view.getId()) {
                case R.id.znak_00:
                    showItem(0);
                    break;
                case R.id.znak_01:
                    showItem(1);
                    break;
                case R.id.znak_02:
                    showItem(2);
                    break;
                case R.id.znak_03:
                    showItem(3);
                    break;
                case R.id.znak_04:
                    showItem(4);
                    break;
                case R.id.znak_05:
                    showItem(5);
                    break;
                case R.id.znak_06:
                    showItem(6);
                    break;
                case R.id.znak_07:
                    showItem(7);
                    break;
                case R.id.znak_08:
                    showItem(8);
                    break;
                case R.id.znak_09:
                    showItem(9);
                    break;
                case R.id.znak_10:
                    showItem(10);
                    break;
                case R.id.znak_11:
                    showItem(11);
                    break;
            }
        }
    }
}
