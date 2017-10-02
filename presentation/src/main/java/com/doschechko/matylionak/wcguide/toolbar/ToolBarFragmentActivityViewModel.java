package com.doschechko.matylionak.wcguide.toolbar;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.SlidingDrawer;
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
    private boolean permission;

    private SlidingDrawer slidingDrawer;

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

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
        //


        //
        Bundle bundle = new Bundle();
        bundle.putBoolean("permission", permission);
        Activity_Maps activity_Maps = new Activity_Maps().newInstance(fragmentManager,"Activity_Maps");
        activity_Maps.setArguments(bundle);
        showFragment(fragmentManager, activity_Maps , false);
       //тут был  true (нюанс был только в белом экране когда true, при false - при сворачивании не создавало активити)

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
        showFragment(fragmentManager, AboutFragment.newInstance(fragmentManager,"AboutFragment"), true);//false
        slidingDrawer.animateClose();
    }


    public void onClickOpenQuote() {
        //it starts an Activity_Quote
        showFragment(fragmentManager, Activity_Authors.newInstance(fragmentManager,"Activity_Authors"), true);
        slidingDrawer.animateClose();
    }


    public void onClickOpenHoroscope() {
        //it starts an Activity_Horoscope
        showFragment(fragmentManager, Activity_Horoscope.newInstance(fragmentManager,"Activity_Horoscope"), true);
        slidingDrawer.animateClose();
    }


    public void onClickOpenAnekdot() {
        //it starts an Activity_Anekdot
        showFragment(fragmentManager, Activity_Anekdot.newInstance(fragmentManager,"Activity_Anekdot"), true);
        slidingDrawer.animateClose();
    }


    public void onClickBack() {
        //backclick
        activity.onBackPressed();

    }


    public static void showFragment(FragmentManager fragmentManager, Fragment fragment, boolean addToBackStack) {
        //здесь мы все перенесли в статический метод
        if(fragment.isAdded())
        {
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //последний аргумент нужен только для индефикации - уникальный тег.
        fragmentTransaction.replace(R.id.container, fragment, fragment.getClass().getName());
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
