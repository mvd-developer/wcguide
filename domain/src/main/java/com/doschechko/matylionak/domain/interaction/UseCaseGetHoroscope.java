package com.doschechko.matylionak.domain.interaction;

import com.doschechko.matylionak.data.net.parsing.ParseHTML;
import com.doschechko.matylionak.domain.entity.Horoscope;
import com.doschechko.matylionak.domain.interaction.base.UseCase;


import io.reactivex.Observable;


public class UseCaseGetHoroscope extends UseCase<String, String> {
    private String url;
    private int index;


    @Override
    protected Observable<String> builtUseCase(String s) {
        ParseHTML parseHTML=ParseHTML.getInstatnce();
        parseHTML.setUrl(url);
        Thread thread = new Thread(parseHTML);
        thread.start();
        return parseHTML.execute();
    }



    public void setUrl(String url) {
        this.url = url;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
