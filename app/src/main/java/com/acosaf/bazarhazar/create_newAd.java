package com.acosaf.bazarhazar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class create_newAd extends AppCompatActivity {
    EditText service, desc, address, remark, openinghr, closinghr, contact;
    Spinner categ;
    CheckBox _24_hr;
    Button uploadimage, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ad);

        //typecasting
        //buttons
        uploadimage = findViewById(R.id.uploadimage_create);
        save = findViewById(R.id.save_create);

        //spinner
        categ = findViewById(R.id.categ_create);

        //checkbox
        _24_hr = findViewById(R.id._24hr_create);

        //edittext
        service = findViewById(R.id.service_create);
        desc = findViewById(R.id.desc_create);
        address = findViewById(R.id.address_create);
        remark = findViewById(R.id.remark_create);
        contact = findViewById(R.id.contact_create);
        openinghr = findViewById(R.id.openinghour_create);
        closinghr = findViewById(R.id.closinghour_create);


        //setting categories in spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(create_newAd.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.category));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categ.setAdapter(adapter);

        _24_hr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    openinghr.setVisibility(View.GONE);
                    closinghr.setVisibility(View.GONE);
                } else if (isChecked == false) {
                    openinghr.setVisibility(View.VISIBLE);
                    closinghr.setVisibility(View.VISIBLE);
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category, services, description, address_, remarks, openhour, closehour, contactnum;
                category = categ.getSelectedItem().toString();
                services = service.getText().toString();
                description = desc.getText().toString();
                address_ = address.getText().toString();
                remarks = remark.getText().toString();
                openhour = openinghr.getText().toString();
                closehour = closinghr.getText().toString();
                contactnum = contact.getText().toString();
                Boolean _24checked_ = _24_hr.isChecked();

                String hours;

                if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(services) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(address_) && !TextUtils.isEmpty(contactnum)) {

                    if (_24checked_ == true) {
                        hours = "24 hours";
                        new create_ad().execute(category, services, description, address_, remarks, hours, contactnum, "image");
                    } else if (_24checked_ == false) {
                        if (openhour != null && closehour != null && !TextUtils.isEmpty(openhour) && !TextUtils.isEmpty(closehour)) {
                            hours = openhour + " - " + closehour;
                            new create_ad().execute(category, services, description, address_, remarks, hours, contactnum, "image");
                        } else
                            Toast.makeText(create_newAd.this, "Specify opening and closing hours", Toast.LENGTH_SHORT).show();
                    }


                } else
                    Toast.makeText(create_newAd.this, "Some fields are compulsory", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public class create_ad extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {

            db_url = "http://test.acosaf.com/bazarhazar/create_ad.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String category, service, desc, address, remark, hours, image, contactnum;

            category = params[0];
            service = params[1];
            desc = params[2];
            address = params[3];
            remark = params[4];
            hours = params[5];
            contactnum = params[6];
            image = params[7];


            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(category, "UTF-8") + "&" +
                        URLEncoder.encode("services", "UTF-8") + "=" + URLEncoder.encode(service, "UTF-8") + "&" +
                        URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8") + "&" +
                        URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&" +
                        URLEncoder.encode("remarks", "UTF-8") + "=" + URLEncoder.encode(remark, "UTF-8") + "&" +
                        URLEncoder.encode("hours", "UTF-8") + "=" + URLEncoder.encode(hours, "UTF-8") + "&" +
                        URLEncoder.encode("photo", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8") + "&" +
                        URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contactnum, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(login_form.date, "UTF-8") + "&" +
                        URLEncoder.encode("proname", "UTF-8") + "=" + URLEncoder.encode(login_form.name, "UTF-8") + "&" +
                        URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(login_form.userid, "UTF-8");


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
            Toast.makeText(create_newAd.this, "Ad Created", Toast.LENGTH_SHORT).show();
            finish();



        }
    }
}
