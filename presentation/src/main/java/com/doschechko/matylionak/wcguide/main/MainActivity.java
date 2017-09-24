package com.doschechko.matylionak.wcguide.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.toolbar.ToolBarFragmentActivity;

public class MainActivity extends Activity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        startButton = (Button) findViewById(R.id.buttonFindWC);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ToolBarFragmentActivity.class);
                MainActivity.this.startActivity(intent);


//                метод для загрузки анекдотов в базу данных
//                UseCaseParseXMLSendToBackeEndless backeEndless = new UseCaseParseXMLSendToBackeEndless(MainActivity.this);
//                ArrayList<Anekdot> list = backeEndless.parseXML();
//                Log.e("ANDEKDOT", "SIZE="+String.valueOf(list.size()));

            }
        });

    }

    public static void showMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }


}
