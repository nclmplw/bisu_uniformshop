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

public class adminlogin extends AppCompatActivity {
    TextInputLayout layout, loname;
    TextInputEditText email, password;
    Button logina, signupadmin;
    ImageButton cstmr;
    ProgressDialog loadingBar;
    DatabaseOperations dbo;

//    String dbo_id;
//    String f, l;
    int intid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        loadingBar = new ProgressDialog(this);
        email= findViewById(R.id.username);
        password=findViewById(R.id.password);
        loname=findViewById(R.id.loname);
        layout=findViewById(R.id.inputlayout);
        logina=(Button) findViewById(R.id.login);
        signupadmin=(Button) findViewById(R.id.signupadmin);
        cstmr=(ImageButton) findViewById(R.id.cstmr);
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
                    loname.setHelperTextEnabled(false);
                    email.setError(null);
                }
                else{
                    email.setError("Invalid email");
                    loname.setHelperText("Enter correct BISU E-mail");
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

        logina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dbo_id= dbo.getIda(email.getText().toString(), password.getText().toString());
//                intid=Integer.parseInt(dbo_id);
//                f=dbo.getContent(intid,"a_firstname","qadmin","admin_id=?");
//                l=dbo.getContent(intid,"a_lastname","qadmin","admin_id=?");

                Boolean checku = dbo.chckusername(email.getText().toString());
                Boolean checkp = dbo.chckpassword(password.getText().toString());

                if (TextUtils.isEmpty(email.getText().toString())) {
                    showError(email,"Enter BISU E-mail!");
                    email.setText("");
                }else if(TextUtils.isEmpty(password.getText().toString())){
                    showError(password,"Enter BISU E-mail!");
                    password.setText("");
                }
                else if (checku == true && checkp == true) {
                    loadingBar.setTitle("Login Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    Intent intent = new Intent(adminlogin.this, admin.class);
//                    intent.putExtra("F",f);
//                    intent.putExtra("L", l);
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

        signupadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(adminlogin.this, signupadmin.class);
                startActivity(intent);
            }
        });

        cstmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(adminlogin.this, login.class);
                startActivity(intent);
            }
        });
    }
}