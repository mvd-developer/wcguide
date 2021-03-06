package com.doschechko.matylionak.wcguide.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.SlidingDrawer;

import com.doschechko.matylionak.wcguide.CONSTANTS;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.about.AboutFragment;
import com.doschechko.matylionak.wcguide.anekdot.Activity_Anekdot;
import com.doschechko.matylionak.wcguide.base.BaseFragmentActivityViewModel;
import com.doschechko.matylionak.wcguide.citaty.Activity_Authors;
import com.doschechko.matylionak.wcguide.horoscope.Activity_Horoscope;
import com.doschechko.matylionak.wcguide.maps.Activity_Item_WC;
import com.doschechko.matylionak.wcguide.maps.Activity_Maps;

import java.lang.annotation.Annotation;

/**
 * ViewModel for the ToolBar
 */

public class ToolBarFragmentActivityViewModel implements BaseFragmentActivityViewModel, BindingAdapter {

    private FragmentManager fragmentManager;
    private Activity activity;

    private SlidingDrawer slidingDrawer;



    public void setSlidingDrawer(SlidingDrawer slidingDrawer) {
        this.slidingDrawer = slidingDrawer;
    }


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public ToolBarFragmentActivityViewModel() {
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void init() {
        SharedPreferences preferences = activity.getSharedPreferences(CONSTANTS.PREFERENCE, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(CONSTANTS.FIRST_ENTER, true ).apply();
        showFragment(fragmentManager,  new Activity_Maps().newInstance(fragmentManager,"Activity_Maps"), false);

    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    public void onClickOpenAbout() {
        //it starts an AboutUsActivity
        showFragment(fragmentManager, AboutFragment.newInstance(fragmentManager, "AboutFragment"), true);//false
        closeDrawer();
    }


    public void onClickOpenQuote() {
        //it starts an Activity_Quote
        showFragment(fragmentManager, Activity_Authors.newInstance(fragmentManager, "Activity_Authors"), true);
        closeDrawer();
    }


    public void onClickOpenHoroscope() {
        //it starts an Activity_Horoscope
        showFragment(fragmentManager, Activity_Horoscope.newInstance(fragmentManager, "Activity_Horoscope"), true);
        closeDrawer();
    }


    public void onClickOpenAnekdot() {
        //it starts an Activity_Anekdot
        showFragment(fragmentManager, Activity_Anekdot.newInstance(fragmentManager, "Activity_Anekdot"), true);
        closeDrawer();
    }


    public void onClickBack() {
        //backclick
        activity.onBackPressed();

    }


    public static void showFragment(FragmentManager fragmentManager, Fragment fragment, boolean addToBackStack) {
        //здесь мы все перенесли в статический метод
        if (fragment.isAdded()) {
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //последний аргумент нужен только для индефикации - уникальный тег.
        fragmentTransaction.replace(R.id.container, fragment, fragment.getClass().getName());
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public  void closeDrawer() {
        //close menu on misclick
        slidingDrawer.animateClose();
    }

    @Override
    public String[] value() {
        return new String[0];
    }

    @Override
    public boolean requireAll() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
