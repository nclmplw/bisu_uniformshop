package com.example.bisubusinessaffairsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {
    TextInputLayout layout, layoutname;
    TextInputEditText email, password;
    Button loginc, signupc;
    ImageButton admnstrtr;
    ProgressDialog loadingBar;
    DatabaseOperations dbo;

    String dbo_id, f, l;
    int intid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingBar = new ProgressDialog(this);
        email= findViewById(R.id.username);
        password=findViewById(R.id.password);
        layout=findViewById(R.id.inputlayout);
        layoutname=findViewById(R.id.layoutname);
        loginc=(Button) findViewById(R.id.login);
        signupc=(Button) findViewById(R.id.signupcustomer);
        admnstrtr=(ImageButton) findViewById(R.id.admnstrtr);
        dbo=new DatabaseOperations(this);



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
                    layoutname.setHelperTextEnabled(false);
                    email.setError(null);
                }
                else{
                    email.setError("Invalid email");
                    layoutname.setHelperText("Enter correct BISU E-mail");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pinput= charSequence.toString();
                    Pattern pattern= Pattern.compile("^a-zA-Z0-9");
                    Matcher matcher=pattern.matcher(pinput);
                    boolean passmatch = matcher.find();
                    if(passmatch){
                        layout.setHelperText("Your Password are Strong");
                        layout.setError("");
                    }else{
                    layout.setHelperText("Enter correct password");
                    layout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loginc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkemail = dbo.checkemail(email.getText().toString());
                Boolean checkpassword = dbo.checkpassword(password.getText().toString());
//                Toast.makeText(login.this, ""+dbo_id, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(email.getText().toString())) {
                    showError(email,"Enter BISU E-mail!");
                    email.setText("");
                }else if(TextUtils.isEmpty(password.getText().toString())){
                    showError(password,"Enter BISU E-mail!");
                    password.setText("");
                }
                else if (checkemail == true && checkpassword == true) {
                    loadingBar.setTitle("Login Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    dbo_id= dbo.getId(email.getText().toString(), password.getText().toString());
                    intid=Integer.parseInt(dbo_id);
                    f=dbo.getContent(intid,"c_firstname","qcustomer","customer_id=?");
                    l=dbo.getContent(intid,"c_lastname","qcustomer","customer_id=?");
////                    Toast.makeText(login.this, f+" "+l, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(login.this, home.class);
                    intent.putExtra("F",f);
                    intent.putExtra("L", l);
                    startActivity(intent);
                    email.setText("");
                    password.setText("");
                } else {
                    showError(email,"Invalid E-mail and Password");
                    email.setText("");
                    password.setText("");
                }
            }

            private void showError(TextInputEditText username, String invalid_username_and_password) {
                username.setError(invalid_username_and_password);
            }
        });

        signupc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(login.this,signupcustomer.class);
                startActivity(intent);
            }
        });

        admnstrtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent= new Intent(login.this,adminlogin.class);
            startActivity(intent);
            }
        });
    }
}