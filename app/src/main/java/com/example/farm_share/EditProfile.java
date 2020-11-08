package com.example.farm_share;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm_share.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;


public class EditProfile extends AppCompatActivity
{
    private ImageView ProfileImageView;
    private EditText Title, ProfileEmailAddress, ProfilePhoneNo ;
    private Button SaveProfileInfo;
    private TextView ProfileChangeTextbtn;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ProfileImageView = (ImageView) findViewById(R.id.profileImageView) ;
        Title = (EditText) findViewById(R.id.title) ;
        ProfileEmailAddress = (EditText) findViewById(R.id.profileEmailAddress) ;
        ProfilePhoneNo = (EditText) findViewById(R.id.profilePhoneNo) ;
        SaveProfileInfo = (Button) findViewById(R.id.saveProfileInfo) ;
        ProfileChangeTextbtn = (TextView) findViewById(R.id.profileChangeTextbtn) ;

        userInfoDisplay(ProfileImageView, Title, ProfilePhoneNo, ProfileEmailAddress );
        
        SaveProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (checker.equals("clicked"))
                {
                     userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();

                }

            }
        });


        ProfileChangeTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checker =  "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(EditProfile.this);

            }
        });
    }

    private void updateOnlyUserInfo()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance() .getReference() .child("Users") ;

        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name", Title.getText().toString()) ;
        userMap. put("address", ProfileEmailAddress.getText().toString()) ;
        userMap. put("phone nCumber", ProfilePhoneNo.getText().toString()) ;
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);


        startActivity(new Intent(EditProfile.this, MainActivity.class));
        Toast.makeText(EditProfile.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode== RESULT_OK && data!= null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            ProfileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(EditProfile.this, EditProfile.class));
            finish();
        }
    }

    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(Title.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ProfileEmailAddress.getText().toString()))
        {
            Toast.makeText(this, "Email is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ProfilePhoneNo.getText().toString()))
        {
            Toast.makeText(this, "Number is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }


    }

    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(Prevalent.currentOnlineUser.getPhone() + ".jpg");

             uploadTask = fileRef.putFile(imageUri);

             uploadTask.continueWithTask(new Continuation() {
                 @Override
                 public Object then(@NonNull Task task) throws Exception
                 {
                     if (!task.isSuccessful())
                     {
                         throw task.getException();
                     }

                     return fileRef.getDownloadUrl();

                 }
             })
             .addOnCompleteListener(new OnCompleteListener<Uri>() {
                 @Override
                 public void onComplete(@NonNull Task<Uri> task)
                 {
                     if (task.isSuccessful())
                     {
                         Uri downloadUrl = task.getResult() ;
                         myUrl = downloadUrl.toString();

                         DatabaseReference ref = FirebaseDatabase.getInstance() .getReference() .child("Users") ;

                         HashMap<String, Object> userMap = new HashMap<>();
                         userMap. put("name", Title.getText().toString()) ;
                         userMap. put("address", ProfileEmailAddress.getText().toString()) ;
                         userMap. put("phone nCumber", ProfilePhoneNo.getText().toString()) ;
                         userMap. put("image", myUrl) ;
                          ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                         progressDialog.dismiss();

                         startActivity(new Intent(EditProfile.this, MainActivity.class));
                         Toast.makeText(EditProfile.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                         finish();
                     }
                     else
                     {
                         progressDialog.dismiss();
                         Toast.makeText(EditProfile.this, "Error.", Toast.LENGTH_SHORT).show();
                     }

                 }
             });

        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }


        }

    private void userInfoDisplay(final ImageView ProfileImageView, final EditText Title, final EditText ProfilePhoneNo, final EditText ProfileEmailAddress)
    {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance() .getReference() .child("Users").child(Prevalent.currentOnlineUser.getPhone());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("image").exists())
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("password").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.get() .load(image) .into(ProfileImageView);
                        Title.setText(name);
                        ProfilePhoneNo.setText(phone);
                        ProfileEmailAddress.setText(address);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}