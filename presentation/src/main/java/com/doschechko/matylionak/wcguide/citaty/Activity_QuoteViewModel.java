package com.doschechko.matylionak.wcguide.citaty;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.doschechko.matylionak.domain.entity.Quote;
import com.doschechko.matylionak.domain.interaction.UseCaseGetQuotesByAuthor;
import com.doschechko.matylionak.wcguide.STATE;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


public class Activity_QuoteViewModel implements BaseFragmentViewModel {
    private String authorName = new String(Activity_Authors.AUTHOR_NAME); //костыль, чтобы создать новый объект Стринг
    private Activity activity;
    private MyAdaptor adaptor = new MyAdaptor();
    private ObservableField<STATE> state = new ObservableField<>(STATE.PROGRESS);
    private UseCaseGetQuotesByAuthor useCaseGetQuotesByAuthor = new UseCaseGetQuotesByAuthor(authorName);
    private ObservableField<STATE> stateButton = new ObservableField<>(STATE.DATA);
    private FragmentManager manager;

    @Override
    public void init() {
        pull();
    }

    //при нажатии на кнопку - "показать ещё
    public void showMore() {
        pull();
    }


    //метод, который подтягивает нам данные из UseCaseGetQuotesByAuthor
    private void pull() {
        state.set(STATE.PROGRESS);
        useCaseGetQuotesByAuthor.execute(null, new DisposableObserver<List<Quote>>() {
            @Override
            public void onNext(@NonNull List<Quote> quotes) {
                adaptor.setList(new ArrayList<>(quotes));
                adaptor.setActivity(activity);
                state.set(STATE.DATA);
                if (quotes.isEmpty()) {
                    stateButton.set(STATE.PROGRESS);
                    Toast.makeText(activity, "Конец", Toast.LENGTH_SHORT).show();
                }


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


    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(manager.findFragmentByTag(Activity_Quote.class.getName()), "dvd");
//        transaction.addToBackStack(null);
//        transaction.commit();

        Log.e("MY_FINAL", "pause");
        if (useCaseGetQuotesByAuthor != null) {
            useCaseGetQuotesByAuthor.dispose();
        }
    }

    @Override
    public void release() {
        Log.e("MY_FINAL", "release");
        //useCaseGetQuotesByAuthor.dispose();
    }


    public MyAdaptor getAdaptor() {
        return adaptor;
    }

    public void setAdaptor(MyAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ObservableField<STATE> getStateButton() {
        return stateButton;
    }

    public void setStateButton(ObservableField<STATE> stateButton) {
        this.stateButton = stateButton;
    }

    public ObservableField<STATE> getState() {
        return state;
    }
}
