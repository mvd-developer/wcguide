package com.doschechko.matylionak.wcguide.horoscope;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;
import android.widget.Toast;

import com.doschechko.matylionak.domain.entity.Horoscope;
import com.doschechko.matylionak.domain.interaction.UseCaseGetHoroscope;
import com.doschechko.matylionak.domain.interaction.UseCaseGetImg;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.STATE;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.main.MainActivity;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


public class Item_HoroscopeViewModel implements BaseFragmentViewModel {
    private ObservableField<String> name = new ObservableField<>("");
    private ObservableField<String> date = new ObservableField<>("");
    private ObservableField<String> text = new ObservableField<>("");
    private ObservableField<STATE> state = new ObservableField<>(STATE.PROGRESS);
    private ObservableField<Integer> img = new ObservableField<>(R.drawable.img_6);
    private int index;
    private UseCaseGetHoroscope useCaseGetHoroscope;
    private Activity activity;
    //для загрузки картинок
    private ObservableField<Integer> imageRes = new ObservableField<>(R.drawable.bg_01);


    public Item_HoroscopeViewModel(int index) {
        this.index = index;
    }


    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }




    @Override
    public void init() {
        state.set(STATE.PROGRESS);
        UseCaseGetImg caseGetImg = new UseCaseGetImg();
        caseGetImg.setActivity(activity.getBaseContext());
        imageRes.set(caseGetImg.getImgRes());
        switch (index){
            case 0:
                img.set(R.drawable.img_1);
                break;
            case 1:
                img.set(R.drawable.img_2);
                break;
            case 2:
                img.set(R.drawable.img_3);
                break;
            case 3:
                img.set(R.drawable.img_4);
                break;
            case 4:
                img.set(R.drawable.img_5);
                break;
            case 5:
                img.set(R.drawable.img_6);
                break;
            case 6:
                img.set(R.drawable.img_7);
                break;
            case 7:
                img.set(R.drawable.img_8);
                break;
            case 8:
                img.set(R.drawable.img_9);
                break;
            case 9:
                img.set(R.drawable.img_10);
                break;
            case 10:
                img.set(R.drawable.img_11);
                break;
            case 11:
                img.set(R.drawable.img_12);
                break;
        }

        //получаем arraylist знаков зодиака
        Horoscope horoscope = Horoscope.getInstance();
        horoscope.setContext(activity);//нужно передать ссылку на активити,чтобы смогли получить доступ
        horoscope.initialize();
        //устанавливаем поля названия и даты
        name.set(horoscope.getZnakArrayList().get(index).getName());
        date.set(horoscope.getZnakArrayList().get(index).getDate());



        useCaseGetHoroscope = new UseCaseGetHoroscope();
        useCaseGetHoroscope.setUrl(horoscope.getZnakArrayList().get(index).getUrl());
        useCaseGetHoroscope.execute(null, new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                text.set(s);
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


    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
        useCaseGetHoroscope.dispose();
    }

    @Override
    public void release() {

    }


    public ObservableField<String> getName() {
        return name;
    }
    public void setName(ObservableField<String> name) {
        this.name = name;
    }
    public ObservableField<String> getDate() {
        return date;
    }
    public void setDate(ObservableField<String> date) {
        this.date = date;
    }
    public ObservableField<String> getText() {
        return text;
    }
    public void setText(ObservableField<String> text) {
        this.text = text;
    }
    public ObservableField<Integer> getImg() {
        return img;
    }
    public void setImg(ObservableField<Integer> img) {
        this.img = img;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public ObservableField<STATE> getState() {
        return state;
    }


    public ObservableField<Integer> getImageRes() {
        return imageRes;
    }

    public void setImageRes(ObservableField<Integer> imageRes) {
        this.imageRes = imageRes;
    }
}
