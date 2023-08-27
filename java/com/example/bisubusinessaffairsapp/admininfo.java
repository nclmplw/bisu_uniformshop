package com.example.bisubusinessaffairsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class admininfo extends AppCompatActivity {
    TextView ida;
    EditText firstnamea, lastnamea, email, password;
    ImageButton insert, delete, update, save, clear;
    ListView listview;
    ArrayList<String> getAdmins;
    ArrayList<String> adminid;
    ArrayList<String> adminfirst;
    ArrayList<String> adminlast;
    ArrayList<String> adminemail;
    ArrayList<String> adminpass;

    String orderColumn = "admin_id",orderSort="ASC";
    String orderBy= orderColumn+" "+orderSort;
    int listViewposition;

//    String f=getIntent().getExtras().getString("F");
//    String l=getIntent().getExtras().getString("L");
    DatabaseOperations dbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admininfo);
        ida = (TextView) findViewById(R.id.ida);
        firstnamea = (EditText) findViewById(R.id.firstnamea);
        lastnamea = (EditText) findViewById(R.id.lastnamea);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        update = (ImageButton) findViewById(R.id.update);
        delete = (ImageButton) findViewById(R.id.delete);
        insert = (ImageButton) findViewById(R.id.insert);
        save = (ImageButton) findViewById(R.id.save);
        clear=(ImageButton) findViewById(R.id.clear);
        listview = (ListView) findViewById(R.id.listview);

        dbo = new DatabaseOperations(this);

        adminid = new ArrayList<>();
        adminfirst = new ArrayList<>();
        adminlast = new ArrayList<>();
        adminemail = new ArrayList<>();
        adminpass = new ArrayList<>();

//        id=getIntent().getExtras().getString("ID");
//        intid=Integer.parseInt(id);

        getAdmins = dbo.getAllAdmin();
        adminid = dbo.getAdminId("admin_id", orderBy);
        adminfirst = dbo.getAdminF("a_firstname", orderBy);
        adminlast = dbo.getAdminL("a_lastname", orderBy);
        adminemail = dbo.getAdminE("a_email", orderBy);
        adminpass = dbo.getAdminP("a_password", orderBy);

        CustomerAdapter adapter = new CustomerAdapter(this, adminid, adminfirst, adminlast, adminemail, adminpass);
        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(admininfo.this, "The Selected Value "+ position, Toast.LENGTH_LONG).show();
//                ida.setText(""+getAdmins.get(position));
//                String text[] = getAdmins.get(position).split(" | ");
//                firstnamea.setText(""+text[1]);
//                lastnamea.setText(""+text[2]);
//                username.setText(""+text[4]);
//                password.setText(""+text[5]);
//            }
//        });


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(firstnamea.getText().toString())){
                    firstnamea.setError("Input Firstname");
                }else if(TextUtils.isEmpty(lastnamea.getText().toString())){
                    lastnamea.setError("Input Lastname");
                }else if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Input BISU E-mail");
                }else if(TextUtils.isEmpty(password.getText().toString())){
                    password.setError("Input Password");
                }else{
                    dbo.insertAdmin(firstnamea.getText().toString(),lastnamea.getText().toString(), email.getText().toString(),password.getText().toString());
                    Toast.makeText(admininfo.this, "PRODUCT INSERTED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    ida.setText("");
                    firstnamea.setText("");
                    lastnamea.setText("");
                    email.setText("");
                    password.setText("");
                    refreshTable();
                    Intent intent=new Intent(admininfo.this, admininfo.class);
//                    intent.putExtra("F",f);
//                    intent.putExtra("L", l);
                    startActivity(intent);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbo.deleteAdmin(Integer.valueOf(ida.getText().toString()))){
                    Toast.makeText(admininfo.this, "CUSTOMER DELETED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    refreshTable();
                    Intent intent=new Intent(admininfo.this, admininfo.class);
//                    intent.putExtra("F",f);
//                    intent.putExtra("L", l);
                    startActivity(intent);
                }else{
                    Toast.makeText(admininfo.this, "DELETE FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
                update.setVisibility(View.INVISIBLE);
                save.setVisibility(View.VISIBLE);
                clear.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(firstnamea.getText().toString())){
                    firstnamea.setError("Input Firstname");
                }else if(TextUtils.isEmpty(lastnamea.getText().toString())){
                    lastnamea.setError("Input Lastname");
                }else if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Input BISU E-mail");
                }else if(TextUtils.isEmpty(password.getText().toString())){
                    password.setError("Input Password");
                }else{
                    dbo.updateAdmin(Integer.valueOf(ida.getText().toString()),firstnamea.getText().toString(),lastnamea.getText().toString(), email.getText().toString(),password.getText().toString());
                    Toast.makeText(admininfo.this, "ADMIN UPDATED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    ida.setText("");
                    firstnamea.setText("");
                    lastnamea.setText("");
                    email.setText("");
                    password.setText("");
                    refreshTable();
                    Intent intent=new Intent(admininfo.this, admininfo.class);
//                    intent.putExtra("F",f);
//                    intent.putExtra("L", l);
                    startActivity(intent);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ida.setText("");
                firstnamea.setText("");
                lastnamea.setText("");
                email.setText("");
                password.setText("");
                insert.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                save.setVisibility(View.INVISIBLE);
                clear.setVisibility(View.INVISIBLE);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ida.setText(adminid.get(position));
                firstnamea.setText(adminfirst.get(position));
                lastnamea.setText(adminlast.get(position));
                email.setText(adminemail.get(position));
                password.setText(adminpass.get(position));
                listViewposition = position;
            }
        });

    }
    class CustomerAdapter extends ArrayAdapter{
        ArrayList<String> arrayadminid;
        ArrayList<String> arrayadminf;
        ArrayList<String> arrayadminl;
        ArrayList<String> arrayadmine;
        ArrayList<String> arrayadminp;
        public CustomerAdapter(@NonNull Context context,
                               ArrayList<String> arrayadminid,
                               ArrayList<String> arrayadminf,
                               ArrayList<String> arrayadminl,
                               ArrayList<String> arrayadmine,
                               ArrayList<String> arrayadminp){
            super(context, R.layout.customadmin, R.id.idtext, arrayadminid);
            this.arrayadminid= arrayadminid;
            this.arrayadminf= arrayadminf;
            this.arrayadminl= arrayadminl;
            this.arrayadmine= arrayadmine;
            this.arrayadminp= arrayadminp;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.customadmin,parent, false);


            TextView ida=(TextView) row.findViewById(R.id.idtext);
            TextView fn=(TextView) row.findViewById(R.id.fna);
            TextView ln=(TextView) row.findViewById(R.id.lna);
            TextView ea=(TextView) row.findViewById(R.id.ea);
            TextView pw=(TextView) row.findViewById(R.id.pw);

            ida.setText(arrayadminid.get(position));
            fn.setText(arrayadminf.get(position));
            ln.setText(arrayadminl.get(position));
            ea.setText(arrayadmine.get(position));
            pw.setText(arrayadminp.get(position));
            return row;
        }

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
                Intent home= new Intent(admininfo.this, admin.class);
//                home.putExtra("F",f);
//                home.putExtra("L", l);
                startActivity(home);
                break;

            case R.id.customerinfo:
                Intent customerinfo=new Intent(admininfo.this, customerinfo.class);
//                customerinfo.putExtra("F",f);
//                customerinfo.putExtra("L", l);
                startActivity(customerinfo);
                break;

            case R.id.admininfo:
                Intent admininfo=new Intent(admininfo.this, admininfo.class);
//                admininfo.putExtra("F",f);
//                admininfo.putExtra("L", l);
                startActivity(admininfo);
                break;

            case R.id.productinfo:
                Intent productinfo=new Intent(admininfo.this, productinfo.class);
//                productinfo.putExtra("F",f);
//                productinfo.putExtra("L", l);
                startActivity(productinfo);
                break;

            case R.id.purchasedinfo:
                Intent purchasedinfo=new Intent(admininfo.this,reservedinfo.class);
//                purchasedinfo.putExtra("F",f);
//                purchasedinfo.putExtra("L", l);
                startActivity(purchasedinfo);
                break;

            case R.id.sales:
                Intent sale=new Intent(admininfo.this, sales.class);
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
                        Intent logout=new Intent(admininfo.this,adminlogin.class);
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

    public void refreshTable(){
        dbo=new DatabaseOperations(this);
        getAdmins = dbo.getAllAdmin();
        adminid = dbo.getAdminId("admin_id", orderBy);
        adminfirst = dbo.getAdminF("a_firstname", orderBy);
        adminlast = dbo.getAdminL("a_lastname", orderBy);
        adminemail = dbo.getAdminE("a_email", orderBy);
        adminpass = dbo.getAdminP("a_password", orderBy);

        CustomerAdapter adapter = new CustomerAdapter(this, adminid, adminfirst, adminlast, adminemail, adminpass);
        listview.setAdapter(adapter);
    }

}