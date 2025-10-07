package com.example.inmobiliarialabiii.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<String> mLogin = new MutableLiveData();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMMensaje(){

        return mMensaje;
    }

    public LiveData<String> getMLogin(){
        return mLogin;
    }


    public void login(String usuario, String clave){

        if(usuario.equalsIgnoreCase("usuario") && clave.equalsIgnoreCase("123")){
            mLogin.setValue("");
        }else{
            mMensaje.setValue("Usuario y/o clave incorrectas");
        }
    }


}
