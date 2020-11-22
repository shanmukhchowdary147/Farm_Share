package com.example.farm_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView trailer, motorcultivator, crawlertractor, rotarytiller;
    private ImageView seeder, harvester, mowing, minitractor;
    private ImageView irrigationmachine, loader, truck, others;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        trailer = (ImageView) findViewById(R.id.trailer);
        motorcultivator = (ImageView) findViewById(R.id.motorcultivator);
        crawlertractor = (ImageView) findViewById(R.id.crawlertractor);
        rotarytiller= (ImageView) findViewById(R.id.rotarytiller);

        seeder = (ImageView) findViewById(R.id.seeder);
        harvester = (ImageView) findViewById(R.id.harvester);
        mowing = (ImageView) findViewById(R.id.mowing);
        minitractor = (ImageView) findViewById(R.id.minitractor);

        irrigationmachine = (ImageView) findViewById(R.id.irrigationmachine);
        loader = (ImageView) findViewById(R.id.loader);
        truck = (ImageView) findViewById(R.id.truck);
        others = (ImageView) findViewById(R.id.others);


        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Trailer");
                startActivity(intent);
            }
        });


        motorcultivator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Motor Cultivator");
                startActivity(intent);
            }
        });


        crawlertractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Crawler Tractor");
                startActivity(intent);
            }
        });


        rotarytiller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Rotary Tiller");
                startActivity(intent);
            }
        });


        seeder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Seeder");
                startActivity(intent);
            }
        });


        harvester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Harvester");
                startActivity(intent);
            }
        });



        mowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Mowing");
                startActivity(intent);
            }
        });


        minitractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Mini Tractor");
                startActivity(intent);
            }
        });



        irrigationmachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Irrigation Machine");
                startActivity(intent);
            }
        });


        loader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Loader");
                startActivity(intent);
            }
        });


        truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "Truck");
                startActivity(intent);
            }
        });


        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, Add_Equip.class);
                intent.putExtra("category", "others");
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}