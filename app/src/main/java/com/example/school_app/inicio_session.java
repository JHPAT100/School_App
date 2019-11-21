package com.example.school_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.assist.AssistStructure;
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
import com.example.school_app.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class inicio_session extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener{
    //inicializando
    public static String t_1,t_2;
    EditText te_1,te_2;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Button b_1,b_2;
    ProgressDialog progreso;
    public static  String c_user;
    //inicializando
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_session);


        te_1=findViewById(R.id.correo);
        te_2=findViewById(R.id.contraseña);
        b_1=findViewById(R.id.btn_login);
        b_2=findViewById(R.id.btn_registro);
        request= Volley.newRequestQueue(this);
        b_1.setOnClickListener(this);
        b_2.setOnClickListener(this);
    }

    //
    private void cargarWebService() {
        //barra de dialogo

        progreso=new ProgressDialog(this);
        progreso.setMessage("Consultando...");
        progreso.show();
        //barra de dialogo



        String URL="http://puntosingular.mx/school_app/login.php?correo="+te_1.getText().toString();
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"No se pudo consultar"+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("Error",error.toString());
    }


    @Override
    public void onClick(View v) {
        if(v==b_1){
            cargarWebService();
        }
        if(v==b_2){
            Intent x = new Intent(this,registro.class);
            startActivity(x);
        }

    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Usuario user=new Usuario();
        JSONArray json= response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);

            user.setNombre(jsonObject.optString("nombre"));
            user.setCorreo(jsonObject.optString("correo"));
            user.setContrasena(jsonObject.optString("contrasena"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        String com=te_1.getText().toString();
        String com_2=te_2.getText().toString();
        if (com.equals(user.getCorreo()) && com_2.equals(user.getContrasena())){
            c_user=te_1.getText().toString();
            t_1=te_1.getText().toString();
            t_2=te_2.getText().toString();
            te_1.setText("");
            te_2.setText("");

            Intent x = new Intent(this,MainActivity.class);
            startActivity(x);
        }else{
            te_1.setError("Datos incorrectos");
            te_2.setError("Datos incorrectos");
            te_1.setText("");
            te_2.setText("");
            te_1.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            te_2.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(this,"No se pudo ingresar",Toast.LENGTH_SHORT).show();
        }
        //
    }
}
