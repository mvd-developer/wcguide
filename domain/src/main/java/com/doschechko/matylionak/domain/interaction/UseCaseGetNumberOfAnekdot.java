package com.doschechko.matylionak.domain.interaction;

import com.doschechko.matylionak.data.net.Rest.RestService;
import com.doschechko.matylionak.domain.interaction.base.UseCase;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;



public class UseCaseGetNumberOfAnekdot extends UseCase<Integer, Integer>{
    Observable<Integer> observable;
    @Override
    protected Observable<Integer> builtUseCase(Integer integer) {
        return RestService.getInstance().getNumberOfAdekdot().doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                 observable = Observable.just(integer);
            }
        });
    }
}
