package com.doschechko.matylionak.domain.interaction;

//import com.example.user.data.entity.Profile;

import android.util.Log;

import com.doschechko.matylionak.data.entity.WcProfileData;
import com.doschechko.matylionak.data.net.Rest.RestService;
import com.doschechko.matylionak.domain.entity.WcId;
import com.doschechko.matylionak.domain.entity.WcProfileModel;
import com.doschechko.matylionak.domain.interaction.base.UseCase;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

//import com.example.user.domain.entity.ProfileModel;
//import com.example.user.domain.entity.ProfileId;

/**
 * Created by user on 11.08.2017.
 */
public class UseCaseGetImageLinkWC extends UseCase<WcId, WcProfileModel> {

    @Override
    protected Observable<WcProfileModel> builtUseCase(WcId param) {
            return RestService.getInstance().getImageLinkWC(param.getId())
                    .map(new Function<WcProfileData, WcProfileModel>() {

                        @Override
                        public WcProfileModel apply(WcProfileData profileData) throws Exception {
                            WcProfileModel profileModel = new WcProfileModel();
                            profileModel.setImage(profileData.getImage());
                            return profileModel;
                        }
                    });
    }


}
