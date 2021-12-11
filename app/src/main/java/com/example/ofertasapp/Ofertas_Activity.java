package com.example.ofertasapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import Utilidades.ListViewAdapterOferta;

public class Ofertas_Activity extends AppCompatActivity {

    List<Oferta> listaOferta;   //para almacenar las ofertas
    List<Integer> listaCiclos;  //guardamos los id de los ciclos en los que está registrado el usuario
    List<Object> listaCiclosOfertas;  //guardamos los id de las ofertas según los ciclos que tiene el usuario
    private static final String JSON_URL_OFERTA = "http://ofertasapp.es/ofertas/";  //url donde realizamos la petición para obtener la oferta
    private static final String JSON_URL = "http://ofertasapp.es/ofertas";  //url donde realizamos la petición para obtener todas las ofertas que están registradas
    private static final String URL = "http://ofertasapp.es/usuCiclo/";   //url donde realizamos la petición para obtener los ciclos de los usuarios
    private static final String URL_OFECI = "http://ofertasapp.es/cicloOfe/";   //url donde realizamos la petición para obtener las ofertas del ciclo
    ListView listView;  //para mostrar la lista
    int idUser; //para obtener el id del usuario registrado
    String perfilUser;   //para obtener el perfil del usuario registrado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);

        //preferencias para guardar los datos de las consultas realizadas
        SharedPreferences sharedPref = this.getSharedPreferences(
                "miSharedPreference", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor2 = sharedPref.edit();

        //obtenemos los datos de la anterior actividad
        Intent intent = getIntent();
        if(intent != null){
            //obtenemos el id del usuario para saber que usuario está registrado
            idUser = intent.getIntExtra("id", 0);
            perfilUser = intent.getStringExtra("perfil");
        }

        //asociamos layout
        listView = findViewById(R.id.listView);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        listaOferta = new ArrayList<>();
        listaCiclos = new ArrayList<>();
        listaCiclosOfertas = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);

        //comprobamos el perfil del usuario para mostrar todas las ofertas o solo las del usuario
        if(perfilUser.equals("Admin")){  //si es Admin mostraremos todas las ofertas
            //realizamos una petición por get para obtener el listado de todas las ofertas
            StringRequest stringRequest3 = new StringRequest(Request.Method.GET, JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.INVISIBLE);

                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray ofertaArray = obj.getJSONArray("detalles");

                                //recorremos el array obtenido
                                for (int i = 0; i < ofertaArray.length(); i++) {
                                    JSONObject ofertaObject = ofertaArray.getJSONObject(i);
                                    Oferta oferta = new Oferta(ofertaObject.getString("empresa"), ofertaObject.getString("informacion"));
                                    listaOferta.add(oferta); //añadimos cada oferta al listado

                                }

                                ListViewAdapterOferta adapter = new ListViewAdapterOferta(listaOferta, getApplicationContext());

                                listView.setAdapter(adapter); //mostramos la lista de las ofertas

                                //comprobamos si no hay ofertas
                                if(listaOferta.isEmpty())
                                    Toast.makeText(getApplicationContext(), "No hay Ofertas", Toast.LENGTH_LONG).show();

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

            RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());
            requestQueue3.add(stringRequest3);
        }else{
            //si el usuario es user comprobamos primero los ciclos en los cuales está registrado
            String urlCiclo = URL + idUser;

            //realizamos la peticion por get
            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlCiclo,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray ciclosArray = obj.getJSONArray("detalles"); //obtenemos los datos

                                //recorremos los datos para añadirlos en la lista de ciclos
                                for (int i = 0; i < ciclosArray.length(); i++) {
                                    JSONObject ciclo = ciclosArray.getJSONObject(i);
                                    listaCiclos.add(ciclo.getInt("CodigoCiclo"));

                                }
                                //una vez tengamos los ciclos recorremos el listado para obtener de cada ciclo las ofertas que están registradas
                                for(Integer cic : listaCiclos) {
                                    String urlCicloofer = URL_OFECI + cic;
                                    StringRequest stringRequest2 = new StringRequest(Request.Method.GET, urlCicloofer,
                                            new Response.Listener<String>() {
                                                @RequiresApi(api = Build.VERSION_CODES.N)
                                                @Override
                                                public void onResponse(String response) {

                                                    try {
                                                        JSONObject obj = new JSONObject(response);
                                                        JSONArray ciclosOfertasArray = obj.getJSONArray("detalles"); //obtenemos los datos
                                                        //recorremos los datos para añadirlos al listado de ofertas de los ciclos
                                                        for(int i = 0; i < ciclosOfertasArray.length();i++){
                                                            listaCiclosOfertas.add(ciclosOfertasArray.getInt(i));
                                                            //lo añadimos a las preferencias para poder usarlo después de la petición
                                                            editor2.putString("ofertas", listaCiclosOfertas.toString());
                                                            editor2.commit();
                                                        }

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

                                    RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue2.add(stringRequest2);
                                }


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

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

            //obtenemos los id de las ofertas guardadas en las preferencias
            String idO = sharedPref.getString("ofertas", "");
            //eliminamos los corchetes
            String idO2 = idO.replace("[", "");
            String idO3 = idO2.replace("]", "");
            //generamos un array a partir de la cadena sin corchetes para poder recorrerlo
            String[] idOArray = idO3.split(", ");
            //eliminamos los elementos duplicados del array para ello lo recorremos y comparamos los elementos que son iguales
            for(int i = 0; i<idOArray.length;i++){
                for(int j = 0; j<idOArray.length; j++){
                    if(i != j){
                        //comprobamos si son iguales
                        if(idOArray[i].equals(idOArray[j])){
                            idOArray[i] = "";  //eliminamos duplicado
                        }
                    }
                }
            }

            //recorremos el array para obtener las ofertas
            for(String id : idOArray){
                String UrlOfertas = JSON_URL_OFERTA + id;
                //realizamos la peticion por get
                StringRequest stringRequest3 = new StringRequest(Request.Method.GET, UrlOfertas,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.INVISIBLE);

                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONObject ofertaObject = obj.getJSONObject("detalles");
                                    Oferta oferta = new Oferta(ofertaObject.getString("empresa"), ofertaObject.getString("informacion"));
                                    listaOferta.add(oferta);  //añadimos la oferta a la lista

                                    ListViewAdapterOferta adapter = new ListViewAdapterOferta(listaOferta, getApplicationContext());

                                    listView.setAdapter(adapter);  //mostramos la lista

                                    if(listaOferta.isEmpty())
                                        Toast.makeText(getApplicationContext(), "No hay Ofertas", Toast.LENGTH_LONG).show();

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

                RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());
                requestQueue3.add(stringRequest3);
            }
        }

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

}