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

public class signupcustomer extends AppCompatActivity {
    EditText fnamecustomer, lnamecustomer, coursecustomer, email, cpssword, pssword;
    Button loginbck2, createcust;
    TextView txterror;
    boolean passwordVisible;
    DatabaseOperations dbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcustomer);
        fnamecustomer = (EditText) findViewById(R.id.fnamecustomer);
        lnamecustomer = (EditText) findViewById(R.id.lnamecustomer);
        coursecustomer = (EditText) findViewById(R.id.coursecustomer);
        email = (EditText) findViewById(R.id.unamecustomer);
        cpssword = (EditText) findViewById(R.id.cpworddcustomer);
        pssword = (EditText) findViewById(R.id.pwordcustomer);
        createcust = (Button) findViewById(R.id.createcustomer);
        loginbck2=(Button) findViewById(R.id.loginbck2);
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

        createcust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirm=cpssword.getText().toString();
                String pword=pssword.getText().toString();
                if(fnamecustomer.getText().toString().equals("")){
                    fnamecustomer.setError("Input Firstname");
                }else if (lnamecustomer.getText().toString().equals("")){
                    lnamecustomer.setError("Input Lastname");
                }else if(coursecustomer.getText().toString().equals("")){
                    coursecustomer.setError("Input Course");
                }else if(email.getText().toString().equals("")) {
                    email.setError("Input BISU E-mail");
                }else if(pssword.getText().toString().equals("")){
                    pssword.setError("Input Password");
                }else if(!confirm.equals(pword)){
                        cpssword.setError("Password didn't matched.");
                        pssword.setText("");
                        cpssword.setText("");
                }else{
                        dbo.insertCustomer(fnamecustomer.getText().toString(), lnamecustomer.getText().toString(), coursecustomer.getText().toString(),email.getText().toString(),pssword.getText().toString());
                        Intent login = new Intent(signupcustomer.this,login.class);
                        startActivity(login);
                    Toast.makeText(signupcustomer.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        loginbck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back= new Intent(signupcustomer.this,login.class);
                startActivity(back);
            }
        });
    }
}