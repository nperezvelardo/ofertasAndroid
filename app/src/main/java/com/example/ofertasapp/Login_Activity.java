package com.example.ofertasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class Login_Activity extends AppCompatActivity {

    Button btnAcceder;  //definimos el botón para acceder a la aplicación
    EditText usuario, password;  //definimos dos edittext para el usuario y la contrasña
    CheckBox recordar;  //definimos un checkbox para recordar usuario y contraseña
    private static final String JSON_URL = "http://ofertasapp.es/login";  //url donde realizaremos la petición para loguearnos
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //asociamos layout
        btnAcceder = (Button)findViewById(R.id.buttonAcceder);
        usuario = (EditText)findViewById(R.id.editTextTextPersonName);
        password = (EditText)findViewById(R.id.editTextTextPassword);
        recordar = (CheckBox)findViewById(R.id.checkBox);


        SharedPreferences sharedPref = this.getSharedPreferences(
                "miSharedPreference", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        //comprobamos si existen preferencias guardadas anteriormente
        String emailtext = sharedPref.getString("usuario", "");
        String pass = sharedPref.getString("password", "");
        boolean stateSwitch = sharedPref.getBoolean("checked", false);

        //asignamos esos valores de las preferencias a los componentes de la vista
        recordar.setChecked(stateSwitch);
        usuario.setText(emailtext);
        password.setText(pass);

        //Abrimos actividad menu si el usuario es correcto
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String user = usuario.getText().toString();
                final String pass = password.getText().toString();

                //comprobamos si existen datos en los editText
                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_LONG).show();  //mostramos el mensaje de que no existen datos
                } else {
                    //si hemos pulsado recordar guardamos los datos en preferencias
                    if (recordar.isChecked()) {
                        editor.putString("usuario", usuario.getText().toString());
                        editor.putString("password", password.getText().toString());
                        editor.putBoolean("checked", recordar.isChecked());
                        editor.commit();
                    } else {
                        //quitamos los datos de las preferencias si no está pulsado
                        editor.putString("usuario", "");
                        editor.putString("password", "");
                        editor.putBoolean("checked", recordar.isChecked());
                        editor.commit();
                    }

                    //llamamos al método para iniciar sesión
                    iniciarSesion();

                }
            }

        });
    }

    /**
     * Método que realiza una petición por post a la url anteriormente definida y comprueba si el usuario existe y está activo
     */
    public void iniciarSesion(){
        RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objResultado = new JSONObject(response); //nos traemos la respuesta
                            String extadox=objResultado.get("status").toString();  //recogemos el valor de status de la respuesta

                            //comprobamos si la respuesta es distinta a 200, porque solo entra en la aplicación cuando tiene respuesta 200
                            if(!extadox.equalsIgnoreCase("200")){
                                Toast.makeText(Login_Activity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_LONG).show();
                            }else{
                                //entramos en la pantalla de menú
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("Email", usuario.getText().toString() );
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("email", usuario.getText().toString());  //recogemos el usuario para enviarlo por post
                params.put("password",password.getText().toString());  //recogemos la contraseña para enviarla por post
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

