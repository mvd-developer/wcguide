package com.doschechko.matylionak.domain.entity;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.doschechko.matylionak.domain.R;

import java.util.ArrayList;

/**
 * Синглтон для класса знаков Зодиака. Получаем объект из статического метода Horoscope.getInstance()
 */
public class Horoscope {
    private static Horoscope horoscope;
    private ArrayList<Znak> znakArrayList;
    private Context context;

    private Horoscope() {
    }

    public static Horoscope getInstance() {
        if (horoscope == null) {
            horoscope = new Horoscope();
        }
        return horoscope;
    }


    public void initialize() {

        Resources resources = context.getApplicationContext().getResources();
        znakArrayList = new ArrayList<>();
        String[] names = resources.getStringArray(R.array.domain_names);
        String[] dates = resources.getStringArray(R.array.domain_dates);
        String[] links = resources.getStringArray(R.array.domain_links);

        for (int i = 0; i < names.length; i++) {
            Znak znak = new Znak();
            znak.setName(names[i]);
            znak.setDate(dates[i]);
            znak.setUrl(links[i]);
            znakArrayList.add(znak);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public ArrayList<Znak> getZnakArrayList() {
        return znakArrayList;
    }






}
