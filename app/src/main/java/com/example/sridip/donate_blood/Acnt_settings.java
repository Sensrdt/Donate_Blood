package com.example.sridip.donate_blood;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Acnt_settings extends AppCompatActivity {

    private TextView name, ph, group, type;

    Button btn;

    private ProgressDialog loadingbar;

    private DatabaseReference getUserData;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acnt_settings);


        name = (TextView) findViewById(R.id.name);
        ph = (TextView) findViewById(R.id.ph);
        group = (TextView) findViewById(R.id.group);
        type = (TextView) findViewById(R.id.type);
        btn = (Button) findViewById(R.id.button2);

        mAuth = FirebaseAuth.getInstance();

        String cur_uid = mAuth.getCurrentUser().getUid();

        getUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(cur_uid);


        getUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String n = (String)dataSnapshot.child("userName").getValue().toString();
                String p = (String)dataSnapshot.child("userPh").getValue().toString();
                String g = (String)dataSnapshot.child("userBlood").getValue().toString();
                String t = (String)dataSnapshot.child("userType").getValue().toString();


                name.setText(n);
                ph.setText(p);
                group.setText(g);
                type.setText(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You need to register ones again with your new details", Toast.LENGTH_LONG).show();
                ;
                new AlertDialog.Builder(Acnt_settings.this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Logout();
                            }
                        }).create().show();
            }
        });

    }

    private void Logout() {


        Toast.makeText(getApplicationContext(), "You need to register ones again with your new details", Toast.LENGTH_LONG).show();
        ;


        Intent startPage = new Intent(Acnt_settings.this, SignUp.class);
        startPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPage);
        finish();
    }
}
