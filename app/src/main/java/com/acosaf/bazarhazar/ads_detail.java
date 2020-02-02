package com.acosaf.bazarhazar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ads_detail extends AppCompatActivity {
    TextView adtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_detail_layout);

        adtype=findViewById(R.id.categ_detail);
        
        getintent();
    }

    private void getintent() {
        Intent intent=getIntent();
        String string=intent.getStringExtra("adtype");
        adtype.setText(string);
    }
}