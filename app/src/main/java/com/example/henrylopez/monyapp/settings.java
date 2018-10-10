package com.example.henrylopez.monyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class settings extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnCerrar;
    TextView Correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Configuración");
        mAuth = FirebaseAuth.getInstance();

        btnCerrar=findViewById(R.id.btnCerrarSesion);
        Correo=findViewById(R.id.tvUserEmail);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrar_sesion();
                revisar_sesion();
            }
        });
        asignar_datos();
    }

    @Override
    protected void onStart() {
        super.onStart();
        revisar_sesion();
    }

    public void revisar_sesion()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent=new Intent(getApplicationContext(),splashscreen.class);
            startActivity(intent);
            finish();
        }
    }

    public void asignar_datos(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Correo.setText(user.getEmail());
        }
    }

    public void cerrar_sesion(){
        FirebaseAuth.getInstance().signOut();
    }

}
