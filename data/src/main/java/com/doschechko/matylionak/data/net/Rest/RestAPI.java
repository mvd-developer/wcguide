package com.doschechko.matylionak.data.net.Rest;

import com.doschechko.matylionak.data.entity.AnekdotData;
import com.doschechko.matylionak.data.entity.AuthorData;
import com.doschechko.matylionak.data.entity.QuoteData;
import com.doschechko.matylionak.data.entity.WcProfileData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RestAPI {

    //загружает 5 объектов
    @GET("data/quotes?pageSize=5")
    Observable<List<QuoteData>> getQuotes();

    //загружает определенный объект
    @GET("data/quotes/{id}")
    Observable<QuoteData> getQuoteByID(@Path("id") String id);


    //загружет все id коллекции
    @GET("data/quotes?props=objectId")
    Observable<List<QuoteData>> getId();


    //для работы с данным авторов


    //загружает 5 объектов c пагинацией
    @GET("data/quotes?pageSize=5")
    Observable<List<QuoteData>> getQuotesWithNumber(@Query("offset") String number);

    //загружает все объекты авторов
    @GET("data/authors?pageSize=50")
    Observable<List<AuthorData>> getAuthors();



//    //загружает по условию "имя автора
//    @GET("data/quotes?where=")
//    Observable<List<QuoteData>> getQuotesByAuthor(@Query("quote_author")String s);


//    //загружает по условию "имя автора
//    @GET("data/quotes?where={path}")
//    Observable<List<QuoteData>> getQuotesByAuthor(@Path("path")String s);


        //загружает по условию "имя автора"
    @GET
    Observable<List<QuoteData>> getQuotesByAuthor(@Url String s);


    //отправляет данные на сервер
    @POST("data/jokes")
    Call<AnekdotData> saveAnekdot(@Body AnekdotData anekdot );


    //загружает 5 анекдотов c пагинацией
    @GET("data/jokes?pageSize=5")
    Observable<List<AnekdotData>> getAnekdotWithNumber(@Query("offset") String number);

    //загружает количество анекдотов на сервере
    @GET ("data/jokes/count?where=")
    Observable<Integer> getNumberOfAdekdot();


    /*
    для работы с картами
     */
    @GET("data/wc_base/{objectId}")
    Observable<WcProfileData> getProfileWC(@Path("objectId")String id);//получаем один туалет по Айди


    @GET("data/wc_base/{objectId}")
    Observable<WcProfileData> getImageLinkWC(@Path("objectId")String id);//получаем ссылку на картинку
    // одного туалет по Айди

    @GET("data/wc_base?pageSize=100")
    Observable <List<WcProfileData>> getWCLocation();//получаем поле с координатами и обджектАйди


    //для загрузки количества туалетов на сервере
    @GET ("data/wc_base/count?where=")
    Observable<Integer> getNumberOfWC();


}
