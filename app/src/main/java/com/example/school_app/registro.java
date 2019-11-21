package com.example.school_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class registro extends AppCompatActivity  implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {
    //barra de progreso
    ProgressDialog progreso;

    //importante
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    //objetos
    EditText te_1,te_2,te_3,te_4;
    Button btn,btn1;
    String t_1,t_2,t_3,t_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //instanciamos
        te_1 = findViewById(R.id.c_Nombre);
        te_2 = findViewById(R.id.c_correo);
        te_3 = findViewById(R.id.c_contraseña);
        te_4 = findViewById(R.id.contraseña_repetir);
        t_1=te_1.getText().toString();
        t_2=te_2.getText().toString();
        t_3=te_3.getText().toString();
        t_4=te_4.getText().toString();
        btn = findViewById(R.id.btn_registro);
        btn1 = findViewById(R.id.btnlogin);
        request = Volley.newRequestQueue(this);

        btn1.setOnClickListener(this);
        btn.setOnClickListener(this);

    }
    //
    private void cargarWebService() {

        //barra de dialogo
        progreso=new ProgressDialog(this);
        progreso.setMessage("cargando....");
        progreso.show();
        //barra de dialogo
        String URL="http://puntosingular.mx/school_app/registro.php?nombre="+te_1.getText().toString()+"&correo="+te_2.getText().toString()+"&contrasena="+te_3.getText().toString();



        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        request.add(jsonObjectRequest);
        Intent x = new Intent(this,inicio_session.class);
        startActivity(x);
    }

    @Override
    public void onClick(View v) {



        if(v==btn){

            String a1,a2;
            a1=te_3.getText().toString();
            a2=te_4.getText().toString();
            if(t_1.equals(te_1.getText().toString()) && t_2.equals(te_2.getText().toString()) && t_3.equals(te_3.getText().toString()) && t_4.equals(te_4.getText().toString()) ){
                //barra de dialogo
                progreso=new ProgressDialog(this);
                progreso.setMessage("cargando....");
                progreso.show();



                //barra de dialogo
                te_1.setError("Campos Obligatorios");
                te_2.setError("Campos Obligatorios");
                te_3.setError("Campos Obligatorios");
                te_4.setError("Campos Obligatorios");
                te_1.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                te_2.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                te_3.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                te_4.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(this,"Error campos obligatorios",Toast.LENGTH_SHORT).show();
            }
            else if(t_1.equals(te_1.getText().toString()) ){
                te_1.setError("Campos Obligatorios");
                te_1.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(this,"Error campos obligatorios",Toast.LENGTH_SHORT).show();
            }
            else if(t_2.equals(te_2.getText().toString()) && t_3.equals(te_3.getText().toString()) && t_4.equals(te_4.getText().toString()) ){
                te_2.setError("Campos Obligatorios");
                te_3.setError("Campos Obligatorios");
                te_4.setError("Campos Obligatorios");
                te_2.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                te_3.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                te_4.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(this,"Error campos obligatorios",Toast.LENGTH_SHORT).show();
            }
            else if(t_3.equals(te_3.getText().toString()) && t_4.equals(te_4.getText().toString()) ){
                te_3.setError("Campos Obligatorios");
                te_4.setError("Campos Obligatorios");
                te_3.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                te_4.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(this,"Error campos obligatorios",Toast.LENGTH_SHORT).show();
            }else if(t_4.equals(te_4.getText().toString()) ){

                te_4.setError("Campos Obligatorios");

                te_4.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(this,"Error campos obligatorios",Toast.LENGTH_SHORT).show();
            }

            else{
                if(a1.equals(a2)){
                    cargarWebService();
                }
                else{
                    te_4.setError("La contraseña no es igual");
                    te_4.setText("");
                    te_4.getBackground().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(this,"No se pudo registrar",Toast.LENGTH_SHORT).show();
                }
            }}
        if(v==btn1){
            Intent x = new Intent(this,inicio_session.class);
            startActivity(x);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this,"No se pudo registrar"+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("Error",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Se a resgistrado correctamente",Toast.LENGTH_SHORT).show();
        progreso.hide();
        te_1.setText("");
        te_2.setText("");
        te_3.setText("");
        te_4.setText("");

    }
}
