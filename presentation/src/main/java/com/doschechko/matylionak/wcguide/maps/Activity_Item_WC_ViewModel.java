package com.doschechko.matylionak.wcguide.maps;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doschechko.matylionak.domain.entity.WcId;
import com.doschechko.matylionak.domain.entity.WcProfileModel;
import com.doschechko.matylionak.domain.interaction.UseCaseGetProfileWC;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.STATE;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.databinding.ActivityItemWcBinding;
import com.doschechko.matylionak.wcguide.main.MainActivity;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ya
 * on 15.09.2017.
 */

public class Activity_Item_WC_ViewModel implements BaseFragmentViewModel {


    private Activity_Item_WC activity;
    private ActivityItemWcBinding binding;
    private String wcId;
    private UseCaseGetProfileWC useCasegetProfileWC=new UseCaseGetProfileWC();
    private ObservableField<String> cost=new ObservableField<>("");
    private ObservableField<String> address=new ObservableField<>("");
    private ObservableField<String> work_time=new ObservableField<>("");
    private ObservableField<String> url=new ObservableField<>("");
    private ObservableField<STATE> state = new ObservableField<>(STATE.PROGRESS);


    public void setBinding(ActivityItemWcBinding binding) {
        this.binding = binding;
    }
    public String getWcId() {
        return wcId;
    }

    public void setWcId(String wcId) {
        this.wcId = wcId;
    }

    public void setActivity(Activity_Item_WC activity) {
        this.activity = activity;
    }

    public ObservableField<STATE> getState() {
        return state;
    }

    public void setState(ObservableField<STATE> state) {
        this.state = state;
    }

    public ObservableField<String> getUrl() {
        return url;
    }

    public void setUrl(ObservableField<String> url) {
        this.url = url;
    }

    public ObservableField<String> getCost() {
        return cost;
    }

    public void setCost(ObservableField<String> cost) {
        this.cost = cost;
    }

    public ObservableField<String> getAddress() {
        return address;
    }

    public void setAddress(ObservableField<String> address) {
        this.address = address;
    }

    public ObservableField<String> getWork_time() {
        return work_time;
    }

    public void setWork_time(ObservableField<String> work_time) {
        this.work_time = work_time;
    }

    @Override
    public void init() {

    }

    @Override
    public void resume() {
        Log.e("final"," resume");

        useCasegetProfileWC.execute(new WcId(wcId), new DisposableObserver<WcProfileModel>() {
            @Override
            public void onNext(@NonNull WcProfileModel wcProfileModel) {

                cost.set(wcProfileModel.getCost());
                address.set(wcProfileModel.getAddress());
                work_time.set(wcProfileModel.getWork_time());
                url.set(wcProfileModel.getImage());
                state.set(STATE.DATA);
                Log.e("finallog ",url.toString());
              }



            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(activity.getContext(),
                        R.string.noInternetConnection,
                        Toast.LENGTH_LONG).show();
                activity.startActivity(new Intent(activity.getContext(),MainActivity.class));

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void pause() {
        if(useCasegetProfileWC!=null)
            useCasegetProfileWC.dispose();

    }

    @Override
    public void release() {

    }
    @BindingAdapter("android:intosrc")
    public static void loadImageWC(ImageView view, String url) {
        if(url!=null) {
            Log.e("urlImageGlideurl ", "url in loadImageWC " + url);
            Glide.with(view.getContext())
                    .load(url)
                    //.apply(RequestOptions.circleCropTransform())
                    .into(view);
        }
    }

}
