package com.example.farm_share;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm_share.Model.Users;
import com.example.farm_share.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class EditProfile extends AppCompatActivity
{

    private static final String TAG ="Shannu" ;
    private Button cancel;

    EditText ProfileFullName, ProfileEmailAddress, ProfilePassword ;

    String _Name,_Name1,_Email,_Email1,_Password,_Password1,_Phone,_Phone1;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference=FirebaseDatabase.getInstance().getReference("Users");

        ProfileFullName= findViewById(R.id.FullName) ;
        ProfileEmailAddress = findViewById(R.id.profileEmailAddress) ;
        ProfilePassword = findViewById(R.id.profilePassword) ;
        cancel=(Button)findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        showAllUserData();

    }

    private void showAllUserData() {

        Bundle bundle=getIntent().getExtras();

        _Name=bundle.getString("NAME");

        _Email=bundle.getString("EMAIL");
        _Phone=bundle.getString("PHONE");
        _Password =bundle.getString("PASSWORD");

        ProfileFullName.setText(_Name);
        ProfileEmailAddress.setText(_Email);
        ProfilePassword.setText(_Password);
    }

    public void update(View view)
    {
        if (isNameChanged() || isPasswordChanged() || isEmailChanged())
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            Toast.makeText(this, "Information Updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Data Cannot be Updated!!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isPasswordChanged() {
        if(!_Password.equals(ProfilePassword.getText().toString()))
        {
            reference.child(_Phone).child("password").setValue(ProfilePassword.getText().toString());
            _Password=ProfilePassword.getText().toString();
            return true;
        }
        else
        {
            return false;
        }

    }

    private boolean isEmailChanged() {
        if(!_Email.equals(ProfileEmailAddress.getText().toString()))
        {
            reference.child(_Phone).child("email").setValue(ProfileEmailAddress.getText().toString());
            _Email=ProfileEmailAddress.getText().toString();
            return true;
        }
        else
        {
            return false;
        }

    }


    private boolean isNameChanged() {
        if(!_Name.equals(ProfileFullName.getText().toString()))
        {
            reference.child(_Phone).child("name").setValue(ProfileFullName.getText().toString());
            _Name=ProfileFullName.getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }


}