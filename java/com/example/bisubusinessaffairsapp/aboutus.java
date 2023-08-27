package com.example.bisubusinessaffairsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.text.DateFormat;
import java.util.Calendar;

public class aboutus extends AppCompatActivity {
    Button vision, mission, goal, core;
    LinearLayout linear, linearsv;
    ScrollView svm, svg;
//
//    String f=getIntent().getExtras().getString("F");
//    String l=getIntent().getExtras().getString("L");
    String nowDate;
    DatabaseOperations dbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        vision=(Button) findViewById(R.id.vision);
        mission=(Button) findViewById(R.id.mission);
        goal=(Button) findViewById(R.id.goal);
        core=(Button) findViewById(R.id.core);
        linear=(LinearLayout) findViewById(R.id.linear);
        linearsv=(LinearLayout) findViewById(R.id.linearsv);
        svm=(ScrollView) findViewById(R.id.svm);
        svg=(ScrollView) findViewById(R.id.svg);


//        id=getIntent().getExtras().getString("ID");
//        intid=Integer.parseInt(id);
        dbo=new DatabaseOperations(this);

        //calendar
        Calendar now = Calendar.getInstance();
        nowDate = DateFormat.getDateInstance(DateFormat.FULL).format(now.getTime());

        vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearsv.setVisibility(View.VISIBLE);
                svm.setVisibility(View.INVISIBLE);
                svg.setVisibility(View.INVISIBLE);
            }
        });

        mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearsv.setVisibility(View.INVISIBLE);
                svm.setVisibility(View.VISIBLE);
                svg.setVisibility(View.INVISIBLE);
            }
        });

        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearsv.setVisibility(View.INVISIBLE);
                svm.setVisibility(View.INVISIBLE);
                svg.setVisibility(View.VISIBLE);
            }
        });

        core.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent home = new Intent(aboutus.this, home.class);
//                home.putExtra("F",f);
//                home.putExtra("L", l);
                startActivity(home);
                break;

            case R.id.shop:
                Intent shop = new Intent(aboutus.this, shop.class);
//                shop.putExtra("F",f);
//                shop.putExtra("L", l);
                shop.putExtra("Petsa", nowDate);
                startActivity(shop);
                break;

            case R.id.aboutus:
                Intent aboutus = new Intent(aboutus.this, aboutus.class);
//                aboutus.putExtra("F",f);
//                aboutus.putExtra("L", l);
                startActivity(aboutus);
                break;

            case R.id.logout:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Log out");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout=new Intent(aboutus.this,login.class);
                        startActivity(logout);
                    }
                }) .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}