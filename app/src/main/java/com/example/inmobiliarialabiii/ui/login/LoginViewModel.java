package com.example.inmobiliarialabiii.ui.login;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliarialabiii.MainActivity;
import com.example.inmobiliarialabiii.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<String> mLogin = new MutableLiveData();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMMensaje(){

        return mMensaje;
    }

    public void login(String mail, String clave){
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(mail, clave);

        llamada.enqueue(new Callback<String>() {
            @Override
            //se ejecuta siempre que hay ua respuesta
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    mMensaje.postValue("Bienvenido"); // es post value porque está en un ámbito asincrono
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                }
                else{
                    mMensaje.postValue("Usuario y/o contraseña incorrectos");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mMensaje.postValue("Error de servidor");
            }


        });

    }


}
