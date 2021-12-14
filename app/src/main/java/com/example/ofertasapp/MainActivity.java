package com.example.ofertasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String JSON_URL = "http://10.0.2.2/ofertasApp/public/email/";  //url donde realizamos la petición para saber el usuario registrado
    String email;  //para obtener el email del usuario registrado
    Usuario user;  //para obtener todos los datos del usuario registrado
    String perfil;  //para obtener el perfil del usuario registrado y poder enviarlo a la siguiente actividad
    CardView logs, users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent != null){
            //obtenemos el email del usuario para saber que usuario está registrado
            email = intent.getStringExtra("Email");
        }

        logs = (CardView)findViewById(R.id.cardLogs);
        users = (CardView)findViewById(R.id.cardUser);

        String url = JSON_URL + email;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objResultado = new JSONObject(response);  //nos traemos la respuesta
                            String extadox=objResultado.get("status").toString();  //recogemos el valor de status de la respuesta

                            //comprobamos el status obtenido para saber que hacer en cada caso
                            if(!extadox.equalsIgnoreCase("200")){
                                Toast.makeText(MainActivity.this,"No se han obtenido los datos",Toast.LENGTH_LONG).show();
                            }else{
                                //obtenemos los datos del usuario registrado para saber sus ofertas y mensajes
                                JSONObject UsuarioObject = objResultado.getJSONObject("detalles");
                                user = new Usuario (UsuarioObject.getInt("id"), UsuarioObject.getString("nombre"),
                                        UsuarioObject.getString("apellido1"), UsuarioObject.getString("email"), UsuarioObject.getString("password"), UsuarioObject.getInt("activo"));
                                perfil = UsuarioObject.getString("perfil");
                                if(perfil.equals("User")){
                                    logs.setVisibility(View.GONE);
                                    users.setVisibility(View.GONE);
                                }
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void onClick(View view) {
        //según la card pulsada abriremos una actividad u otra
        switch (view.getId()){
            case R.id.imageView3:  //al pulsar el icono de la flecha volvemos a la pantalla principal
                finish();
                break;
            case R.id.cardOfertas:
                Intent i = new Intent(getApplicationContext(), Ofertas_Activity.class);
                //mandamos el perfil y el id del usuario a la actividad de ofertas
                i.putExtra("id", user.getId());
                i.putExtra("perfil", perfil);
                startActivity(i);  //si pulsamos sobre la card de ofertas abrimos la actividad Ofertas_Activity
                break;
            case R.id.cardMensajes:
                Intent it = new Intent(getApplicationContext(), Mensajes_Activity.class);
                //mandamos el id del usuario a la actividad de mensajes
                it.putExtra("id", user.getId());
                startActivity(it);  //si pulsamos sobre la card de mensajes abrimos la actividad Mensajes_Activity
                break;
            case R.id.cardLogs:
                if(perfil.equals("Admin")){
                    Intent inte = new Intent(getApplicationContext(), Logs_Activity.class);
                    //mandamos el id del usuario a la actividad de logs
                    inte.putExtra("id", user.getId());
                    startActivity(inte);  //si pulsamos sobre la card de logs abrimos la actividad Logs_Activity
                }else{
                    Toast.makeText(getApplicationContext(), "No tienes acceso a logs", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.cardUser:
                if(perfil.equals("Admin")){
                    Intent intent = new Intent(getApplicationContext(), Usuarios_Activity.class);
                    //mandamos el id del usuario a la actividad de usuarios
                    intent.putExtra("id", user.getId());
                    intent.putExtra("email", email);
                    startActivity(intent);  //si pulsamos sobre la card de logs abrimos la actividad Users_Activity
                }else{
                    Toast.makeText(getApplicationContext(), "No tienes acceso a usuarios", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}