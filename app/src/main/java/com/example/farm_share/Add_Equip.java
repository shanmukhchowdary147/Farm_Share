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

public class Add_Equip extends AppCompatActivity {

    private String CategoryName, DTitle, DAdditionalInfo, DCost, DDays, DContactNumber,saveCurrentDate,saveCurrentTime;
    private Button AddNew;
    private ImageView EquipImage;
    private EditText Title, AdditionalInfo, Cost, Days, ContactNumber;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String EquipRandomKey, downloadImageUrl;
    private StorageReference EqipImagesRef;
    private DatabaseReference EquipsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__equip);


        CategoryName = getIntent() .getExtras().get("category").toString();
        EqipImagesRef = FirebaseStorage.getInstance().getReference().child("Equip Images");
        EquipsRef= FirebaseDatabase.getInstance().getReference().child("Equipments")

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
        AddNew=(Button) findViewById(R.id.AddNew);
        EquipImage=(ImageView) findViewById(R.id.EquipImage);
        Title=(EditText) findViewById(R.id.Title);
        AdditionalInfo=(EditText) findViewById(R.id. AdditionalInfo);
        Cost=(EditText) findViewById(R.id.Cost);
        Days=(EditText) findViewById(R.id.Days);
        ContactNumber=(EditText) findViewById(R.id.ContactNumber);

        EquipImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        AddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateEquipData();
            }
        });



    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            EquipImage.setImageURI(ImageUri);
        }

    }

    private void ValidateEquipData()
    {
        DTitle = Title.getText().toString();
        DAdditionalInfo = AdditionalInfo.getText().toString();
        DCost = Cost.getText().toString();
        DDays = Days.getText().toString();
        DContactNumber = ContactNumber.getText().toString();

        if (ImageUri==null)
        {
            Toast.makeText(this, "Upload Equipment Image", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DTitle))
        {
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

        final StorageReference filepath= EqipImagesRef.child(ImageUri.getLastPathSegment() + EquipRandomKey +".jpg");

        final UploadTask uploadTask = filepath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message= e.toString();
                Toast.makeText(Add_Equip.this, "ERROR!! ", Toast.LENGTH_SHORT).show();

            }
        }) .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Add_Equip.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri>urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageUrl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Add_Equip.this, "got Equipment image url succesfully ", Toast.LENGTH_SHORT).show();
                            SaveEquipInfoTodatabase();
                        }

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
        Equipmap.put("image",downloadImageUrl);
        Equipmap.put("category",CategoryName);
        Equipmap.put("Title",DTitle);
        Equipmap.put("AdditionalInfo",DAdditionalInfo);
        Equipmap.put("Cost",DCost);
        Equipmap.put("Days",DDays);
        Equipmap.put("ContactNumber",DContactNumber);

        EquipsRef.child(EquipRandomKey).updateChildren(Equipmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Add_Equip.this, "Equipment is added Successfully ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(Add_Equip.this, "Error"+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}