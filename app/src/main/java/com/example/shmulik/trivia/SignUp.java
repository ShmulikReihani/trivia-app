package com.example.shmulik.trivia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class SignUp extends AppCompatActivity {


    EditText etEmail,etName,etPassword;
    Button btnSignUp;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

//    SharedPreferences prefs;
////    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.password);
        btnSignUp = (Button) findViewById(R.id.btnRegister);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

//        gson = new Gson();
//        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            // handle the already login
        }
    }

    public void register()
    {
        final String email = etEmail.getText().toString();
        final String pass = etPassword.getText().toString();
        final String name = etName.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUp.this,"u sign up!",Toast.LENGTH_LONG).show();
                    String id ;
                    id = mAuth.getCurrentUser().getUid();
                    Users users = new Users(name,email,pass);

//                    String json = gson.toJson(users);
//                    SharedPreferences.Editor editoJ = prefs.edit();
//                    editoJ.putString("currentUser",json);
//                    editoJ.apply();

                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.child("users").child(id).setValue(users);

                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SignUp.this,"there is a problem!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
