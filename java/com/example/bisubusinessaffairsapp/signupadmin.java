package com.example.bisubusinessaffairsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupadmin extends AppCompatActivity {
    EditText fnameadmin, lnameadmin, email, apword, pssword;
    Button loginbck, createad;
    TextView txterror;
    boolean passwordVisible;
    DatabaseOperations dbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupadmin);
        fnameadmin = (EditText) findViewById(R.id.fnameadmin);
        lnameadmin = (EditText) findViewById(R.id.lnameadmin);
        email = (EditText) findViewById(R.id.emaill);
        apword = (EditText) findViewById(R.id.apwordd);
        pssword = (EditText) findViewById(R.id.pworddadmin);
        createad = (Button) findViewById(R.id.createadmin);
        loginbck=(Button) findViewById(R.id.loginbck);
        txterror=(TextView) findViewById(R.id.error);
        dbo = new DatabaseOperations(this);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String e=email.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@bisu.edu.ph";
                if (e.matches(emailPattern) && s.length() > 0)
                {
                    email.setError(null);
                }
                else{
                    email.setError("Invalid email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirm=apword.getText().toString();
                String pass=pssword.getText().toString();
                if(fnameadmin.getText().toString().equals("")){
                    fnameadmin.setError("Input Firstname");
                }else if (lnameadmin.getText().toString().equals("")){
                    lnameadmin.setError("Input Lastname");
                }else if(email.getText().toString().equals("")) {
                    email.setError("Input BISU E-mail");
                }else if(pssword.getText().toString().equals("")){
                    pssword.setError("Input Password");
                }else if(!confirm.equals(pass)){
                    apword.setError("Password didn't matched.");
                    pssword.setText("");
                    apword.setText("");
                }else{
                    txterror.setText("");
                    if(dbo.insertAdmin(fnameadmin.getText().toString(), lnameadmin.getText().toString(), email.getText().toString(),pssword.getText().toString())){
                        Intent login = new Intent(signupadmin.this,adminlogin.class);
                        startActivity(login);
                        Toast.makeText(signupadmin.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(signupadmin.this, "Failed to save!", Toast.LENGTH_SHORT).show();
                    }
                }
                }
        });


        loginbck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(signupadmin.this,adminlogin.class);
                startActivity(back);
            }
        });
    }
}