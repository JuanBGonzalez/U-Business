package com.example.juanb.ubusiness.Screens.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.juanb.ubusiness.R;
import com.example.juanb.ubusiness.models.objects.Inquilinos;
import com.example.juanb.ubusiness.models.objects.Myglobalvar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BeginACT extends AppCompatActivity   {


    private ArrayList<Inquilinos> listData;
    private FragmentManager manager;
    Button siguiente, siguienteRegistrar, siguienteAdministrador;
    EditText email,password;
    TextView estado;
    RequestQueue requestQueue;
    String URL = "http://192.168.1.124/prog5/User_Control.php";
    StringRequest request;
    String correo;
    private static final String FRAG_REGIS = "FRAG_REGIS";


    public void empezar(){
        ((Myglobalvar) this.getApplication()).setSomeVariable(correo);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_act);
        manager = getSupportFragmentManager();



        siguiente = (Button) findViewById(R.id.btentrar);
        siguienteRegistrar = (Button) findViewById(R.id.btregis);
        siguienteAdministrador = (Button) findViewById(R.id.btadmin);
        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        estado = (TextView) findViewById(R.id.txestado);
        requestQueue = Volley.newRequestQueue(this);
        estado.setText("");

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString(), pass = password.getText().toString();
                if (mail.matches("") || pass.matches("")) {
                    ToastBOOM("Te falta llenar un campo (email y password");
                } else {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "SUCCESS: " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                estado.setText(jsonObject.getString("success") + " Exitoso ", TextView.BufferType.EDITABLE);
                                correo = jsonObject.getString("success");
                                empezar();
                                email.setText("");
                                password.setText("");
                                estado.setText("  ", TextView.BufferType.EDITABLE);
                                startActivity(new Intent(getApplicationContext(), DRAWERACT.class));

                            } else {
                                Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("Error"), Toast.LENGTH_SHORT).show();
                                //estado.setText("Error en tus datos", TextView.BufferType.EDITABLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //estado.setText("Json exception" + e.getMessage(), TextView.BufferType.EDITABLE);
                            ToastBOOM("Json exception" + e.getMessage());
                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //estado.setText("Al parecer no existes, registrate haciendo click abajo", TextView.BufferType.EDITABLE);
                        ToastBOOM("Al parecer no existes, registrate haciendo click abajo");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("email", email.getText().toString());
                        hashMap.put("pass", password.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
            }

        });






        siguienteRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  siguienteRegis = new Intent(BeginACT.this, RegistrarACT.class);
                startActivity(siguienteRegis);
            }
        });

        siguienteAdministrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  siguienteAdministrador = new Intent(BeginACT.this, DRAWERACT.class);
                startActivity(siguienteAdministrador);
            }
        });
    }

    public void ToastBOOM(String texto) {
        Toast.makeText(getApplicationContext(), " "+texto+" ", Toast.LENGTH_SHORT).show();
    }


}
