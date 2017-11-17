package com.doschechko.matylionak.domain.interaction.useCaseRealm;

import com.doschechko.matylionak.data.RepositoryFactory;
import com.doschechko.matylionak.domain.interaction.base.UseCase;
import com.doschechko.matylionak.domain.interaction.base.UseCaseOtherThread;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Umbra on 03.11.2017.
 */

//public class UseCaseStartLoading extends UseCaseOtherThread<Void, Boolean> {
//    @Override
//    protected Observable<Boolean> builtUseCase(Void aVoid) {
//        return RepositoryFactory.getInstance().startLoading();
//    }
//}

public class UseCaseStartLoading extends UseCase <Void, Boolean>{


    @Override
    protected Observable<Boolean> builtUseCase(Void aVoid) {
        return RepositoryFactory.getInstance().startLoading();
    }

    @Override
    public void execute(Void aVoid, DisposableObserver<Boolean> disposableObserver) {
        super.execute(aVoid, disposableObserver);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}