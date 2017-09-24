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

/**
 * Возвращает коллекцию с пагинацией. на странице по 10 цитат.
 */

public class UseCaseGetQuotesWithNumber extends UseCase<List<QuoteData>, List<Quote>> {
    private static int index = 0;


    @Override
    protected Observable<List<Quote>> builtUseCase(List<QuoteData> quoteDatas) {
        return RestService.getInstance().getQuotesWithNumber(String.valueOf(index))
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
}
