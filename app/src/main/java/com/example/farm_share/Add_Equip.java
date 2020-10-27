package com.example.farm_share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Add_Equip extends AppCompatActivity {

    private String CategoryName;
    private Button AddNew;
    private ImageView ProductImage;
    private EditText Title, AdditionalInfo, Cost, Days, ContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__equip);

        CategoryName = getIntent() .getExtras().get("category").toString();

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
        AddNew=(Button) findViewById(R.id.AddNew);
        ProductImage=(ImageView) findViewById(R.id.ProductImage);
        Title=(EditText) findViewById(R.id.Title);
        AdditionalInfo=(EditText) findViewById(R.id. AdditionalInfo);
        Cost=(EditText) findViewById(R.id.Cost);
        Days=(EditText) findViewById(R.id.Days);
        ContactNumber=(EditText) findViewById(R.id.ContactNumber);



    }
}