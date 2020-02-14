package com.acosaf.bazarhazar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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

public class ads_detail extends AppCompatActivity {
    TextView categ, address, date, desc, proname, remarks, services, contact, hours;
    String Categ, Address, Date, Desc, Proname, Remarks, Services, Contact, Hours, Image;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_detail_layout);

        //typecasting_textviews
        categ = findViewById(R.id.categ_detail);
        address = findViewById(R.id.address_detail);
        date = findViewById(R.id.date_detail);
        desc = findViewById(R.id.desc_detail);
        proname = findViewById(R.id.proname_detail);
        remarks = findViewById(R.id.remark_detail);
        services = findViewById(R.id.serv_detail);
        contact = findViewById(R.id.contact_detail);
        hours = findViewById(R.id.hours_detail);

        imageView = findViewById(R.id.image_detail);


        getintent();
    }

    private void getintent() {
        Intent intent = getIntent();
        String postid = intent.getStringExtra("postid");
        new get_ad_details().execute(postid);
    }


    public class get_ad_details extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {

            db_url = "http://test.acosaf.com/bazarhazar/get_ad_details.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String postid;
            postid = params[0];

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(postid, "UTF-8");

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
                Categ = jsonObject.getString("category");
                Address = jsonObject.getString("address");
                Date = jsonObject.getString("date");
                Desc = jsonObject.getString("description");
                Proname = jsonObject.getString("proname");
                Remarks = jsonObject.getString("remarks");
                Services = jsonObject.getString("services");
                Contact = jsonObject.getString("contact");
                Hours = jsonObject.getString("hours");
                Image = jsonObject.getString("image");


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
            categ.setText(Categ);
            address.setText(Address);
            date.setText(Date);
            desc.setText(Desc);
            proname.setText(Proname);
            services.setText(Services);
            contact.setText(Contact);
            hours.setText(Hours);

            if (Remarks != null) remarks.setText(Remarks);
            Picasso.get().load(Image).into(imageView);

        }
    }
}