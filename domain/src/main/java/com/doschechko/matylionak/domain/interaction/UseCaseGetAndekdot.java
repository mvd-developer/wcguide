package com.doschechko.matylionak.domain.interaction;

import com.doschechko.matylionak.data.entity.AnekdotData;
import com.doschechko.matylionak.data.net.Rest.RestService;
import com.doschechko.matylionak.domain.entity.Anekdot;
import com.doschechko.matylionak.domain.interaction.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


public class UseCaseGetAndekdot extends UseCase<List<AnekdotData>, List<Anekdot>> {
    //private static int index = 0;
    public static int index = 0;
    @Override
    protected Observable<List<Anekdot>> builtUseCase(List<AnekdotData> anekdotDatas) {
        return RestService.getInstance().getAnekdotWithNumber(String.valueOf(index))
                .map(new Function<List<AnekdotData>, List<Anekdot>>() {
                    @Override
                    public List<Anekdot> apply(@NonNull List<AnekdotData> anekdotDatas) throws Exception {
                        List<Anekdot> list = new ArrayList<>();

                        for (AnekdotData i : anekdotDatas) {
                            Anekdot anekdot = new Anekdot(i.getBody());
                            list.add(anekdot);
                        }
                        index =index+5;
                        return list;
                    }
                });
    }

}
