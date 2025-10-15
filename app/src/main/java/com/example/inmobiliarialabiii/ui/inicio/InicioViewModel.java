package com.example.inmobiliarialabiii.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class InicioViewModel extends AndroidViewModel {

    private MutableLiveData<MapaActual> mMapaActual = new MutableLiveData<>();

    public InicioViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaActual> getMMapaActual(){
        return mMapaActual;
    }

    public void cargarMapa(){
        MapaActual mapaActual = new MapaActual();
        mMapaActual.setValue(mapaActual);
    }



    public class MapaActual implements OnMapReadyCallback{
        LatLng sanluis = new LatLng(-33.280572 , -66.332482);
        LatLng ulp = new LatLng(-33.150720 , -66.386864);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MarkerOptions marcadorSanLuis = new MarkerOptions();
            marcadorSanLuis.position(sanluis);
            marcadorSanLuis.title("San Luis");

            googleMap.addMarker(marcadorSanLuis);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            CameraPosition cameraPosition = new CameraPosition
                    .Builder()
                    .target(sanluis)
                    .zoom(30)
                    .bearing(45)
                    .tilt(15)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}