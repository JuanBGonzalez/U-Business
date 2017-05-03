package com.example.juanb.ubusiness.Screens.Activities;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class RegistrarACT extends AppCompatActivity {


    EditText nombre, apellido, email, pass, residencia, edad;
    Button insert, show;
    RequestQueue requestQueue;
    //String insertUrl = "http://192.168.1.100/prog5/Registration.php";
    String insertUrl = "http://192.168.1.124/prog5/Registration.php";
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_act);

        nombre = (EditText) findViewById(R.id.editText3);
        apellido = (EditText) findViewById(R.id.editText4);
        email = (EditText) findViewById(R.id.editText5);
        pass = (EditText) findViewById(R.id.editText6);
        residencia = (EditText) findViewById(R.id.editText7);
        edad = (EditText) findViewById(R.id.editText8);
        insert = (Button) findViewById(R.id.buttonA);
        result = (TextView) findViewById(R.id.textView9);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String nomb = nombre.getText().toString(), ape = apellido.getText().toString(), mail =email.getText().toString(), password = pass.getText().toString(), resid = residencia.getText().toString(), age =edad.getText().toString() ;
                    if (nomb.matches("")  || ape.matches("") || mail.matches("") || password.matches("") || resid.matches("") || age.matches("")) {
                        ToastBOOM("Quedan campos vacios");
                    }else {
                        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    result.setText("Exitoso" + email, TextView.BufferType.EDITABLE);
                                    System.out.println(response.toString());
                                    Toast.makeText(getApplicationContext(), "Exitoso", Toast.LENGTH_SHORT).show();
                                    Intent siguiente = new Intent(RegistrarACT.this, BeginACT.class);
                                    startActivity(siguiente);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ToastBOOM("Fallo la respuesta");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.getMessage());
                                //result.setText("Error " + email + " ", TextView.BufferType.EDITABLE);
                                ToastBOOM("Error de respuesta con el servidor");
                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                parameters.put("email", email.getText().toString());
                                parameters.put("nombre", nombre.getText().toString());
                                parameters.put("apellido", apellido.getText().toString());
                                parameters.put("pass", pass.getText().toString());
                                parameters.put("residencia", residencia.getText().toString());
                                parameters.put("edad", edad.getText().toString());

                                return parameters;
                            }
                        };
                        requestQueue.add(request);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastBOOM("Error sin coneccion Registrar");
                }

            }



        });

    }


    public void ToastBOOM(String texto) {
        Toast.makeText(getApplicationContext(), " "+texto+" ", Toast.LENGTH_SHORT).show();
    }
}
