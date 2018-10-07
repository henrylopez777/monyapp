package com.example.henrylopez.monyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


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
}