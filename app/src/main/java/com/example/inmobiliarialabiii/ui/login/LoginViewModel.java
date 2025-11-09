package com.example.inmobiliarialabiii.ui.login;

import static java.lang.Math.sqrt;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliarialabiii.MainActivity;
import com.example.inmobiliarialabiii.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<String> mLogin = new MutableLiveData<>();
    private Float acceleration = 0f;
    private Float anteriorAcceleration = 0f;
    private Float actualAcceleration = 0f;
    private SensorManager manager;
    private List<Sensor> sensores;
    private ManejaSensores maneja;

    private final float SHAKE_THRESHOLD = 52f;
    private final float SHAKE_RESET_THRESHOLD = 0.001f;
    private boolean shakeInProgress = false;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMMensaje(){

        return mMensaje;
    }

    public void login(String mail, String clave){
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(mail, clave);

        if(mail.isBlank()){
            mMensaje.setValue("Debe ingresar un Email");
        }
        else if (clave.isBlank()){
            mMensaje.setValue("Debe Ingresar la contraseña");
        }
        else{
            llamada.enqueue(new Callback<String>() {
                @Override
                //se ejecuta siempre que hay una respuesta
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

    public void activarLecturas(){
        manager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
        sensores = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        maneja = new ManejaSensores();
        manager.registerListener(maneja, sensores.get(0), SensorManager.SENSOR_DELAY_GAME);

    }


    public void desactivarLecturas(){
        if (!sensores.isEmpty())
            manager.unregisterListener(maneja); //desregistra la misma instancia.
    }
    private class ManejaSensores implements SensorEventListener {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { //cuando cambia la presicion

        }

        @Override
        public void onSensorChanged(SensorEvent event) { //cuando el sensor envía una notificación
            float ejeX = event.values[0];
            float ejeY = event.values[1];
            float ejeZ = event.values[2];
            anteriorAcceleration = actualAcceleration;

            actualAcceleration = (float) sqrt((ejeX * ejeX + ejeY * ejeY + ejeZ * ejeZ));
            float delta = actualAcceleration - anteriorAcceleration;
            acceleration = acceleration * 0.9f + delta;

            if (!shakeInProgress && acceleration > 12 ) {
                shakeInProgress = true;
                realizarLlamada();
            }else if (shakeInProgress && acceleration < SHAKE_RESET_THRESHOLD){
                shakeInProgress = false;
            }
        }

    }
    private void realizarLlamada() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:2664899944"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }
}
