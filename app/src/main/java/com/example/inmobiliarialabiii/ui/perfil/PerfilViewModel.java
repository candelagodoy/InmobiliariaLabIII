package com.example.inmobiliarialabiii.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliarialabiii.model.Propietario;
import com.example.inmobiliarialabiii.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private final MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private final MutableLiveData<String> mBoton = new MutableLiveData<>();
    private final MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private String mensajeError ="";

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getMPropietario (){
        return mPropietario;
    }

    public LiveData<Boolean> getMEstado(){
        return mEstado;
    }

    public LiveData<String> getMBoton(){
        return mBoton;
    }

    public LiveData<String> getMMensaje(){
        return mMensaje;
    }

    public void guardar(String boton, String nom, String ap, String dni, String tel, String email){

        boolean ret = false;

        if(boton.equalsIgnoreCase("Editar")){
            mEstado.setValue(true);
            mBoton.setValue("GUARDAR");
        }
        else{
            mensajeError = "";
            boolean esValido = true;

            if (!validarCampoVacio(nom, "Debe ingresar un nombre")) esValido = false;
            if (!validarCampoVacio(ap, "Debe ingresar un apellido")) esValido = false;
            if (!validarCampoVacio(email, "Debe ingresar un email")) esValido = false;
            if(!validarCampoVacio(dni, "Debe ingresar un dni")) esValido = false;
            if (!validarCampoNum(dni, "Debe ingresar un valor numerico en el campo dni")) esValido = false;
            if(!validarCampoVacio(tel, "Debe ingresar un teléfono")) esValido = false;
            if (!validarCampoNum(tel, "Debe ingresar un valor numerico en el campo teléfono")) esValido = false;

            if (!esValido) {
                mMensaje.setValue(mensajeError);
                return;
            }

            Propietario p = new Propietario();
            p.setIdPropietario(getMPropietario().getValue().getIdPropietario());
            p.setNombre(nom);
            p.setApellido(ap);
            p.setEmail(email);
            p.setDni(dni);
            p.setTelefono(tel);
            p.setClave(null);

            mBoton.setValue("EDITAR");
            mEstado.setValue(false);


            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getApiInmobiliaria().actualizarPropietario("Bearer " + token, p);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        mMensaje.postValue("Propietario Actualizado correctamente");

                    }
                    else{
                        mMensaje.postValue("Error al actualizar");
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    mMensaje.postValue("Error de servidor");
                    Log.d("Error", t.getMessage());
                }
            });

        }
    }

    public void leerPropietario(){
        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getApiInmobiliaria().obtenerPropietario("Bearer " + token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    mPropietario.postValue(response.body());
                }
                else{
                    Toast.makeText(getApplication(), "No se pudo obtener el propietario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
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
    private boolean validarCampoNum(String campoNum, String mensaje){
        boolean numValido = true;
        if (!campoNum.matches("\\d+")) {
            mensajeError += mensaje + "\n";
            numValido = false;
        }
        return numValido;
    }



}