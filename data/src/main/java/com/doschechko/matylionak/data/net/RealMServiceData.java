package com.doschechko.matylionak.data.net;


import android.util.Log;

import com.doschechko.matylionak.data.entity.WcProfileData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Umbra on 21.10.2017.
 * Локальная база данных RealM, SINGLETON
 */

public class RealMServiceData implements Repository {
    private static final RealMServiceData instance = new RealMServiceData();
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;


    private RealMServiceData() {
        Log.e(">>>STILL", "RealMServiceData.create = " + Thread.currentThread().getName());
    }


    public static RealMServiceData getInstance() {
        return instance;
    }


    @Override
    public Observable<List<WcProfileData>> getWC() {
        Log.e(">>>STILL", "RealMServiceData.getWC =" + Thread.currentThread().getName());

        Realm realm = Realm.getDefaultInstance();
        RealmResults<WcProfileData> profileData = realm.where(WcProfileData.class).findAll();
        Log.e(">>>STILL", "RealMServiceData.getWC ="  + Thread.currentThread().getName());

        ArrayList<WcProfileData> allList = new ArrayList<>();
        for (int i = 0; i<profileData.size(); i++){
            WcProfileData data = new WcProfileData();
            data.setAddress(profileData.get(i).getAddress());
            data.setCoordinats(profileData.get(i).getCoordinats());
            data.setCost(profileData.get(i).getCost());
            data.setImage(profileData.get(i).getImage());
            data.setLastUpdated(profileData.get(i).getLastUpdated());
            data.setObjectId(profileData.get(i).getObjectId());
            data.setWork_time(profileData.get(i).getWork_time());
            allList.add(data);
        }
        if (!realm.isClosed()) {
            Log.e(">>>STILL", "RealMServiceData.getWC = ХАКРЫТ РИАЛЭМ  " + Thread.currentThread().getName());
            realm.close();
        }


        Log.e(">>>STILL", "RealMServiceData.getWC = ИЗ РЕАЛЭМА ПРИГШЛО  ");
        Log.e(">>>STILL", "RealMServiceData.getWC = ЗАКОПИРОВАНЫ ДАННЫЕ  " + Thread.currentThread().getName());


//        Observable<List<WcProfileData>> observable = realm.where(WcProfileData.class)
//                .isNotNull("objectId").findAllAsync().asFlowable()
//                .filter(RealmResults::isLoaded)
//                .map(wcProfileDatas -> {
//                    List<WcProfileData> list1 = new ArrayList<>();
//                    list1.addAll(wcProfileDatas);
//                    Log.e(">>>STILL", "RealMServiceData.getWC.map =" + Thread.currentThread().getName() + list1.toString());
//                    return list1;
//                }).toObservable();

        return Observable.just(allList);

    }



    public boolean loadData(List<WcProfileData> list) {
        Realm realm = Realm.getDefaultInstance();
        Log.e(">>>STILL", "RealMServiceData.loadData ЗАПИСЬ В БАЗУ ДАННЫХ  РЕАЛЭМ = " + Thread.currentThread().getName());
        SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
        Date date = new Date();
        Log.e(">>>STILL", "RealMServiceData.loadData ЦИКЛ ЗАПИСИ В БАЗУ ");
        Log.e(">>>STILL", "RealMServiceData.loadData list = " + list.size());
        for (WcProfileData profileData : list) {

            realm.beginTransaction();
            WcProfileData entity = realm.createObject(WcProfileData.class);
            entity.setAddress(profileData.getAddress());
            entity.setCoordinats(profileData.getCoordinats());
            entity.setCost(profileData.getCost());
            entity.setImage(profileData.getImage());
            entity.setObjectId(profileData.getObjectId());
            entity.setWork_time(profileData.getWork_time());
            entity.setLastUpdated(ISO8601DATEFORMAT.format(date));
            Log.e(">>>STILL", "RealMServiceData.loadData  ЗАПИСАН В БАЗУ = " + profileData.toString());
            realm.commitTransaction();
        }
        realm.close();
        return true;
    }




    public boolean isExpired() {
        Realm realm = Realm.getDefaultInstance();
        Log.e(">>>STILL", "РЕАЛЭМ ПРОВЕРКА НА ПРОСРОЧКУ");
        //Realm realm = Realm.getDefaultInstance();
        if (realm.where(WcProfileData.class).count() != 0) {
            Date currentTime = new Date(System.currentTimeMillis());
            SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
            Date lastUpdated = null;
            try {
                lastUpdated = ISO8601DATEFORMAT.parse(realm.where(WcProfileData.class).findFirst().getLastUpdated());
                boolean isExpired = currentTime.getTime() - lastUpdated.getTime() > EXPIRATION_TIME;
                if (isExpired) {
                    realm.beginTransaction();
                    realm.delete(WcProfileData.class);
                    realm.commitTransaction();
                    realm.close();
                }
                realm.close();
                return isExpired;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        realm.close();
        return false;
    }


    public boolean isCached() {
        Log.e(">>>STILL", "РЕАЛЭМ ПРОВЕРКА НА КЭШИРОВАНИЕ");
        Realm realm = Realm.getDefaultInstance();
        boolean flag = realm.where(WcProfileData.class).findAll() != null && realm.where(WcProfileData.class).findAll().size() > 0;
        realm.close();
        Log.e(">>>STILL", "РЕАЛЭМ ПРОВЕРКА НА КЭШИРОВАНИЕ, результат =" + flag);
        return flag;
    }


}
