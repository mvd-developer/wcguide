package com.doschechko.matylionak.wcguide.maps;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doschechko.matylionak.domain.entity.WcProfileModel;
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
        , GoogleMap.OnInfoWindowClickListener

{

    private Activity_Maps activity;
    Activity_MapsViewModel activity_mapsViewModel = this;
    FragmentManager manager;
    private ArrayList<WcProfileModel> listWc;
    private UseCaseGetListWC useCaseGetListWC = new UseCaseGetListWC();
    // private UseCaseGetImageLinkWC useCaseGetImageLinkWC = new UseCaseGetImageLinkWC();
    private SupportMapFragment mapFragment;
    private boolean visibleOnScreen = false;
    private View mapView;
    private LocationManager locationManager;
    private Location location;
    private GoogleMap googleMap;
    private boolean isFirstStart = true;
    private boolean getPermission = true;
    //private String urlImageGlideurl;
    //  private boolean isContinue=false;


    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public void setActivity(Activity_Maps activity) {
        this.activity = activity;
    }

    public void setGetPermission(boolean getPermission) {
        this.getPermission = getPermission;
    }

    @Override
    public void init() {

        Toast.makeText(activity.getContext(),
                R.string.loading_data,
                Toast.LENGTH_LONG).show();
        visibleOnScreen = true;
        useCaseGetListWC.execute(null, new DisposableObserver<List<WcProfileModel>>() {
            @Override
            public void onNext(@NonNull List<WcProfileModel> wcProfileModels) {
                listWc = new ArrayList<>();
                listWc.addAll(wcProfileModels);

                //проверка на состояние активити
                if (visibleOnScreen) {
                    mapFragment = (SupportMapFragment) activity.getChildFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(activity_mapsViewModel);
                    mapView = mapFragment.getView();
                }
            }
            @Override
            public void onError(@NonNull Throwable e) {

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

            }
        });
    }

    @Override
    public void resume() {


    }

    @Override
    public void pause() {
        if (locationListener != null && locationManager != null)
            locationManager.removeUpdates(locationListener);
        visibleOnScreen = false;


        if (useCaseGetListWC != null)
            useCaseGetListWC.dispose();

/*
УДАЛЯЕМ ФРАГМЕНТ с родительского фрагмента - это не баг, а фича Google Maps v2
 :-))))
 */
        FragmentManager managerA = activity.getChildFragmentManager();

        try {
            if (mapFragment != null) {
                FragmentTransaction transaction = managerA.beginTransaction().remove(mapFragment);
                transaction.commitNow();
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void release() {

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
            if(getPermission) {
                setLocation(location);
                //только при первом запуске центрирование по местоположению
                if (location != null && isFirstStart) {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(
                            new LatLng(location.getLatitude(), location.getLongitude())).zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                isFirstStart = false;
                //для отключения слушателя, потому что он нам больш6:е не надо
                if (locationListener != null && locationManager != null)
                    locationManager.removeUpdates(locationListener);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        setGoogleMap(googleMap);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        googleMap.getUiSettings().setZoomControlsEnabled(true);//кнопки зума

        //перемещает кнопку MyLocationButton на нижнюю треть экрана
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);


        rlp.setMargins(0, 0, 30, getDisplaySizes()[0] / 3);

        googleMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL);//установка типа карты
        double latitude = 0.0;
        double longitude = 0.0;



        for (int i = 0; i < listWc.size(); i++) {//расставляем точки из листа,который пришел с сервера
            LatLng coordinatsWC;
            try {

                String strLatitude = parseWC(listWc.get(i).getCoordinats())[0];
                String strLongitude = parseWC(listWc.get(i).getCoordinats())[1];
                latitude = Double.valueOf(strLatitude);
                longitude = Double.valueOf(strLongitude);
            } catch (Exception e) {

                continue;

            }
            coordinatsWC = new LatLng(latitude, longitude);
            String markerSnippet = listWc.get(i).getWork_time() + "\n" + "Подробнее...";
            String markerTitle = listWc.get(i).getAddress();

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(coordinatsWC)
                    .title(markerTitle)
                    .snippet(markerSnippet);

            //кастомное инфовью
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {

                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

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
                    int height = getDisplaySizes()[0] / 8;//
                    //задаем размеры менюшки
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(finalSize, height);

                    //делаем картинку

                    final ImageView imageView = new ImageView(activity.getContext());
                    imageView.setLayoutParams(layoutParams);

                    imageView.setImageResource(R.drawable.splash_proper_min);
                    //добавляем элементы в менюшку
                    info.addView(imageView);
                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });

            if (OpenCloseWC(listWc.get(i).getWork_time())) {

                if (listWc.get(i).getCost().equalsIgnoreCase("платный")) {

                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.toilet_ac_small_gold));
                } else
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.toilet_ac_blue_small));
            } else {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.toilet_ac_red_small));
            }

            Marker marker = googleMap.addMarker(markerOptions);
            marker.setTag(listWc.get(i).getObjectId());//устанавливаем маркеру ТАГ с кго номером в базе данных
            //по этому номеру будем открывать подробности об этом элементе

        }

        if(getPermission) {
            googleMap.setMyLocationEnabled(true);//разрешение на определение текущего местополощения

            //эта куча кода для получения текушего местоположения
            locationManager = (LocationManager) activity.getContext().getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 50, 1, locationListener);
            }
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50, 1, locationListener);
            }
        }
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
        } catch (ParseException e) {
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

        //return true;
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Bundle bundle = new Bundle();
        bundle.putString("bundle_item_wc", marker.getTag().toString());
        FragmentManager fragManager = activity.getActivity().getSupportFragmentManager();
        Activity_Item_WC activity_item_wc = Activity_Item_WC.newInstance(fragManager, "Activity_Item_WC");
        activity_item_wc.setArguments(bundle);
        try {
            ToolBarFragmentActivityViewModel.showFragment(fragManager, activity_item_wc, true);//а поставлю-ка я тут true
        } catch (Exception e) {

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