package com.doschechko.matylionak.wcguide.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.doschechko.matylionak.domain.interaction.useCaseRealm.UseCaseStartLoading;
import com.doschechko.matylionak.wcguide.CONSTANTS;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.toolbar.ToolBarFragmentActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback
      {
    private Button startButton;
    private WaveLoadingView waveLoadingView;
    private Disposable observable;
    private UseCaseStartLoading loading;
    private SharedPreferences preferences;
    private final int REQUEST_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());//подключение Crashlytics
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(CONSTANTS.PREFERENCE, Context.MODE_PRIVATE);
        waveLoadingView = findViewById(R.id.waveLoadingView);
        startWave();
        startButton = findViewById(R.id.buttonFindWC);
        startLoadingData();   //start downloading data from Internet and load it in RealM

    }

    /**
     * Custom progress bar loading in a cycle
     */
    private void progress() {
        observable = Observable
                .intervalRange(0, 10, 0, 500, TimeUnit.MILLISECONDS)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    int num = (int) (aLong * 10);
                    waveLoadingView.setProgressValue(num);
                });
    }

    void startLoadingData() {
        loading = new UseCaseStartLoading();
        loading.execute(null, new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                startButton.setOnClickListener(v -> {
                    preferences.edit().putBoolean(CONSTANTS.FIRST_ENTER, true).apply();
                    Intent intent = new Intent(MainActivity.this, ToolBarFragmentActivity.class);
                    MainActivity.this.startActivity(intent);

                });
                if (aBoolean) {

                    Toast.makeText(getApplicationContext(), R.string.data_is_loaded, Toast.LENGTH_SHORT).show();
                    waveLoadingView.setVisibility(View.GONE);
                    if(!startButton.getText().toString().equals(R.string.findWC))
                        startButton.setText(R.string.findWC);
                    startButton.setVisibility(View.VISIBLE);
                    startButton.setEnabled(true);
                    if (!observable.isDisposed()) {
                        observable.dispose();
                    }
                    askPermis();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), R.string.noInternetConnection, Toast.LENGTH_SHORT).show();
                waveLoadingView.setVisibility(View.GONE);
                startButton.setText(R.string.try_else);
                startButton.setVisibility(View.VISIBLE);
                startButton.setEnabled(true);
                startButton.setOnClickListener(v -> {
                    startButton.setVisibility(View.GONE);
                    waveLoadingView.setVisibility(View.VISIBLE);
                    startWave();
                    startLoadingData();
                });
            }

            @Override
            public void onComplete() {

            }
        });

    }

          private void startWave(){
              waveLoadingView.setProgressValue(50);
              waveLoadingView.setAmplitudeRatio(80);
              waveLoadingView.setAnimDuration(1500);
              waveLoadingView.startAnimation();
              progress();//loading progress bar
          }

          @Override
          protected void onResume() {
              super.onResume();

          }

          @Override
    protected void onPause() {
        loading.dispose();
        super.onPause();
    }

    public static void showMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

          public void askPermis(){


              if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                      PackageManager.PERMISSION_GRANTED
                      && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                      PackageManager.PERMISSION_GRANTED) {
                  ActivityCompat.requestPermissions(this,
                          new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                  android.Manifest.permission.ACCESS_FINE_LOCATION},
                          REQUEST_LOCATION);
              }

          }

          @Override
          public void onRequestPermissionsResult(int requestCode,
                                                 @android.support.annotation.NonNull String[] permissions,
                                                 @android.support.annotation.NonNull int[] grantResults) {
              switch (requestCode) {
                  case REQUEST_LOCATION: {

                      if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                          preferences.edit().putBoolean(CONSTANTS.KEY_PERMISSION, true ).apply();

                      } else {
                          Toast.makeText(getBaseContext(), "Для корректной работы требуется подтвердить " +
                                          "запрашиваемые разрешения или задать их вручную!",
                                  Toast.LENGTH_LONG).show();

                          preferences.edit().putBoolean(CONSTANTS.KEY_PERMISSION, false ).apply();

                      }
                  }

              }
          }



}
