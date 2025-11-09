package com.example.inmobiliarialabiii.ui.perfil;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliarialabiii.request.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarClaveViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private final MutableLiveData<String> mLimppiarCampos = new MutableLiveData<>();
    private String mensajeError ="";

    public ActualizarClaveViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMMensaje() {
        return mMensaje;
    }

    public LiveData<String> getMLimpiarCampos(){
        return mLimppiarCampos;
    }

    public void cambiarClave(String claveAntigua, String claveNueva, String claveNuevaVerificacion) {
        mensajeError="";

        boolean esValido = true;

        if (!validarCampoVacio(claveAntigua, "Debe ingresar su clave anterior")) esValido = false;
        if (!validarCampoVacio(claveNueva, "Debe ingresar su nueva clave")) esValido = false;
        if (!validarCampoVacio(claveNuevaVerificacion, "Debe repetir su nueva clave"))
            esValido = false;

        if (!esValido) {
            mMensaje.setValue(mensajeError);
            return;
        }

        if (!claveNueva.equals(claveNuevaVerificacion)) {
            mMensaje.setValue("Las claves deben ser iguales");
        }

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService inmobiliariaService = ApiClient.getApiInmobiliaria();
        Call<Void> llamada = inmobiliariaService.cambiarClave("Bearer " + token, claveAntigua, claveNueva);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mMensaje.postValue("Contraseña modificada con éxito");
                    mLimppiarCampos.postValue("");
                }
                else {
                    try {
                        if(response.errorBody().string().equals("La contraseña actual es incorrecta.")){
                            mMensaje.postValue("La contraseña actual es incorrecta");
                        }
                        else{
                            mMensaje.postValue("Error al actualizar");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mMensaje.postValue("Error de servidor");
            }
        });
    }

    private boolean validarCampoVacio(String campo, String mensaje){
        boolean esValido = true;
        if(campo.isBlank()){
            mensajeError += mensaje + "\n";
            esValido = false;
        }
        return esValido;
    }

}