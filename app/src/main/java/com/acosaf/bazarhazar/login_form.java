package com.acosaf.bazarhazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_form extends AppCompatActivity {
    Button btn_login;
    EditText email,password;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
       // getSupportActionBar().setTitle("Login Form");
        //
        btn_login=(Button)findViewById(R.id.button_login);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=(EditText)findViewById(R.id.login_mob);
                String emailid=email.getText().toString();

                password=(EditText)findViewById(R.id.login_pass);
                String passid=password.getText().toString();

                if(TextUtils.isEmpty(emailid)){
                    Toast.makeText(login_form.this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passid)){
                    Toast.makeText(login_form.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(emailid, passid)
                        .addOnCompleteListener(login_form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(login_form.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),user_dashboard.class));
                                    finish();


                                } else {
                                    Toast.makeText(login_form.this, "Login Failed or User not found", Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });
            }
        });




    }

    public void change_screen(View view) {
        startActivity(new Intent(this, signup_form.class));
        finish();
    }




    }

