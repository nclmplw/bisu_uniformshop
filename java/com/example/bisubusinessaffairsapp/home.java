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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import java.text.DateFormat;
import java.util.Calendar;

public class home extends AppCompatActivity {
    Button shop;
    TextView date, display, prdctname, prdctprice;
    ImageButton bscs1, bsit1, bsf1, midwifery1, bsede1, bsedm1, fpst1;
    ShapeableImageView viewiamge;
    String f,l;
    String nowDate;
    DatabaseOperations dbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        shop=(Button) findViewById(R.id.shopuni);
        dbo= new DatabaseOperations(this);
        bscs1=(ImageButton) findViewById(R.id.bscs1);
        bsit1=(ImageButton) findViewById(R.id.bsit1);
        bsf1=(ImageButton) findViewById(R.id.bsf1);
        midwifery1=(ImageButton) findViewById(R.id.midwifery1);
        bsede1=(ImageButton) findViewById(R.id.bsede1);
        bsedm1=(ImageButton) findViewById(R.id.bsedm1);
        fpst1=(ImageButton) findViewById(R.id.fpst1);

        f=getIntent().getExtras().getString("F");
        l=getIntent().getExtras().getString("L");

        viewiamge=(ShapeableImageView) findViewById(R.id.viewimage);
        prdctname=(TextView) findViewById(R.id.prdctname);
        prdctprice=(TextView) findViewById(R.id.prdctprice);
        //calendar
        Calendar now = Calendar.getInstance();
        nowDate = DateFormat.getDateInstance(DateFormat.FULL).format(now.getTime());
        date = (TextView) findViewById(R.id.date);
        display=(TextView) findViewById(R.id.name);
        display.setText(f+" "+l+"  ㋡");
        date.setText(nowDate);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,shop.class);
                intent.putExtra("F",f);
                intent.putExtra("L",l);
                startActivity(intent);
            }
        });

        bscs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewiamge.setImageResource(R.drawable.bscs);
                prdctname.setText("BSCS DEPARTMENTAL UNIFORM");
                prdctprice.setText("₱420.00");
            }
        });

        bsit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewiamge.setImageResource(R.drawable.frntbsite);
                prdctname.setText("BSIT-E DEPARTMENTAL UNIFORM");
                prdctprice.setText("₱420.00");
            }
        });

        bsf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewiamge.setImageResource(R.drawable.fntbsf);
                prdctname.setText("BSF DEPARTMENTAL UNIFORM");
                prdctprice.setText("₱420.00");
            }
        });

        fpst1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewiamge.setImageResource(R.drawable.frntbsitft);
                prdctname.setText("BSIT-FSPT DEPARTMENTAL UNIFORM");
                prdctprice.setText("₱420.00");
            }
        });

        midwifery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewiamge.setImageResource(R.drawable.midwifery);
                prdctname.setText("MIDWIFERY DEPARTMENTAL UNIFORM");
                prdctprice.setText("₱420.00");
            }
        });

        bsede1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewiamge.setImageResource(R.drawable.bed);
                prdctname.setText("BSED-Eng DEPARTMENTAL UNIFORM");
                prdctprice.setText("₱420.00");
            }
        });

        bsedm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewiamge.setImageResource(R.drawable.bed);
                prdctname.setText("BSED-Math DEPARTMENTAL UNIFORM");
                prdctprice.setText("₱420.00");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent home= new Intent(home.this, home.class);
                home.putExtra("F",f);
                home.putExtra("L",l);
                startActivity(home);
                break;

            case R.id.shop:
                Intent shop= new Intent(home.this, shop.class);
                shop.putExtra("F",f);
                shop.putExtra("L",l);
                startActivity(shop);
                break;

            case R.id.aboutus:
                Intent aboutus=new Intent(home.this,aboutus.class);
                aboutus.putExtra("F",f);
                aboutus.putExtra("L",l);
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
                        Intent logout=new Intent(home.this,login.class);
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