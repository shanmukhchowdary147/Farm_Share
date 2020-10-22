package com.example.farm_share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView cardEditprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardEditprofile=(CardView) findViewById(R.id.cEditProfile);
        cardEditprofile.setOnClickListener(this);
    }



    public void logout(View view) {
            FirebaseAuth.getInstance().signOut(); //logout
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cEditProfile :
                i=new Intent(this,EditProfile.class);
                startActivity(i);
                break;
        }

    }
}