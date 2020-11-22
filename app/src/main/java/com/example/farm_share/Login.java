package com.example.farm_share;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm_share.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private static final String TAG ="shannu2" ;
    EditText InPassword,InPhone;
    Button mLoginBtn;
    TextView mCreateBtn,forgotTextLink;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InPassword =(EditText) findViewById(R.id.password);
        mLoginBtn = (Button)findViewById(R.id.loginBtn);
        mCreateBtn = (TextView)findViewById(R.id.createText);
        InPhone =(EditText) findViewById(R.id.phone);

        progressBar = findViewById(R.id.progressBar);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Otp_num.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();

            }
        });


    }

    private void LoginUser() {

        String password=InPassword.getText().toString();
        String phone=InPhone.getText().toString();
        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please enter your phone number!!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {

            progressBar.setVisibility(View.VISIBLE);
            AllowAccessToAcc(phone,password);
        }
    }

    private void AllowAccessToAcc(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users userData= dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (userData.getPhone().equals(phone))
                    {
                        if (userData.getPassword().equals(password))
                        {
                            String Lname=userData.getName();
                            String Lemail=userData.getEmail();
                            Intent intent=new Intent(Login.this,EditProfile.class);
                            intent.putExtra("NAME",Lname);
                            intent.putExtra("EMAIL",Lemail);
                            intent.putExtra("PHONE",phone);
                            intent.putExtra("PASSWORD",password);
                            startActivity(intent);
                            Log.d(TAG, "onDataChange: "+Lname);

                            Toast.makeText(Login.this, "Login Success..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Account with this deatils doesnot exist..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
