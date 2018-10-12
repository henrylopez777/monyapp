package com.example.henrylopez.monyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class transactions extends AppCompatActivity {

    RadioButton rbEntrada, rbSalida;
    Button btnCancel, btnSave;
    EditText etTitle, etCant, etDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        String selection = getIntent().getStringExtra("selection");

        rbEntrada=findViewById(R.id.rbInput);
        rbSalida=findViewById(R.id.rbOutput);

        btnCancel=findViewById(R.id.btnCancel);
        btnSave=findViewById(R.id.btnSave);

        etTitle=findViewById(R.id.etTitle);
        etCant=findViewById(R.id.etCant);
        etDetail=findViewById(R.id.etDetail);

        if(selection.equals("Entrada")){
            rbEntrada.setChecked(true);
            rbSalida.setChecked(false);
            rbSalida.setVisibility(View.INVISIBLE);
            setTitle("Nueva Entrada");
        }else if (selection.equals("Salida")){
            rbEntrada.setChecked(false);
            rbSalida.setVisibility(View.VISIBLE);
            rbEntrada.setVisibility(View.INVISIBLE);
            rbSalida.setChecked(true);
            setTitle("Nueva Salida");
        }

        rbEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbEntrada.setChecked(true);
                rbSalida.setChecked(false);
            }
        });

        rbSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbEntrada.setChecked(false);
                rbSalida.setChecked(true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result=validar_campos();
                if(result==1){
                    guardar_datos();
                    Toast.makeText(getApplicationContext(),"Datos Guardados Correctamente",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    private int validar_campos(){
        int a;
        if(etTitle.getText().toString().isEmpty()){
            etTitle.setError("Favor de introducir el título");
        }
        if(etCant.getText().toString().isEmpty()){
            etCant.setError("Favor de introducir la cantidad");
        }
        if(etDetail.getText().toString().isEmpty()){
            etDetail.setError("Favor de introducir el detalle de la transacción");
        }
        if(etDetail.getText().toString().isEmpty() || etCant.getText().toString().isEmpty() || etTitle.getText().toString().isEmpty())
        {
            a=0;
        }
        else{
            a=1;
        }

        return a;
    }

    private void guardar_datos(){
        /*String type="";
        DatabaseReference dbRef =
                FirebaseDatabase.getInstance().getReference()
                        .child("Movimientos");

        Map<String, String> Transaction = new HashMap<>();
        if(rbSalida.isChecked()) {
            type="Salida";
        }else if(rbEntrada.isChecked()){
            type="Entrada";
        }
        Transaction.put("Type", type);
        Transaction.put("Title", etTitle.getText().toString());
        Transaction.put("Cant", etCant.getText().toString());
        Transaction.put("Detail", etDetail.getText().toString());
        dbRef.push().setValue(Transaction);*/
        String type="",key="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference dbRef =
                FirebaseDatabase.getInstance().getReference()
                        .child(String.valueOf(user.getUid()));

        Map<String, String> Transaction = new HashMap<>();
        if(rbSalida.isChecked()) {
            type="Salida";
        }else if(rbEntrada.isChecked()){
            type="Entrada";
        }
        Transaction.put("Type", type);
        Transaction.put("Title", etTitle.getText().toString());
        Transaction.put("Cant", etCant.getText().toString());
        Transaction.put("Detail", etDetail.getText().toString());
        key=dbRef.push().getKey();
        Transaction.put("Key",key);
        dbRef.child(key).setValue(Transaction);
    }

    private void actualizar_datos(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef =
                FirebaseDatabase.getInstance().getReference()
                        .child(user.getUid());
        //dbRef.child("-LOCHReGyE0wDcPWLQgu");
        String type="",key="-LOCHReGyE0wDcPWLQgu";
        Map<String, String> Transaction = new HashMap<>();
        if(rbSalida.isChecked()) {
            type="Salida";
        }else if(rbEntrada.isChecked()){
            type="Entrada";
        }
        Transaction.put("Type", type);
        Transaction.put("Title", etTitle.getText().toString());
        Transaction.put("Cant", etCant.getText().toString());
        Transaction.put("Detail", etDetail.getText().toString());
        Transaction.put("Key",key);
        dbRef.child(key).setValue(Transaction);
    }

}

