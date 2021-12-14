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

import Utilidades.ListViewAdapterLog;
import Utilidades.ListViewAdapterUsuario;

public class Users_Activity extends AppCompatActivity {

    List<Usuario> listaUser = new ArrayList<>();  //para guardar el listado de usuarios
    private static final String JSON_URL = "http://ofertasapp.es/usuarios";  //url donde realizamos la petición para obtener los usuarios
    ListView listView;  //para cargar la lista de usuarios
    int idUser;  //para obtener de las preferencias el id del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Intent intent = getIntent();
        if(intent != null){
            //obtenemos el id del usuario para saber que usuario está registrado
            idUser = intent.getIntExtra("id", 0);
        }

        //asociamos layout
        listView = findViewById(R.id.listView4);

        //llamamos al método
        loadUserList();
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

    public void loadUserList(){
        final ProgressBar progressBar = findViewById(R.id.progressBar4);

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);

                        try {

                            JSONObject obj = new JSONObject(response);  //nos traemos la respuesta
                            JSONArray userArray = obj.getJSONArray("detalles");  //recogemos el array detalles de la respuesta

                            //recorremos el array. Obtenemos un usuario en cada iteración
                            for (int i = 0; i < userArray.length(); i++) {
                                JSONObject userObject = userArray.getJSONObject(i);
                                Usuario user = new Usuario(userObject.getInt("id"), userObject.getString("nombre"), userObject.getString("apellido1"), userObject.getString("email"), userObject.getString("password"), userObject.getInt("activo"));

                                //lo añadimos a la lista
                                listaUser.add(user);

                            }

                            ListViewAdapterUsuario adapter = new ListViewAdapterUsuario(listaUser, getApplicationContext());

                            listView.setAdapter(adapter);        //mostramos la lista de mensajes en la lista del layout

                            //comprobamos si la lista está vacía
                            if(listaUser.isEmpty())
                                Toast.makeText(getApplicationContext(), "No hay usuarios", Toast.LENGTH_LONG).show();

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