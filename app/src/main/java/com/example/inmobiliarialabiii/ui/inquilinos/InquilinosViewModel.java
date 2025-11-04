package com.example.inmobiliarialabiii.ui.inquilinos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> mInmuebleInquilinos = new MutableLiveData<>();
    public InquilinosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getMImuebleInquilinos() {
        return mInmuebleInquilinos;

    }

    public void leerInmuebleConContrato(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<List<Inmueble>> llamada = api.obtenerInmueblesPorContrato("Bearer " + token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    mInmuebleInquilinos.postValue(response.body());
                }
                else{
                    Toast.makeText(getApplication(), "No hay inmuebles con contratos vigentes disponibles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en servidor", Toast.LENGTH_SHORT).show();

            }
        });
    }
}