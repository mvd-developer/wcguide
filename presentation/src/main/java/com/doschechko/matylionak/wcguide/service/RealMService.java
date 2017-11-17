package com.doschechko.matylionak.wcguide.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.doschechko.matylionak.domain.entity.WcProfileModel;
import com.doschechko.matylionak.domain.interaction.UseCaseGetListWCOnOtherThread;
import com.doschechko.matylionak.domain.interaction.UseCaseGetNumberOfWC;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.realm.Realm;

import static com.doschechko.matylionak.wcguide.CONSTANTS.END_SERVICE;
import static com.doschechko.matylionak.wcguide.CONSTANTS.PROGRESS_FINISH;
import static com.doschechko.matylionak.wcguide.CONSTANTS.PROGRESS_INT;


public class RealMService extends Service {
    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("FFF", "СЕРВИС = onStartCommand");

        new Thread(new Runnable() {
            UseCaseGetListWCOnOtherThread listWC;
            Realm data;
            int storagesWC;
            LocalBroadcastManager lbm;

            @Override
            public void run() {
                listWC = new UseCaseGetListWCOnOtherThread();
                data = Realm.getDefaultInstance();
                lbm = LocalBroadcastManager.getInstance(getApplication());

                //--------------------------------------------
                if (data.isEmpty()) {
                    Log.e("FFF", "БД ПУСТАЯ");
                    //стартуем загрузку
                    loadData();
                } else {
                    Log.e("FFF", "В БД ЧТО-ТО ЕСТЬ");
                    //получаем количество толканов на сервере

                    getNumber();
                    Log.e("FFF", "КОЛИЧЕСТВО ТУАЛЕТОВ НА БЕКЭНДЛЕССЕ = " + storagesWC);
                    Log.e("FFF", "КОЛИЧЕСТВО ТУАЛЕТОВ В REALM = " + data.where(WCServiceEntity.class).findAll().size());
                    if (storagesWC != data.where(WCServiceEntity.class).findAll().size()) {
                        Log.e("FFF", "Количество на сервере не равно количеству в БД");
                        //старт загрузки
                        loadData();
                    } else {
                        Log.e("FFF", "REALM ЗАКРЫВАЕМ");
                        data.close();
                        Log.e("FFF", "ОСТАНАВЛИВАЕМ СЕРВИС");
                        stopSelf();
                    }


                }


            }

            void loadData() {
                if (!data.isEmpty()) {
                    Log.e("FFF", "БАЗА ДАННЫХ НЕ ПУСТАЯ - ОЧИЩАЕМ");
                    Log.e("FFF", "NAME OF THREAD = " + Thread.currentThread().getName());
                    data.beginTransaction();
                    data.deleteAll();
                    data.commitTransaction();
                    Log.e("FFF", "БАЗА ДАННЫХ ОЧИЩЕНА");
                }

                listWC.execute(null, new DisposableObserver<List<WcProfileModel>>() {
                    @Override
                    public void onNext(@NonNull List<WcProfileModel> wcProfileModels) {
                        storagesWC = wcProfileModels.size();
                        int counter = 0;
                        for (WcProfileModel a : wcProfileModels) {
                            Log.e("FFF", "NAME OF THREAD = " + Thread.currentThread().getName());
                            Log.e("FFF", "ПОЛУЧЕН ТОЛКАН = " + a.toString());
                            data.beginTransaction();
                            WCServiceEntity entity = data.createObject(WCServiceEntity.class);
                            Log.e("FFF", "ТОЛКАН ЗАПИСЫВАЕТСЯ В БАЗУ = " + a.toString());
                            entity.setAddress(a.getAddress());
                            entity.setCoordinats(a.getCoordinats());
                            entity.setCost(a.getCost());
                            entity.setImage(a.getImage());
                            entity.setObjectId(a.getObjectId());
                            entity.setWork_time(a.getWork_time());
                            data.commitTransaction();
                            Log.e("FFF", "ТОЛКАН ЗАПИСАН");
                            ++counter;
                            sendNumberOfDownloadedWC(counter);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("FFF", "ТОЛКАНОВ НА БАЗЕ = " + data.where(WCServiceEntity.class).findAll().size());
                        data.close();
                        Log.e("FFF", "REALM закрыт");
                        Log.e("FFF", "ОСТАНАВЛИВАЕМ СЕРВИС");
                        stopSelf();
                    }
                });


            }

            //--------
            private void sendNumberOfDownloadedWC(int downloaded) {
                Intent intent = new Intent(END_SERVICE);
                float number = (float) downloaded / storagesWC * 100f;
                int number2 = (int) number;
                intent.putExtra(PROGRESS_INT, number2);
                lbm.sendBroadcast(intent);
                Log.e("FFF", "storagesWC = " + storagesWC);
                Log.e("FFF", "downloaded = " + downloaded);
                Log.e("FFF", "sendNumberOfDownloadedWC = " + number2);
            }
            //--------


            //*****

            public void getNumber() {
                UseCaseGetNumberOfWC wc = new UseCaseGetNumberOfWC();
                wc.execute(null, new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(@NonNull Integer integer) {
                        storagesWC = integer;
                        Log.e("FFF", "Количество туалетов на сервере = " + storagesWC);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }


            ///*****


        }).start();

        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.e("FFF", "СЕРВИС ЗАКРЫТ");
        sendMessage();
        super.onDestroy();
    }

    //отправляем интент об окончании
    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent(END_SERVICE);
        intent.putExtra(PROGRESS_FINISH, PROGRESS_FINISH);
        localBroadcastManager.sendBroadcast(intent);
    }




}
