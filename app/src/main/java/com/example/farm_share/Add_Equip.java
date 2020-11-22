package com.example.farm_share;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class Add_Equip extends AppCompatActivity {

    private String CategoryName, DTitle, DAdditionalInfo, DCost, DDays, DContactNumber,saveCurrentDate,saveCurrentTime;
    private Button AddNew;
    private ImageView EquipImage;
    private EditText Title, AdditionalInfo, Cost, Days, ContactNumber;
    private static final int PICK_FILE=1;
    Uri ImageUri;
    StorageReference Folder;
    String ImagePath;

    private String EquipRandomKey;

    private DatabaseReference EquipsRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__equip);


        CategoryName = getIntent() .getExtras().get("category").toString();

        EquipsRef= FirebaseDatabase.getInstance().getReference().child("Equipments");

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
        AddNew=(Button) findViewById(R.id.AddNew);

        Title=(EditText) findViewById(R.id.Title);
        AdditionalInfo=(EditText) findViewById(R.id. AdditionalInfo);
        Cost=(EditText) findViewById(R.id.Cost);
        Days=(EditText) findViewById(R.id.Days);
        ContactNumber=(EditText) findViewById(R.id.ContactNumber);
        EquipImage=findViewById(R.id.EquipImage);


        EquipImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePictures();
            }
        });

        AddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateEquipData();
            }
        });



    }

    private void choosePictures() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null )
        {
            ImageUri= data.getData();
            Folder=FirebaseStorage.getInstance().getReference().child("Equipment Images");
            EquipImage.setImageURI(ImageUri);

            ValidateEquipData();
        }
    }




    private void ValidateEquipData()
    {
        DTitle = Title.getText().toString();
        DAdditionalInfo = AdditionalInfo.getText().toString();
        DCost = Cost.getText().toString();
        DDays = Days.getText().toString();
        DContactNumber = ContactNumber.getText().toString();

       if (TextUtils.isEmpty(DTitle))      {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DAdditionalInfo))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DCost))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DDays))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DContactNumber))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreEquipInfo();
        }
    }

    private void StoreEquipInfo() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        EquipRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference file_name=Folder.child("image"+ImageUri.getLastPathSegment()+ EquipRandomKey +".jpg");
        file_name.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImagePath=String.valueOf(uri);
                        SaveEquipInfoTodatabase();
                    }
                });

            }
        });




    }

    private void SaveEquipInfoTodatabase() {
        HashMap<String, Object> Equipmap=new HashMap<>();
        Equipmap.put("Eid",EquipRandomKey);
        Equipmap.put("date",saveCurrentDate);
        Equipmap.put("time",saveCurrentTime);
        Equipmap.put("image",ImagePath);
        Equipmap.put("category",CategoryName);
        Equipmap.put("title",DTitle);
        Equipmap.put("additionalInfo",DAdditionalInfo);
        Equipmap.put("cost",DCost);
        Equipmap.put("days",DDays);
        Equipmap.put("contactNumber",DContactNumber);

        EquipsRef.child(EquipRandomKey).updateChildren(Equipmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Add_Equip.this, "Equipment is added Successfully ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(Add_Equip.this, "Error"+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),AdminCategoryActivity.class));
    }
}