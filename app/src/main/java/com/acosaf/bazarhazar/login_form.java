package com.acosaf.bazarhazar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

public class login_form extends AppCompatActivity {
    Button btn_login;
    EditText email, password;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    public static String userid;
    public static String name;
    public static String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        // getSupportActionBar().setTitle("Login Form");
        //
        btn_login = (Button) findViewById(R.id.button_login);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        //getting current date
         date = (String) android.text.format.DateFormat.
                format("yyyy-MM-dd", Calendar.getInstance().getTime());


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = (EditText) findViewById(R.id.login_mob);
                final String emailid = email.getText().toString();

                password = (EditText) findViewById(R.id.login_pass);
                String passid = password.getText().toString();

                if (TextUtils.isEmpty(emailid)) {
                    Toast.makeText(login_form.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passid)) {
                    Toast.makeText(login_form.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(emailid, passid)
                        .addOnCompleteListener(login_form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    new login().execute(emailid);

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


    public class login extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {

            db_url = "http://test.acosaf.com/bazarhazar/login.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String email;
            email = params[0];

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuffer buffer = new StringBuffer();
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String data = stringBuilder.toString().trim();

                JSONObject jsonObject = new JSONObject(data);
                userid = jsonObject.getString("userid");
                name = jsonObject.getString("fullname");


                return null;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(login_form.this, "Login Succesful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), user_dashboard.class));
            finish();


        }
    }

}

