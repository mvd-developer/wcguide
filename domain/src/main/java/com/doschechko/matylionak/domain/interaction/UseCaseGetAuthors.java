package com.doschechko.matylionak.domain.interaction;

import com.doschechko.matylionak.data.entity.AuthorData;
import com.doschechko.matylionak.data.net.Rest.RestService;
import com.doschechko.matylionak.domain.entity.Author;
import com.doschechko.matylionak.domain.interaction.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Этим UseCase тянем все авторов
 */

public class UseCaseGetAuthors extends UseCase<List<AuthorData>, List<Author>> {
    @Override
    protected Observable<List<Author>> builtUseCase(List<AuthorData> authorDatas) {
        return RestService.getInstance().getAuthors().map(
                new Function<List<AuthorData>, List<Author>>() {
                    @Override
                    public List<Author> apply(@NonNull List<AuthorData> authorDatas) throws Exception {
                        List<Author> list = new ArrayList<>();
                        for (AuthorData i : authorDatas) {
                            list.add(new Author(i.getName(), i.getImage(), i.getObjectId()));
                        }
                        return list;
                    }
                }

        );
    }
}
