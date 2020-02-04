package com.acosaf.bazarhazar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ads_detail_admin extends AppCompatActivity {
    ImageView imageView;
    TextView adid,date,username,category,services,desc,address,remarks,status;
    EditText comment;
    Button approvee,reject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_detail_admin);

        //typecasting
        imageView=findViewById(R.id.imageview_approve);

        adid=findViewById(R.id.adid_req);
        date=findViewById(R.id.date_req);
        username=findViewById(R.id.username_req);
        category=findViewById(R.id.adtype_reqq);
        services=findViewById(R.id.serv_req);
        desc=findViewById(R.id.desc_req);
        address=findViewById(R.id.address_req);
        remarks=findViewById(R.id.remarks_req);
        status=findViewById(R.id.status_req);

        comment=findViewById(R.id.comment_req);

        approvee=findViewById(R.id.approve_btn_req);
        reject=findViewById(R.id.reject_btn_req);
    }
}
