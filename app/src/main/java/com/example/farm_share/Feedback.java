package com.example.farm_share;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Feedback extends AppCompatActivity {
    private EditText issue,comment;
    private String saveCurrentDate,saveCurrentTime;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        issue=(EditText) findViewById(R.id.issue);
        comment=(EditText) findViewById(R.id.comment);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cissue=issue.getText().toString();
                String Ccomment=comment.getText().toString();
                 if (TextUtils.isEmpty(Cissue))
                 {
                     Toast.makeText(Feedback.this, "Please tell us your issue!", Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                     StoreFeedback(Cissue,Ccomment);
                 }

            }
        });
    }

    private void StoreFeedback(final String cissue, final String ccomment) {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final String RandomKey = saveCurrentDate + saveCurrentTime;

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> feedbackMap=new HashMap<>();
                feedbackMap.put("Issue",cissue);
                feedbackMap.put("Comment",ccomment);

                RootRef.child("UserFeedback").child(RandomKey).updateChildren(feedbackMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                Toast.makeText(Feedback.this, "feedback Received", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Feedback.this, "Error!!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }


}