package com.doschechko.matylionak.data.net.parsing;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import org.jsoup.select.Elements;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.ReplaySubject;

public class ParseHTML implements Runnable {

    private static ParseHTML instance = new ParseHTML();
    private static String url;
    ReplaySubject<String> description = ReplaySubject.create();
    String str = "";

    public static synchronized ParseHTML getInstatnce() {
        return instance;
    }


    @Override
    public void run() {
        Elements content = null;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            content = doc.select(".article__text");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReplaySubject<String> text = ReplaySubject.create();

       try{
           text.onNext(content.text());
       }
       catch (Exception e){
           Log.e("MISTAKE", "Internet HTML parsing");
       }


        text.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        description.onNext(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    public Observable<String> execute(){
        return description;
    }



    public void setUrl(String url) {
        this.url = url;
    }



}
