package com.example.farm_share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView cardEditprofile;
    public CardView cardSearch;
    public CardView cardAdd;
    public CardView cardActivity;
    public CardView cardFav;
    public CardView cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardEditprofile=(CardView) findViewById(R.id.cEditProfile);
        cardSearch=(CardView) findViewById(R.id.cSearch);
        cardAdd=(CardView) findViewById(R.id.cAdd);
        cardActivity=(CardView) findViewById(R.id.cFeedback);
        cardFav=(CardView) findViewById(R.id.cFav);
        cardLogout=(CardView) findViewById(R.id.cLogout);

        cardEditprofile.setOnClickListener(this);
        cardSearch.setOnClickListener(this);
        cardAdd.setOnClickListener(this);
        cardActivity.setOnClickListener(this);
        cardFav.setOnClickListener(this);
        cardLogout.setOnClickListener(this);
    }



    

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cEditProfile :
                i=new Intent(this,EditProfile.class);
                startActivity(i);
                break;
            case R.id.cAdd :
                i=new Intent(this,AdminCategoryActivity.class);
                startActivity(i);
                break;
            case R.id.cLogout :
                
                i=new Intent(this,Login.class);
                startActivity(i);
                break;
            case R.id.cSearch :
                i=new Intent(this,Search.class);
                startActivity(i);
                break;
            case R.id.cFeedback :
                i=new Intent(this,Feedback.class);
                startActivity(i);
                break;

        }

    }
    @Override
    public void onBackPressed()
    {

    }
}