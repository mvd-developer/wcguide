package com.doschechko.matylionak.wcguide.anekdot;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.doschechko.matylionak.domain.entity.Anekdot;
import com.doschechko.matylionak.domain.interaction.UseCaseGetAndekdot;
import com.doschechko.matylionak.domain.interaction.UseCaseGetNumberOfAnekdot;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.STATE;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.databinding.ActivityAnekdotBinding;
import com.doschechko.matylionak.wcguide.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class Activity_AnekdotViewModel implements BaseFragmentViewModel, SeekBar.OnSeekBarChangeListener {

    MyAdaptor adaptor = new MyAdaptor();
    private UseCaseGetAndekdot useCaseGetAndekdot;
    private ObservableField<STATE> state = new ObservableField<>(STATE.PROGRESS);
    private ObservableField<STATE> stateButton = new ObservableField<>(STATE.DATA);
    private ObservableField<Integer> number = new ObservableField<>(0);
    private ActivityAnekdotBinding binding;
    private SeekBar seekBar;
    private Activity activity;

    private UseCaseGetNumberOfAnekdot getNumberOfAnekdot = new UseCaseGetNumberOfAnekdot();

    @Override
    public void init() {
        seekBar = binding.seekBar;
        seekBar.getProgressDrawable().setColorFilter(activity.getResources().getColor(R.color.specialBlueColor), PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar.setOnSeekBarChangeListener(this);
        useCaseGetAndekdot = new UseCaseGetAndekdot();
        showMore();
        //получаем количество элементов в таблице анекдотов
        getNumberOfAnekdot.execute(null, new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NonNull Integer integer) {
                int i = integer / 5;
                number.set(i);
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


    public void showMore() {
        state.set(STATE.PROGRESS);
        seekBar.setProgress(UseCaseGetAndekdot.index/5);
        useCaseGetAndekdot.execute(null, new DisposableObserver<List<Anekdot>>() {
            @Override
            public void onNext(@NonNull List<Anekdot> anekdots) {
                adaptor.setList(anekdots);
                state.set(STATE.DATA);


                if (anekdots.isEmpty()) {
                    stateButton.set(STATE.PROGRESS);
                    Toast.makeText(activity, "Конец", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

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
        useCaseGetAndekdot.dispose();
        UseCaseGetAndekdot.index=0;
    }

    @Override
    public void release() {

    }


    public ObservableField<STATE> getState() {
        return state;
    }

    public void setState(ObservableField<STATE> state) {
        this.state = state;
    }

    public ObservableField<Integer> getNumber() {
        return number;
    }

    public void setBinding(ActivityAnekdotBinding binding) {
        this.binding = binding;
    }

    /*
    seekBar methods
     */

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Context context = binding.getRoot().getContext().getApplicationContext();
        Toast.makeText(context,"страница "+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
        UseCaseGetAndekdot.index = seekBar.getProgress()*5;
        showMore();
    }

    public ObservableField<STATE> getStateButton() {
        return stateButton;
    }

    public void setStateButton(ObservableField<STATE> stateButton) {
        this.stateButton = stateButton;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
