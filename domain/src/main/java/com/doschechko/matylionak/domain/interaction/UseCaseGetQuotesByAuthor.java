package com.doschechko.matylionak.domain.interaction;

import com.doschechko.matylionak.data.entity.QuoteData;
import com.doschechko.matylionak.data.net.Rest.RestService;
import com.doschechko.matylionak.domain.entity.Quote;
import com.doschechko.matylionak.domain.interaction.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


public class UseCaseGetQuotesByAuthor extends UseCase<List<QuoteData>, List<Quote>>{
    private String s = ""; //хранится имя автора, котого нам передали в конструкторе
    private static int index = 0;

    public UseCaseGetQuotesByAuthor(String str) {
        if(!s.equals(str)){
            index = 0;
        }

        this.s =str;
    }

    @Override
    protected Observable<List<Quote>> builtUseCase(List<QuoteData> quoteDatas) {
        String link = generateLink(s);
        return RestService.getInstance().getQuotesByAuthor(link)
                .map(new Function<List<QuoteData>, List<Quote>>() {
                    @Override
                    public List<Quote> apply(@NonNull List<QuoteData> quoteDatas) throws Exception {
                        List<Quote> list = new ArrayList<>();

                        for (QuoteData i : quoteDatas) {
                            Quote quote = new Quote(i.getQuote_body(), i.getQuote_author(), i.getQuote_author());
                            list.add(quote);
                        }
                        index =index+5;
                        return list;
                    }
                });
    }

    private String generateLink(String name){
        StringBuilder builder = new StringBuilder("data/quotes?where=quote_author");
        builder.append("%3D%27");
        builder.append(name);
        builder.append("%27");
        builder.append("&pageSize=5&offset=");
        builder.append(String.valueOf(index));
        return  builder.toString();
    }
}
