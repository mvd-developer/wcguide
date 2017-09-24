package com.doschechko.matylionak.wcguide.citaty;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.doschechko.matylionak.domain.entity.Author;
import com.doschechko.matylionak.domain.interaction.UseCaseGetAuthors;
import com.doschechko.matylionak.wcguide.STATE;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.databinding.ActivityAllAuthorsBinding;
import com.doschechko.matylionak.wcguide.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


public class Activity_AuthorsViewModel implements BaseFragmentViewModel {
    MyGridAdaptor adaptor = new MyGridAdaptor();
    private ActivityAllAuthorsBinding binding;
    private Activity activity;
    private ObservableField<STATE> state = new ObservableField<>(STATE.PROGRESS);
    private ArrayList<Author> list = new ArrayList<>();
    private UseCaseGetAuthors useCaseGetAuthors = new UseCaseGetAuthors();
    private FragmentManager manager;


    @Override
    public void init() {
        binding.allAuthorsRecycle.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.allAuthorsRecycle.setHasFixedSize(true);

        //получаем из domain данные для работы
        useCaseGetAuthors.execute(null, new DisposableObserver<List<Author>>() {
            @Override
            public void onNext(@NonNull List<Author> authors) {
                list.addAll(authors);
                adaptor.setList(list);
                state.set(STATE.DATA);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                MainActivity.showMainActivity(activity);
                Toast.makeText(activity, "Проверьте интернет-соединение", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        });


        // нужно установить адаптер и менеджер, чтобы запускать новые активити
        adaptor.setManager(manager);
        adaptor.setActivity(activity);
        binding.allAuthorsRecycle.setAdapter(adaptor);


    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        useCaseGetAuthors.dispose();
    }

    @Override
    public void release() {

    }


    public void setBinding(ActivityAllAuthorsBinding binding) {
        this.binding = binding;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public ObservableField<STATE> getState() {
        return state;
    }

    public void setState(ObservableField<STATE> state) {
        this.state = state;
    }


    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }
}
