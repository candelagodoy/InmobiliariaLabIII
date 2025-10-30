package com.example.inmobiliarialabiii.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliarialabiii.model.Inmueble;
import com.example.inmobiliarialabiii.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CargarInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Uri> mUri= new MutableLiveData<>();
    public CargarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getMUri(){
        return mUri;
    }

    public void recibirFoto(ActivityResult result){
        if(result.getResultCode() == RESULT_OK){ //si es codigo de respuesta es igual a la constante, significa que el usuario SI eligio una foto luego de abrir la galeria
            Intent data = result.getData();
            Uri uri = data.getData();//guarda un intent
            Log.d("salida",uri.toString());
            mUri.setValue(uri);
        }
    }

    public void cargarInmueble(String direccion, String valor, String tipo, String uso,
                               String ambientes, String superficie, boolean disponible){
        int superficiePars, ambientesPars;
        double valorPars;

        try {
            valorPars = Double.parseDouble(valor);
            superficiePars = Integer.parseInt(superficie);
            ambientesPars = Integer.parseInt(ambientes);
            if(direccion.isBlank() || tipo.isBlank() || valor.isBlank() || uso.isBlank() ||
                    ambientes.isBlank() || superficie.isBlank()){
                Toast.makeText(getApplication(), "No debe haber campos vacios",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if(mUri.getValue() == null){
                Toast.makeText(getApplication(), "Debe agregar una foto",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setValor(valorPars);
            inmueble.setTipo(tipo);
            inmueble.setUso(uso);
            inmueble.setAmbientes(ambientesPars);
            inmueble.setSuperficie(superficiePars);
            inmueble.setDisponible(disponible);

            byte[] imagen = transformarImagen();
            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse(
                    "application/json; charset=utf-8"),inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"),
                    imagen);

            //armando multipart
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen",
                    "imagen.jpg", requestFile);

            ApiClient.InmobiliariaService inmobiliariaService = ApiClient.getApiInmobiliaria();
            String token = ApiClient.leerToken(getApplication());
            Call<Inmueble> call = inmobiliariaService.cargarInmueble("Bearer " + token ,
                    imagenPart, inmuebleBody);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplication(), "Inmueble cargado exitosamente",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplication(), "Error al cargar inmueble ",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error de servidor",
                            Toast.LENGTH_LONG).show();
                }
            });



        }catch (NumberFormatException nfe){

        }

    }

    private byte[] transformarImagen(){
        try {
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);//creo un canal para conectarme a un archivo
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArratInputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArratInputStream);
            return byteArratInputStream.toByteArray();

        }catch (FileNotFoundException er){
            Toast.makeText(getApplication(), "No ha seleccionado una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }

}