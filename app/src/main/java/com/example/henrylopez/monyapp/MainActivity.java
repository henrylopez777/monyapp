package com.example.henrylopez.monyapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.example.henrylopez.monyapp.Metodos.mMovimientos;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    //RecyclerView
    private RecyclerView rvMovimientos;
    //Referencia de Base de datos
    private DatabaseReference mDatabase;

    public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        //DEFINIR TOOLBAR
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTheme(R.style.AppTheme1);
        //DECLARACION DE VARIABLES PARA MOSTRAR FECHA
        String mess="", diaa="";
        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR); //obtenemos el año
        int mes = c.get(Calendar.MONTH); //obtenemos el mes
        int dias=c.get(Calendar.DAY_OF_WEEK);
        diaa=cambiar_dia(dias);

        //Los meses se presentan del 0 al 11, representando el cero a Enero y el once a diciembre, por lo cual para su presentación
        //sumaremos 1 a la variable entera MES.
        mes = mes + 1;
        //SOLICITA EL STRING DEL MES
        mess=cambiar_nombre(mes);

        int dia = c.get(Calendar.DAY_OF_MONTH); // obtemos el día.
        getSupportActionBar().setTitle(diaa + ", " + dia + " de " + mess + " del " + anio);

        //REVISAR ESTA LINEA
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.menu_fab);

        //SOLICITAR LA VIEW DEL ELEMENTO
        View entrada,salida;
        //ESTABLECER EL ELEMENTO A LA VARIABLE PARA EL CLIC
        entrada = findViewById(R.id.fbEntrada);
        salida= findViewById(R.id.fbSalida);
        //ESTABLECER EL CONTEXTO PARA EL CLICK
        entrada.setOnClickListener(this);
        salida.setOnClickListener(this);

        //CLICK ANTES DE USAR VARIOS ELEMENTOS
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent=new Intent(getApplicationContext(),splashscreen.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),"Bienvenido " +  user.getEmail(),Toast.LENGTH_LONG).show();
        }
        llenar_movimientos();
    }

    private String cambiar_dia(int dia) {
        String mesString="";
        switch (dia) {
            case 2:  mesString = "Lunes";
                break;
            case 3:  mesString  = "Martes";
                break;
            case 4:  mesString = "Miercoles";
                break;
            case 5:  mesString = "Jueves";
                break;
            case 6:  mesString = "Viernes";
                break;
            case 7:  mesString = "Sabado";
                break;
            case 1:  mesString = "Domingo";
                break;
        }
        return mesString;
    }

    public String cambiar_nombre(int mes){
        String mesString;
        switch (mes) {
            case 1:  mesString = "Enero";
                break;
            case 2:  mesString  = "Febrero";
                break;
            case 3:  mesString = "Marzo";
                break;
            case 4:  mesString = "Abril";
                break;
            case 5:  mesString = "Mayo";
                break;
            case 6:  mesString = "Junio";
                break;
            case 7:  mesString = "Julio";
                break;
            case 8:  mesString = "Agosto";
                break;
            case 9:  mesString = "Septiembre";
                break;
            case 10: mesString = "Octubre";
                break;
            case 11: mesString = "Noviembre";
                break;
            case 12: mesString = "Diciembre";
                break;
            default: mesString = "Invalid month";
                break;
        }
        return mesString;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fbEntrada:
                Log.i("hola","entrada");
                /*Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT);*/
                Intent intent = new Intent(getApplicationContext(),transactions.class);
                intent.putExtra("selection","Entrada");
                startActivity(intent);
                break;
            case R.id.fbSalida:
                Log.i("hola","salida");
                Intent intent1 = new Intent(getApplicationContext(),transactions.class);
                intent1.putExtra("selection","Salida");
                startActivity(intent1);
                break;
        }
    }

    public void llenar_movimientos(){

        //CONEXION FIREBASE CONSULTA Y MOSTRAR DATOS
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query filtro=mDatabase.child(String.valueOf(user.getUid()));

        //Los siguientes filtros son para devolver un unico valor será usado a la hora de traer restaurantes
        //Query filtro=mDatabase.child("Preferences").orderByKey().equalTo("100");
        //Query filtro=mDatabase.child("Preferences").orderByChild("Desc").equalTo("Snacks");
        mDatabase.keepSynced(true);


        //Declarar RecyclerView y asignarlo a una variable
        rvMovimientos= (RecyclerView)findViewById(R.id.rvMoviments);
        //Habilitar que RecyclerView para que se modifiqué segun el contenido del adaptador
        rvMovimientos.setHasFixedSize(true);
        rvMovimientos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //ADAPTADOR FIREBASE PARA POSTERIORMENTE ASIGNARLO A RECYCLERVIEW
        FirebaseRecyclerAdapter<mMovimientos,mMovimientosViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<mMovimientos, mMovimientosViewHolder>
                        (mMovimientos.class,R.layout.movimientos_styles,mMovimientosViewHolder.class,filtro) {
                    //mPreferences.class(Constructor), ASIGNAR LAS PREFERENCIAS DE COMO SERÁ DESPLEGADO, CLASE ASIGNADORA DE EVENTOS, BaseDatos
                    @Override
                    protected void populateViewHolder(mMovimientosViewHolder viewHolder, mMovimientos model, int position) {
                       viewHolder.setType(model.getType());
                        viewHolder.setKey(model.getKey());
                       viewHolder.setTitle(model.getTitle());
                       viewHolder.setDetail(model.getDetail());
                        viewHolder.setCant(model.getCant());

                        /* viewHolder.setImage(getContext().getApplicationContext(),model.getImage());
                        viewHolder.setDesc(model.getDesc());*/
                        //viewHolder.setOnClickListeners();
                    }

                    @Override
                    public void onBindViewHolder(mMovimientosViewHolder viewHolder, int position) {
                        super.onBindViewHolder(viewHolder, position);
                        //viewHolder.setOnClickListeners();
                    }
                };
        //ASIGNARTODO AL RECYCLER VIEW
        rvMovimientos.setAdapter(firebaseRecyclerAdapter);

    }

    public static class mMovimientosViewHolder extends RecyclerView.ViewHolder{
        View mView;
        Context context;

        public mMovimientosViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            context=itemView.getContext();
        }

        public void setType(String Type){
            TextView type=(TextView)mView.findViewById(R.id.tvType);
            type.setText(Type);
            if(Type.equals("Entrada")){
                type.setBackgroundColor(Color.rgb(97,136,51));
            }
            if(Type.equals("Salida")){
                type.setBackgroundColor(Color.rgb(216,27,96));
            }
        }

        public void setTitle(String title){
            TextView Title=(TextView)mView.findViewById(R.id.tvTitle);
            Title.setText(title);
        }

        public void setDetail(String Detail){
            TextView detail=(TextView)mView.findViewById(R.id.tvDetail);
            detail.setText(Detail);
        }
        //TODOS LOS SETTERS PARA EL RECYLCER
        public void setCant(String Cant){
            TextView cant=(TextView)mView.findViewById(R.id.tvCant);
            cant.setText(Cant);
        }

        public void setKey(String  Key){
            TextView key=(TextView)mView.findViewById(R.id.tvKey);
            key.setText(Key);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}