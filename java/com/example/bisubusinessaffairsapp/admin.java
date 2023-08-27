package com.example.bisubusinessaffairsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class admin extends AppCompatActivity {
    TextView date, display, ci, ai, pi, pdi, si;
    DatabaseOperations dbo;

    String f,l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        dbo=new DatabaseOperations(this);
        ci=(TextView) findViewById(R.id.ci);
        ai=(TextView) findViewById(R.id.ai);
        pi=(TextView) findViewById(R.id.pi);
        pdi=(TextView) findViewById(R.id.pdi);
        si=(TextView) findViewById(R.id.si);


        //calendar
        Calendar now = Calendar.getInstance();
        String nowDate = DateFormat.getDateInstance(DateFormat.FULL).format(now.getTime());
        date = (TextView) findViewById(R.id.date);
        display=(TextView) findViewById(R.id.name);
//        display.setText(f+" "+l);
        date.setText(nowDate);

        ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("kanni");
                Intent intent=new Intent(admin.this,customerinfo.class);
//                intent.putExtra("F",f);
//                intent.putExtra("L", l);
                startActivity(intent);
            }
        });

        ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admin.this,admininfo.class);

                startActivity(intent);
            }
        });

        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("kanni2");
                Intent intent=new Intent(admin.this,productinfo.class);
//                intent.putExtra("F",f);
//                intent.putExtra("L", l);
                startActivity(intent);
            }
        });

        pdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admin.this,reservedinfo.class);
//                intent.putExtra("F",f);
//                intent.putExtra("L", l);
                startActivity(intent);
            }
        });

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admin.this,sales.class);
//                intent.putExtra("F",f);
//                intent.putExtra("L", l);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.adminmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeadmin:
                Intent home= new Intent(admin.this, admin.class);
//                home.putExtra("F",f);
//                home.putExtra("L", l);
                startActivity(home);
                break;

            case R.id.customerinfo:
                Intent customerinfo=new Intent(admin.this, customerinfo.class);
//                customerinfo.putExtra("F",f);
//                customerinfo.putExtra("L", l);
                startActivity(customerinfo);
                break;

            case R.id.admininfo:
                Intent admininfo=new Intent(admin.this, admininfo.class);
//                admininfo.putExtra("F",f);
//                admininfo.putExtra("L", l);
                startActivity(admininfo);
                break;

            case R.id.productinfo:
                Intent productinfo=new Intent(admin.this, productinfo.class);
//                productinfo.putExtra("F",f);
//                productinfo.putExtra("L", l);
                startActivity(productinfo);
                break;

            case R.id.purchasedinfo:
                Intent purchasedinfo=new Intent(admin.this,reservedinfo.class);
//                purchasedinfo.putExtra("F",f);
//                purchasedinfo.putExtra("L", l);
                startActivity(purchasedinfo);
                break;

            case R.id.sales:
                Intent sale=new Intent(admin.this, sales.class);
//                sale.putExtra("F",f);
//                sale.putExtra("L", l);
                startActivity(sale);
                break;

            case R.id.logout:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Log out");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout=new Intent(admin.this,adminlogin.class);
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