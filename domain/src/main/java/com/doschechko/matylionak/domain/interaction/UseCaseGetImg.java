package com.doschechko.matylionak.domain.interaction;


import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;

public class UseCaseGetImg {
private Context context;

    ArrayList<Integer> list = new ArrayList<>();

    public Integer getImgRes() {
        for(int i=0;i<24;i++){
            list.add(context.getResources().getIdentifier("bg_horoscope_"+i, "drawable", context.getPackageName()));
        }

        Collections.shuffle(list);
        return list.iterator().next();
    }


    public void setActivity(Context context) {
        this.context = context;
    }
}
