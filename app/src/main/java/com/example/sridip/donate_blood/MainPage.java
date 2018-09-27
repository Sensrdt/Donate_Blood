package com.example.sridip.donate_blood;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference,shareDatabase;

    Toolbar toolbar;

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private TabsPagerAdapter mTabsPagerAdapter;

    ProgressDialog progressDialog;





    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mAuth = FirebaseAuth.getInstance();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        progressDialog = new ProgressDialog(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LifeSaver");

        progressDialog.setTitle("Blood is the precious thing of all");
        progressDialog.show();

        viewPager = (ViewPager) findViewById(R.id.main_pager);

        tabLayout = (TabLayout) findViewById(R.id.main_tab);

        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mTabsPagerAdapter);

        progressDialog.dismiss();


        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            LogoutUser();

        } else {

            update();


        }


    }

    private void update() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Update");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {







            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void LogoutUser() {

        Intent startPage = new Intent(MainPage.this, StartPage.class);
        startPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPage);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.logout) {
            LogoutUser();

        } else if (item.getItemId() == R.id.acntsettings) {

            Intent a = new Intent(MainPage.this, Acnt_settings.class);
            startActivity(a);

        }

        else if(item.getItemId()==R.id.share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Life_Saver");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        return true;
    }


    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {

            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainPage.super.onBackPressed();
                            quit();
                        }
                    }).create().show();
        }
        else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
        }

    }
    public void quit() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(start);
    }
}
