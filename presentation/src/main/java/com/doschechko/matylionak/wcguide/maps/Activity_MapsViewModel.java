package com.doschechko.matylionak.wcguide.maps;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;
import com.doschechko.matylionak.data.entity.WcProfileData;
import com.doschechko.matylionak.domain.entity.WcProfileModel;
import com.doschechko.matylionak.wcguide.CONSTANTS;
import com.doschechko.matylionak.wcguide.MyDialog;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.google.android.gms.maps.SupportMapFragment;
import io.realm.Realm;
import io.realm.RealmResults;

public class Activity_MapsViewModel extends MapReady implements
        BaseFragmentViewModel
{

    private Activity_MapsViewModel activity_mapsViewModel = this;
    private FragmentManager manager;
    private SupportMapFragment mapFragment;
    private boolean visibleOnScreen = false;
    private boolean isFirstStart = true;
    private SharedPreferences preferences;

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }
    public void setActivity(Activity_Maps activity) {
        this.activity = activity;
    }



    @Override
    public void init() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<WcProfileData> data = realm.where(WcProfileData.class).findAll();
        for (WcProfileData p: data){
            WcProfileModel model = new WcProfileModel();
            model.setAddress(p.getAddress());
            model.setCoordinats(p.getCoordinats());
            model.setCost(p.getCost());
            model.setImage(p.getImage());
            model.setObjectId(p.getObjectId());
            model.setWork_time(p.getWork_time());
            listWc.add(model);
        }
        realm.close();

        //проверям наличие наших настроек
        preferences = activity.getActivity()
                .getSharedPreferences(CONSTANTS.PREFERENCE, Context.MODE_PRIVATE);
        int flag = preferences.getInt(CONSTANTS.SHOW_AGAIN, 0);
        getPermission = preferences.getBoolean(CONSTANTS.KEY_PERMISSION, true);
        isFirstStart = preferences.getBoolean(CONSTANTS.FIRST_ENTER, true);
        if (flag == 0) {
            //показываем легенду карты
            MyDialog dialogFragment = new MyDialog();
            dialogFragment.show(manager, "string");
            //
        }
        visibleOnScreen = true;
        if (isFirstStart) {
            if (visibleOnScreen) {
                Toast.makeText(activity.getContext(),
                        R.string.loading_data,
                        Toast.LENGTH_SHORT).show();
            }

            //проверка на состояние активити
            if (visibleOnScreen) {
                mapFragment = (SupportMapFragment) activity.getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(activity_mapsViewModel);
                mapView = mapFragment.getView();
                Toast.makeText(activity.getContext(),
                        R.string.mapLoaded,
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        visibleOnScreen = false;
        if (locationListener != null && locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        preferences.edit().putBoolean(CONSTANTS.FIRST_ENTER, true ).apply();

    }

    @Override
    public void release() {
        preferences.edit().putBoolean(CONSTANTS.FIRST_ENTER, false ).apply();

    }

}