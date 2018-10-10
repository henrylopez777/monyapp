package com.example.henrylopez.monyapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class login_user extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView email, password;
    Button iniciar_sesion, registrar_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();*/

        setContentView(R.layout.activity_login_user);
        mAuth = FirebaseAuth.getInstance();

        email=(TextView)findViewById(R.id.etEmail);
        password=(TextView)findViewById(R.id.etPassword);

        iniciar_sesion=findViewById(R.id.btnIniciar);
        registrar_usuario=findViewById(R.id.btnRegistrar);

        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()==true){
                    login_user(email.getText().toString(),password.getText().toString());
                }
                else{
                    validar();
                }
            }
        });

        registrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()==true){
                    signin_user(email.getText().toString(),password.getText().toString());
                }
            }
        });

    }

    private boolean validar(){
        boolean estado=false;
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(email.getText().toString().isEmpty() || pattern.matcher(email.getText().toString()).matches()==false){
            email.setError("Favor de verificar su correo");
            estado=false;
        }
        if(password.getText().toString().isEmpty() || password.getText().length()<6){
            password.setError("Verifique Contraseña Mínimo 6 Dígitos");
            estado=false;
        }
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
        {
            estado=false;
        }
        if(email.getText().toString().isEmpty() ==false && password.getText().toString().isEmpty()==false && password.getText().length()>=6 && pattern.matcher(email.getText().toString()).matches()==true){
            estado=true;
        }
        return estado;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }

    public void signin_user(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Creado", "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Usuario Registrado Correctamente. \n Ahora Inicie Sesion",
                                    Toast.LENGTH_LONG).show();
                            /*if(validar()){
                                login_user(email,password);
                            }*/
                            /*FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ErrCreado", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "1. Error al registrar usuario.\n2. Usuario Existente.\n3. Intente de nuevo o Inicie sesión\n4. REVISA TU CONEXION A INTERNET",
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });

    }

    public void login_user(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.i("LogSus", "signInWithEmail:success");
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            /*FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LogFil", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "1. Favor de verificar usuario y/o contraseña. \n2. Registrate Ahora\n3. REVISA TU CONEXION A INTERNET",
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }
}
