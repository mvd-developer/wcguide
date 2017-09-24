package com.doschechko.matylionak.domain.interaction;

//import com.example.user.data.entity.Profile;

import android.util.Log;

import com.doschechko.matylionak.data.entity.WcProfileData;
import com.doschechko.matylionak.data.net.Rest.RestService;
import com.doschechko.matylionak.domain.entity.WcId;
import com.doschechko.matylionak.domain.entity.WcProfileModel;
import com.doschechko.matylionak.domain.interaction.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

//import com.example.user.domain.entity.ProfileModel;
//import com.example.user.domain.entity.ProfileId;

/**
 * Created by user on 11.08.2017.
 */
public class UseCaseGetListWC extends UseCase<WcId, List<WcProfileModel>> {


    @Override
    protected Observable<List<WcProfileModel>> builtUseCase(WcId wcId) {
        return RestService.getInstance().getWCLocation().map(new Function<List<WcProfileData>, List<WcProfileModel>>() {
            @Override
            public List<WcProfileModel> apply(@NonNull List<WcProfileData> profiles) throws Exception {
                List<WcProfileModel> list = new ArrayList<>();
                for(WcProfileData profile: profiles) {
                    list.add(convert(profile));
                }
                return list;
            }
        });
    }



        private WcProfileModel convert(WcProfileData dataModel) {
            WcProfileModel profileModel = new WcProfileModel();
            profileModel.setObjectId(dataModel.getObjectId());
            profileModel.setImage(dataModel.getImage());
            profileModel.setCost(dataModel.getCost());
            profileModel.setAddress(dataModel.getAddress());
            profileModel.setCoordinats(dataModel.getCoordinats());
            profileModel.setWork_time(dataModel.getWork_time());
            return profileModel;
        }


}