package com.doschechko.matylionak.data.net;

import com.doschechko.matylionak.data.entity.WcProfileData;
import java.util.List;
import io.reactivex.Observable;

/**
 * Created on 21.10.2017.
 * Common interface for RealMServiceData и RestService
 */

public interface Repository {
    Observable<List<WcProfileData>> getWC(); 
}
