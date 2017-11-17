package com.doschechko.matylionak.domain.interaction;

import com.doschechko.matylionak.data.net.Rest.RestService;
import com.doschechko.matylionak.domain.interaction.base.UseCaseOtherThread;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


public class UseCaseGetNumberOfWC extends UseCaseOtherThread<Integer, Integer> {
    private Observable<Integer> observable;

    @Override
    protected Observable<Integer> builtUseCase(Integer integer) {
        return RestService.getInstance().getNumberOfWC().doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                observable = Observable.just(integer);
            }
        });
    }
}
