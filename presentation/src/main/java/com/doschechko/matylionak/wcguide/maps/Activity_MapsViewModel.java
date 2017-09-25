package com.doschechko.matylionak.wcguide.maps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doschechko.matylionak.domain.entity.WcId;
import com.doschechko.matylionak.domain.entity.WcProfileModel;
import com.doschechko.matylionak.domain.interaction.UseCaseGetImageLinkWC;
import com.doschechko.matylionak.domain.interaction.UseCaseGetListWC;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.main.MainActivity;
import com.doschechko.matylionak.wcguide.toolbar.ToolBarFragmentActivityViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.LOCATION_SERVICE;

public class Activity_MapsViewModel implements BaseFragmentViewModel, OnMapReadyCallback
        , GoogleMap.OnMarkerClickListener
        , GoogleMap.OnInfoWindowClickListener {


    private Activity_Maps activity;
    Activity_MapsViewModel activity_mapsViewModel = this;
    FragmentManager manager;
    private ArrayList<WcProfileModel> listWc;
    private UseCaseGetListWC useCaseGetListWC = new UseCaseGetListWC();
    private UseCaseGetImageLinkWC useCaseGetImageLinkWC = new UseCaseGetImageLinkWC();
    private SupportMapFragment mapFragment;
    private boolean visibleOnScreen = false;
    private View mapView;
    private LocationManager locationManager;
    private Location location;
    private GoogleMap googleMap;
    private boolean isFirstStart = true;
    private String urlImageGlideurl;
    //  private boolean isContinue=false;


    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public void setActivity(Activity_Maps activity) {
        this.activity = activity;
    }

    @Override
    public void init() {
        Toast.makeText(activity.getContext(),
                R.string.loading_data,
                Toast.LENGTH_LONG).show();
        visibleOnScreen = true;
        Log.e("final ", " init  ");
        useCaseGetListWC.execute(null, new DisposableObserver<List<WcProfileModel>>() {
            @Override
            public void onNext(@NonNull List<WcProfileModel> wcProfileModels) {
                listWc = new ArrayList<>();
                listWc.addAll(wcProfileModels);
                Log.e("final ", " onNext 00 " + listWc.size());
                /////////////////для логов
//                for (WcProfileModel list : listWc) {
//                    Log.e("final ", " resume MapsViewModel onNext " + list.toString() + "\n" +
//                            list.getCoordinats().split(",")[0].toString());
//
//                }
                //проверка на состояние активити, чтобы не было краша
                if (visibleOnScreen) {
                    mapFragment = (SupportMapFragment) activity.getChildFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(activity_mapsViewModel);
                    mapView = mapFragment.getView();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

                Log.e("final ", " onError ");
                Toast.makeText(activity.getContext(),
                        R.string.noInternetConnection,
                        Toast.LENGTH_SHORT).show();
                if (visibleOnScreen) {
                    activity.startActivity(new Intent(activity.getContext(), MainActivity.class));
                }

            }

            @Override
            public void onComplete() {

                Toast.makeText(activity.getContext(),
                        R.string.mapLoaded,
                        Toast.LENGTH_LONG).show();
                //state.set(STATE.DATA);
                Log.e("final ", " onComplete ");

            }
        });
    }

    @Override
    public void resume() {
        Log.e("final ", " resume  ");
    }

    @Override
    public void pause() {
        if (locationListener != null && locationManager != null)
            locationManager.removeUpdates(locationListener);
        visibleOnScreen = false;
        Log.e("final ", " pause  ");


        if (useCaseGetListWC != null)
            useCaseGetListWC.dispose();

/*
УДАЛЯЕМ ФРАГМЕНТ с родительского фрагмента - это не баг, а фича Google Maps v2
 :-))))
 */
       FragmentManager managerA = activity.getChildFragmentManager();

        Log.e("finalize ", " pause - manage to delite MAP  ");
        try {
            if (mapFragment != null) {
                Log.e("finalize ", " remove   manage to delite MAP");
              FragmentTransaction transaction = managerA.beginTransaction().remove(mapFragment);
                transaction.commitNow();
            }
        } catch (Exception e) {
            Log.e("finalize ", " THIS IS EXEPTION");

        }

    }

    @Override
    public void release() {
//        Log.e("finalize ", " release  ");
//        if(mapFragment!=null){
//            Log.e("finalize ", " remove  ");
//            manager.beginTransaction().remove(mapFragment).commit();
//        }

    }

    private void setLocation(Location location) {
        this.location = location;
    }

    private void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            Log.e("final ", " onLocationChanged ");
            Log.e("final ", " location " + location.getLongitude() + " " + location.getLatitude());
            setLocation(location);
            //только при первом запуске центрирование по местоположению
            if (location != null && isFirstStart) {
                Log.e("final ", " location!=null ");
                CameraPosition cameraPosition = new CameraPosition.Builder().target(
                        new LatLng(location.getLatitude(), location.getLongitude())).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            isFirstStart = false;
            //для отключения слушателя, потому что он нам больш6:е не надо
            if (locationListener != null && locationManager != null)
                locationManager.removeUpdates(locationListener);
            Log.e("final ", " locationListener is removed ");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e("final ", " onStatusChanged ");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e("final ", " onProviderEnabled ");

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e("final ", " onProviderDisabled ");

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        setGoogleMap(googleMap);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        Log.e("final ", " onMapReady  ");
        googleMap.getUiSettings().setZoomControlsEnabled(true);//кнопки зума

        //перемещает кнопку MyLocationButton на нижнюю треть экрана
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);


        Log.e("display ", " height " + getDisplaySizes()[0] + " width " + getDisplaySizes()[1]);
        // rlp.setMargins(0, 0, 30, 400);
        rlp.setMargins(0, 0, 30, getDisplaySizes()[0] / 3);


        //googleMap.getUiSettings().setMyLocationButtonEnabled(true);//установка кнопки текущего местоположения
        if (ActivityCompat.checkSelfPermission(activity.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);//разрешение на определение текущего местополощения
        googleMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL);//установка типа карты
        Log.e("final ", " onMapReady 22 ");
        double latitude = 0.0;
        double longitude = 0.0;


        Log.e("final ", " onMapReady 33 " + listWc.size());

        for (int i = 0; i < listWc.size(); i++) {//расставляем точки из листа,который пришел с сервера
            Log.e("final ", " onMapReady MapsViewModel onNext " + i);
            LatLng coordinatsWC;
            try {

                String strLatitude = parseWC(listWc.get(i).getCoordinats())[0];
                String strLongitude = parseWC(listWc.get(i).getCoordinats())[1];
                latitude = Double.valueOf(strLatitude);
                longitude = Double.valueOf(strLongitude);
            } catch (Exception e) {

                Log.e("wcerror ", e.toString() + latitude + " " + longitude);
                continue;

            }
            Log.e("wcerror ", "i " + i);
            coordinatsWC = new LatLng(latitude, longitude);
            String markerSnippet = listWc.get(i).getWork_time() + "\n" + "Подробнее...";
            String markerTitle = listWc.get(i).getAddress();

            Log.e("marker ", markerSnippet + " " + markerTitle);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(coordinatsWC)
                    .title(markerTitle)
                    .snippet(markerSnippet);

            //кастомное инфовью
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {

                    Log.e("urlImageGlideurl ", " getInfoWindow ");
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    Log.e("urlImageGlideurl ", "кастомное инфовью ");

                    LinearLayout info = new LinearLayout(activity.getContext());
                    info.setOrientation(LinearLayout.VERTICAL);
                    //делаем title
                    TextView title = new TextView(activity.getContext());
                    title.setMinWidth((int) (getDisplaySizes()[1] / 2.66));
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());
                    title.measure(0, 0);

                    //делаем snippet
                    TextView snippet = new TextView(activity.getContext());
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());
                    snippet.measure(0, 0);
                    //задаем размеры менюшки
                    int finalSize = 0;
                    int widthSnippet = snippet.getMeasuredWidth(); // ширина по тексту
                    int widthTitle = title.getMeasuredWidth(); // ширина по адресу

                    finalSize = widthSnippet > widthTitle ? widthSnippet : widthTitle;
                    Log.e("title", finalSize + " " + widthSnippet + " " + widthTitle);
                    int height = getDisplaySizes()[0] / 8;//
                    //задаем размеры менюшки
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(finalSize, height);

                    //делаем картинку

                    final ImageView imageView = new ImageView(activity.getContext());
                    imageView.setLayoutParams(layoutParams);
                    Log.e("urlImageGlideurl", "marker.getTag().toString() " + marker.getTag().toString());
//                    useCaseGetImageLinkWC.execute(new WcId(marker.getTag().toString()), new DisposableObserver<WcProfileModel>() {
//                        @Override
//                        public void onNext(@NonNull WcProfileModel wcProfileModel) {
//                            urlImageGlideurl = wcProfileModel.getImage();
//                            Log.e("urlImageGlideurl ", "url " + urlImageGlideurl);
////                            if (urlImageGlideurl != null) {
////                                Activity_Item_WC_ViewModel.loadImageWC(imageView, urlImageGlideurl);
////                            } else {
////
////                                imageView.setImageResource(R.drawable.no_photo);
////                            }
//
//                            Log.e("urlImageGlideurl ", "url in excecute " + urlImageGlideurl);
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//                            isContinue=true;
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            Log.e("urlImageGlideurl ", "onComplete " + urlImageGlideurl);
//                            isContinue=true;
//
//
//                        }
//                    });


                    //  Log.e("urlImageGlideurl ", "url polsle anonima " + urlImageGlideurl);
//                    if(urlImageGlideurl!=null) {
//                        Activity_Item_WC_ViewModel.loadImageWC(imageView, urlImageGlideurl);
//                    }else {
//                        imageView.setImageResource(R.drawable.no_photo);
//                    }
                    imageView.setImageResource(R.drawable.no_photo);
                    //добавляем элементы в менюшку
                    info.addView(imageView);
                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });

            if (OpenCloseWC(listWc.get(i).getWork_time())) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.toilet_ac_blue_small));
            } else {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.toilet_ac_red_small));
            }

            Marker marker = googleMap.addMarker(markerOptions);
            marker.setTag(listWc.get(i).getObjectId());//устанавливаем маркеру ТАГ с кго номером в базе данных
            //по этому номеру будем открывать подробности об этом элементе

        }
        Log.e("final ", " for otrabotal ");

        //эта куча кода для получения текушего местоположения
        locationManager = (LocationManager) activity.getContext().getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e("final ", "LocationManager.GPS_PROVIDER " + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 50, 1, locationListener);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.e("final ", "LocationManager.NETWORK_PROVIDER " + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50, 1, locationListener);
        }
        Log.e("final ", " locationManager toString" + locationManager.toString());
        Log.e("final ", " locationManager providers 2" + locationManager.getAllProviders().toString());
        //location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), true));

        //устанавливаем  камеру на текущее местоположение с коэф. 15 без анимации
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
//                location.getLongitude()), 15));

        //устанавливаем  камеру на центр минска с коэф. 15 с анимацией

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(53.89561, 27.547938)).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    //проверяет, работает ли туалет в текущий момент
    private boolean OpenCloseWC(String work_time) {

        String[] arrWorkWcTime;
        String wcStart;
        String wcEnd;
        if (work_time.equals("Круглосуточно")) {

            return true;
        }

        //если ошибка парсинга данных, то сделаем закрытым
        try {
            arrWorkWcTime = parseWC(work_time);
            wcStart = arrWorkWcTime[0];
            wcEnd = arrWorkWcTime[1];
        } catch (Exception e) {
            return false;
        }
        //проверка на круглосуточность
        String checkKruglosut = "00:00";
        if ((wcStart.equals(checkKruglosut) && wcEnd.equals(checkKruglosut))) {
            return true;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        long workWcTimeStart = 0;
        long workWcTimeEnd = 0;
        long currentTime = 0;
        try {
            workWcTimeStart = simpleDateFormat.parse(wcStart).getTime();
            workWcTimeEnd = simpleDateFormat.parse(wcEnd).getTime();
            currentTime = simpleDateFormat.parse(simpleDateFormat.format(new Date())).getTime();
            Log.e("time ", " wcStart" + wcStart + "xs");
            Log.e("time ", " wcEnd" + wcEnd + "sss");
            Log.e("time ", " currentTime " + currentTime);
        } catch (ParseException e) {
            Log.e("time ", " parse exception");
        }
        //проверка на время окончания работы, если до полуночи
        if ((workWcTimeStart < currentTime) && wcEnd.equals("00:00"))
            return true;
        if (workWcTimeEnd < workWcTimeStart) {
            if ((currentTime > workWcTimeEnd && currentTime > workWcTimeStart) ||
                    (currentTime < workWcTimeEnd && currentTime < workWcTimeStart)) {
                return true;

            } else
                return false;
        }

        return (workWcTimeEnd > currentTime) && (workWcTimeStart < currentTime);
    }

    //парсит координаты и время
    @android.support.annotation.NonNull
    private String[] parseWC(String s) {
        String[] str = s.replace(" ", "").split("[,-]");
        return str;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("final ", " onMarkerClick ");

        //return true;
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Log.e("final ", " onInfoWindowClick ");
        Log.e("final ", " oblect Id on marker " + marker.getTag());
        Bundle bundle = new Bundle();
        bundle.putString("bundle_item_wc", marker.getTag().toString());
        FragmentManager fragManager = activity.getActivity().getSupportFragmentManager();
        Activity_Item_WC activity_item_wc = Activity_Item_WC.newInstance(fragManager,"Activity_Item_WC");
        activity_item_wc.setArguments(bundle);
        try {
            ToolBarFragmentActivityViewModel.showFragment(fragManager,activity_item_wc, true);//а поставлю-ка я тут true
        } catch (Exception e) {

            Log.e("wcerror ", e.toString());
        }
    }

    //вычисляем размер экрана, возвращает массив с 2мя значениями: 1-height, 2-width
    private int[] getDisplaySizes() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int[] dispSizesArr = {height, width};
        return dispSizesArr;
    }
}