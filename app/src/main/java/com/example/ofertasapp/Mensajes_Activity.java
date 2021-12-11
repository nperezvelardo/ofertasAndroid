package com.example.ofertasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Utilidades.ListViewAdapterMensaje;

public class Mensajes_Activity extends AppCompatActivity {

    List<Mensaje> listaMensaje = new ArrayList<>();  //para guardar el listado de mensajes
    private static final String JSON_URL = "http://ofertasapp.es/mensajes";  //url donde realizamos la petición para obtener los mensajes
    ListView listView;  //para cargar la lista de mensajes
    int idUser;  //para obtener de las preferencias el id del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        Intent intent = getIntent();
        if(intent != null){
            //obtenemos el id del usuario para saber que usuario está registrado
            idUser = intent.getIntExtra("id", 0);
        }

        //asociamos layout
        listView = findViewById(R.id.listView2);

        //llamamos al método
        loadMensajeList();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIcoAtras:  //al pulsar el icono de la flecha volvemos a la pantalla principal
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    /**
     * Método que realiza una petición por get para obtener el listado de mensajes. De ese listado solo mostraremos en la lista
     * los mensajes cuyo destinatario sea el id del usuario registrado
     */
    private void loadMensajeList() {

        final ProgressBar progressBar = findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);

                        try {
                            JSONObject obj = new JSONObject(response);  //nos traemos la respuesta
                            JSONArray mensajeArray = obj.getJSONArray("detalles");  //recogemos el array detalles de la respuesta

                            //recorremos el array. Obtenemos un mensaje en cada iteración
                            for (int i = 0; i < mensajeArray.length(); i++) {
                                JSONObject mensajeObject = mensajeArray.getJSONObject(i);
                                Mensaje mensaje = new Mensaje(mensajeObject.getString("titulo"), mensajeObject.getString("contenido"));
                                //comprobamos que ese mensaje obtenido tenga de destinatario el id del usuario registrado
                                if(mensajeObject.getInt("destino") == idUser){
                                    //si es correcto lo añadimos a la lista
                                    listaMensaje.add(mensaje);
                                }

                            }

                            ListViewAdapterMensaje adapter = new ListViewAdapterMensaje(listaMensaje, getApplicationContext());

                            listView.setAdapter(adapter);        //mostramos la lista de mensajes en la lista del layout

                            //comprobamos si la lista está vacía
                            if(listaMensaje.isEmpty())
                                Toast.makeText(getApplicationContext(), "No hay Mensajes", Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}