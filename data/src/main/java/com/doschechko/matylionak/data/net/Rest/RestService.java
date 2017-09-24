package com.doschechko.matylionak.data.net.Rest;

/*
API сервис для получения Obserable цитат
 */

import com.doschechko.matylionak.data.entity.AnekdotData;
import com.doschechko.matylionak.data.entity.AuthorData;
import com.doschechko.matylionak.data.entity.QuoteData;
import com.doschechko.matylionak.data.entity.WcProfileData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {
    private static final RestService instance = new RestService();
    private RestAPI restApi;
    private RestAPI restApiWC;

    private RestService() {
        init();
    }

    public static RestService getInstance() {
        return instance;
    }


    private void init() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS) //максимальное время получения данных от сервера
                .connectTimeout(10, TimeUnit.SECONDS) //максимальное время подключения к серверу
                .addInterceptor(logging)
                .build();
        Gson gson = new GsonBuilder().create();
        //сам ретрофит
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.backendless.com/30D50B7B-B5A7-7E64-FFFB-170C1588BA00/DD4392F8-C2C1-E087-FFF8-229EC8428500/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//добавляем RX2 Java
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build();
       // restApi = retrofit.create(RestAPI.class);
        Retrofit retrofitWC = new Retrofit.Builder()
                .baseUrl("https://api.backendless.com/E306428C-F94F-B3D5-FF35-6F20F9600400/A9468BE4-FCBE-67F3-FF31-1F1482C47A00/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//добавляем RX2 Java
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build();
        restApi = retrofit.create(RestAPI.class);
        restApiWC = retrofitWC.create(RestAPI.class);



    }


    public Observable<List<QuoteData>> getQuotes() {

        return restApi.getQuotes();

    }

    public Observable<QuoteData> getQuoteByID(String id) {


        return restApi.getQuoteByID(id);
    }


    public Observable<List<QuoteData>> getId() {

        return restApi.getId();


    }


    public Observable<List<QuoteData>> getQuotesWithNumber(String number) {
        return restApi.getQuotesWithNumber(number);

    }


    public Observable<List<AuthorData>> getAuthors() {
        return restApi.getAuthors();


    }


    public Observable<List<QuoteData>> getQuotesByAuthor(String s ) {
        return restApi.getQuotesByAuthor(s);
    }

    //просто для автоматической загрузки анекдотов на сервер
public void saveAnekdot(AnekdotData anekdot){
    Call<AnekdotData> oblect = restApi.saveAnekdot(anekdot);
    oblect.enqueue(new Callback<AnekdotData>() {
        @Override
        public void onResponse(Call<AnekdotData> call, Response<AnekdotData> response) {

        }

        @Override
        public void onFailure(Call<AnekdotData> call, Throwable t) {

        }
    });
}


        //получаем по пять анекдотов с пагинацией
 public  Observable<List<AnekdotData>> getAnekdotWithNumber(String str){
        return restApi.getAnekdotWithNumber(str);
    }

    public Observable<Integer> getNumberOfAdekdot(){
        return restApi.getNumberOfAdekdot();
    }


    public Observable<WcProfileData>getProfileWC(String id){
        return restApiWC.getProfileWC(id);

    }

    public Observable<WcProfileData>getImageLinkWC(String id){
        return restApiWC.getImageLinkWC(id);

    }

    public Observable<List<WcProfileData>>getWCLocation(){
        return restApiWC.getWCLocation();

    }



}
