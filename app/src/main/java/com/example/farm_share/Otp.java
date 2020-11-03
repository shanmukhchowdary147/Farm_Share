package com.example.farm_share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText Vphone, Votp;
    Button Verify;
    TextView Vlogin;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        fAuth=FirebaseAuth.getInstance();
        Vphone=findViewById(R.id.Vphone);
        Votp=findViewById(R.id.Otp);
        Verify=findViewById(R.id.Verify);
        Vlogin=findViewById(R.id.phone);
        progressBar=findViewById(R.id.progressBar);
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Vphone.getText().toString().isEmpty() && Vphone.getText().toString().length()==10)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    requestOTP(Vphone);
                }
                else
                {
                    Vphone.setError("Phone Number is Not Valid");
                }
            }
        });


    }

    private void requestOTP(EditText Vphone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(String.valueOf(Vphone), 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Otp.this, "Cannot Create Account", Toast.LENGTH_SHORT).show();

            }
        });
    }
}