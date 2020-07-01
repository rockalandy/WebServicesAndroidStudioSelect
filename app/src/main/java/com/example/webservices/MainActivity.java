package com.example.webservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    LinearLayout ll;
    TextView[] alumnos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = findViewById(R.id.LytContenedor);
        buscarDatos("http://192.168.0.6/webservices/buscar_alumnos.php");
    }
    private void buscarDatos(String URL){
        JsonArrayRequest jsr = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jso = null;
                for (int i= 0; i<response.length();i++){
                    try{
                        TextView[] alumnos = new TextView[response.length()];
                        alumnos[i] = new TextView(getApplicationContext());
                        jso = response.getJSONObject(i);
                        String id = jso.getString("id");
                        String nombre = jso.getString("nombre");
                        String semestre = jso.getString("semestre");
                        String carrera = jso.getString("carrera");
                        String dato = "Matricula: "+id+" nombre: "+nombre+" semestre: "+semestre+" carrera: "+carrera;
                        alumnos[i].setText(dato);
                        ll.addView(alumnos[i]);
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq = Volley.newRequestQueue(this);
            rq.add(jsr);

    }
}