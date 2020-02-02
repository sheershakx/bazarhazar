package com.acosaf.bazarhazar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

public class signup_form extends AppCompatActivity {

    ProgressDialog progressDialog;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    Button register_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        // getSupportActionBar().setTitle("Signup form");
        final Spinner myspinner1 = findViewById(R.id.district_option);
        //
        register_btn = (Button) findViewById(R.id.register_btn);
        //
//spinner function for province dropdown menu
        // final String Province[]={"Province 1","Province 2","Province 3","Province 4","Province 5","Province 6","Province 7"}
        final Spinner myspinner = findViewById(R.id.province_option);
        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(signup_form.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Province));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myadapter);

        //spinner function for district(on basis of province selection) dropdown menu

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String itemselected = getResources().getStringArray(R.array.Province)[position];
                if (position == 0) {


                    ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(signup_form.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.district1));
                    myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myspinner1.setAdapter(myadapter1);

                }
                if (position == 1) {
                    ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(signup_form.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.district2));
                    myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myspinner1.setAdapter(myadapter1);

                }
                if (position == 2) {
                    ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(signup_form.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.district3));
                    myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myspinner1.setAdapter(myadapter1);

                }
                if (position == 3) {
                    ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(signup_form.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.district4));
                    myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myspinner1.setAdapter(myadapter1);

                }
                if (position == 4) {
                    ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(signup_form.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.district5));
                    myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myspinner1.setAdapter(myadapter1);

                }
                if (position == 5) {
                    ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(signup_form.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.district6));
                    myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myspinner1.setAdapter(myadapter1);

                }
                if (position == 6) {
                    ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(signup_form.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.district7));
                    myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myspinner1.setAdapter(myadapter1);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("bazarhazar123");
        firebaseAuth = FirebaseAuth.getInstance();


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = progressDialog.show(signup_form.this, "", "Registering, Please wait....", true);
                //get data from spinner province
                Spinner spinner = (Spinner) findViewById(R.id.province_option);
                final String province = spinner.getSelectedItem().toString();

                //get data from spinner district
                Spinner spinner1 = (Spinner) findViewById(R.id.district_option);
                final String district = spinner1.getSelectedItem().toString();


//get data from  contact person
                EditText cperson = (EditText) findViewById(R.id.txt_cperson);
                final String contactperson = cperson.getText().toString();
//get data from email
                EditText eml = (EditText) findViewById(R.id.id_email);
                final String email = eml.getText().toString();
//get data from mobile number
                EditText mbl = (EditText) findViewById(R.id.num_phone);
                final String mobile = mbl.getText().toString();

//get data from password
                EditText pass = (EditText) findViewById(R.id.txt_password);
                final String password = pass.getText().toString();
//get data from city
                EditText cityname = (EditText) findViewById(R.id.txt_city);
                final String city = cityname.getText().toString();


                if (!TextUtils.isEmpty(contactperson) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(province) && !TextUtils.isEmpty(district) && !TextUtils.isEmpty(city)) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(signup_form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        users information = new users(
                                                email,
                                                password

                                        );
                                        FirebaseDatabase.getInstance().getReference("Registration information")
                                                .child("users")
                                                //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                //  String firebaseuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                                new register().execute(contactperson, email, mobile, province, district, city);

                                                // Toast.makeText(getApplicationContext(), "You are now registered in BazarHazar, Please Login now", Toast.LENGTH_LONG).show();
                                                // startActivity(new Intent(getApplicationContext(), login_form.class));
                                                //  finish();


                                            }
                                        });


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Sorry, Resigtration failed..", Toast.LENGTH_LONG).show();
                                    }

                                    // ...
                                }

                            });
                } else if (TextUtils.isEmpty(contactperson)) {
                    Toast.makeText(signup_form.this, "Please Enter Contact Person Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(signup_form.this, "Please Enter Email id", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(signup_form.this, "Please Enter Phone numnber", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(signup_form.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(province)) {
                    Toast.makeText(signup_form.this, "Please Select Province", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(district)) {
                    Toast.makeText(signup_form.this, "Please Select District", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(city)) {
                    Toast.makeText(signup_form.this, "Please Enter City Name", Toast.LENGTH_SHORT).show();
                }


            }

        });
    }

    public void change_screen_to_login(View view) {
        startActivity(new Intent(this, login_form.class));
        finish();
    }

    public class register extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {

            db_url = "http://test.acosaf.com/bazarhazar/register.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String fullname, email, mobile, province, district, city;
            fullname = params[0];
            email = params[1];
            mobile = params[2];
            province = params[3];
            district = params[4];
            city = params[5];


            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("fullname", "UTF-8") + "=" + URLEncoder.encode(fullname, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8") + "&" +
                        URLEncoder.encode("province", "UTF-8") + "=" + URLEncoder.encode(province, "UTF-8") + "&" +
                        URLEncoder.encode("district", "UTF-8") + "=" + URLEncoder.encode(district, "UTF-8") + "&" +
                        URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();


                return null;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "You are now registered in BazarHazar, Please Login now", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), login_form.class));
            finish();


        }
    }

}
