package com.doschechko.matylionak.data;

import android.util.Log;

import com.doschechko.matylionak.data.entity.WcProfileData;
import com.doschechko.matylionak.data.net.Rest.RestService;
import com.doschechko.matylionak.data.net.RealMServiceData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;


public class RepositoryFactory {
    private static final RepositoryFactory instance = new RepositoryFactory();
    private RealMServiceData realmService;
    private RestService rest;
    private BehaviorSubject<Boolean> subject = BehaviorSubject.createDefault(false);

    private RepositoryFactory() {
        Log.e(">>>STILL", "RepositoryFactory = " + Thread.currentThread().getName());
        rest = RestService.getInstance();
        realmService = RealMServiceData.getInstance();
    }

    public static RepositoryFactory getInstance() {
        return instance;
    }

    public Observable<Boolean> startLoading() {
        if (realmService.isCached() && !realmService.isExpired()) {
            Log.e(">>>STILL", "ЗАГРУЗКА ИЗ РЕАЛЭМА");
            subject.onNext(true);
        } else {
            return   rest.getWC().map(new Function<List<WcProfileData>, Boolean>() {
                @Override
                public Boolean apply(List<WcProfileData> wcProfileData) throws Exception {
                    return realmService.loadData(wcProfileData);
                }
            });

        }

        return subject;
    }



}




