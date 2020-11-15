package com.example.farm_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class Otp_num extends AppCompatActivity
{
    CountryCodePicker ccp;
    EditText t1;
    TextView OLogin;

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_num);

        t1=(EditText)findViewById(R.id.t1);
        OLogin=(TextView)findViewById(R.id.OLogin);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);
        b1=(Button)findViewById(R.id.b1);

        OLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

         b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numb=t1.getText().toString();
                if(TextUtils.isEmpty(numb))
                {
                    Toast.makeText(Otp_num.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(Otp_num.this,Otp_manage.class);
                    intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                    startActivity(intent);
                }
            }
        });

    }
}