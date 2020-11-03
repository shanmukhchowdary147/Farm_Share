package com.example.farm_share;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm_share.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText Vphone, Votp;
    Button Verify;
    TextView Vlogin;
    ProgressBar progressBar;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verficationInProgress = false;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
                if (!verficationInProgress)
                {
                    if(!Vphone.getText().toString().isEmpty() && Vphone.getText().toString().length()==10)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        Vlogin.setText("Sending OTP..");
                        Vlogin.setVisibility(View.VISIBLE);
                        requestOTP(Vphone);

                    }
                    else
                    {
                        Vphone.setError("Phone Number is Not Valid");
                    }
                }
                else
                {
                    String userotp=Votp.getText().toString();
                    if(!userotp.isEmpty() && userotp.length()==6)
                    {
                        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,userotp);
                        verifyAuth(credential);
                    }
                    else
                    {
                        Votp.setError("Enter Valid OTP");
                    }


                }

            }
        });


    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Otp.this, "Phone Number is verified", Toast.LENGTH_SHORT).show();
                    Redirect(Vphone);
                }
                else
                {
                    Toast.makeText(Otp.this, "Authentication failed!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Redirect(EditText vphone) {
        final String SVphone=Vphone.getText().toString();
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(SVphone).exists())
                {
                    Users userData= dataSnapshot.child("Users").child(SVphone).getValue(Users.class);
                    if (userData.getPhone().equals(SVphone))
                    {
                            Toast.makeText(Otp.this, "Login Success..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            progressBar.setVisibility(View.GONE);
                    }

                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Otp.this, "Create new Account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Register.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void requestOTP(EditText Vphone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(String.valueOf(Vphone), 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                Votp.setVisibility(View.VISIBLE);
                verificationId = s;
                token = forceResendingToken;
                Verify.setText("Verify");
                Verify.setEnabled(false);
                verficationInProgress= true;


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

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