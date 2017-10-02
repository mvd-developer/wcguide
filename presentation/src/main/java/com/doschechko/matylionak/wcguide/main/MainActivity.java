package com.doschechko.matylionak.wcguide.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.toolbar.ToolBarFragmentActivity;

public class MainActivity extends Activity
        implements ActivityCompat.OnRequestPermissionsResultCallback
{
    Button startButton;
    private final int REQUEST_LOCATION = 1;
    private boolean getPermission=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        //запрос разрешений
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);}


        startButton = (Button) findViewById(R.id.buttonFindWC);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ToolBarFragmentActivity.class);
                intent.putExtra("getPermission",getPermission);
                MainActivity.this.startActivity(intent);


//                метод для загрузки анекдотов в базу данных
//                UseCaseParseXMLSendToBackeEndless backeEndless = new UseCaseParseXMLSendToBackeEndless(MainActivity.this);
//                ArrayList<Anekdot> list = backeEndless.parseXML();
//                Log.e("ANDEKDOT", "SIZE="+String.valueOf(list.size()));

            }
        });

    }
//ответ от запроса разрешения
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @android.support.annotation.NonNull String[] permissions,
                                           @android.support.annotation.NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {

                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {
                    Toast.makeText(getBaseContext(), "Для корректной работы требуется подтвердить " +
                                    "запрашиваемые разрешения или задать их вручную!",
                            Toast.LENGTH_LONG).show();

                    getPermission = false;

                }
            }

        }
    }


    public static void showMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }


}
