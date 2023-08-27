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

public class reservedinfo extends AppCompatActivity {

    TextView idp;
    EditText firstname, lastname, productname, price, quantity, size, date;
    ImageButton delete, update, save, clear;
    ListView listview;
    ArrayList<String> getAllPurchase;
    ArrayList<String> purchaseid;
    ArrayList<String> purchasefirst;
    ArrayList<String> purchaselast;
    ArrayList<String> purchasename;
    ArrayList<String> purchaseprice;
    ArrayList<String> purchasesize;
    ArrayList<String> purchasequan;
    ArrayList<String> purchasedate;

    String orderColumn = "purchase_id",orderSort="ASC";
    String orderBy= orderColumn+" "+orderSort;
    int listViewposition;

//
//    String f=getIntent().getExtras().getString("F");
//    String l=getIntent().getExtras().getString("L");
    DatabaseOperations dbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservedinfo);
        idp=(TextView) findViewById(R.id.ids);
        firstname=(EditText) findViewById(R.id.firstname);
        lastname=(EditText) findViewById(R.id.lastname);
        productname=(EditText) findViewById(R.id.prdctname);
        price=(EditText) findViewById(R.id.num);
        quantity=(EditText) findViewById(R.id.quan);
        size=(EditText) findViewById(R.id.size);
        date=(EditText) findViewById(R.id.date);
        delete=(ImageButton) findViewById(R.id.delete);
        update=(ImageButton) findViewById(R.id.update);
        save=(ImageButton) findViewById(R.id.save);
        clear=(ImageButton) findViewById(R.id.clear);
        listview=(ListView) findViewById(R.id.listview);
        dbo = new DatabaseOperations(this);

        getAllPurchase= new ArrayList<>();
        purchaseid= new ArrayList<>();
        purchasefirst= new ArrayList<>();
        purchaselast=new ArrayList<>();
        purchasename= new ArrayList<>();
        purchaseprice= new ArrayList<>();
        purchasesize= new ArrayList<>();
        purchasequan= new ArrayList<>();
        purchasedate= new ArrayList<>();

        getAllPurchase=dbo.getAllPurchase();
        purchaseid=dbo.getPurchaseId("purchase_id", orderBy);
        purchasefirst=dbo.getPurchaseFN("c_firstname", orderBy);
        purchaselast=dbo.getPurchaseLN("c_lastname", orderBy);
        purchasename=dbo.getPurchasePN("qproductname", orderBy);
        purchaseprice=dbo.getPurchasePP("qprice", orderBy);
        purchasequan=dbo.getPurchasePQ("qquantity", orderBy);
        purchasesize=dbo.getPurchasePS("qsize", orderBy);
        purchasedate=dbo.getPurchasePD("qdate", orderBy);

        CustomerAdapter customerAdapter= new CustomerAdapter(this, purchaseid,purchasefirst, purchaselast, purchasename, purchaseprice, purchasequan, purchasesize, purchasedate);
        listview.setAdapter(customerAdapter);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbo.deletePurchase(Integer.valueOf(idp.getText().toString()))){
                    Toast.makeText(reservedinfo.this, "PURCHASED DELETED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    refreshTable();
                    Intent intent=new Intent(reservedinfo.this, reservedinfo.class);
//                    intent.putExtra("F",f);
//                    intent.putExtra("L", l);
                    startActivity(intent);
                }else{
                    Toast.makeText(reservedinfo.this, "DELETE FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.setVisibility(View.INVISIBLE);
                update.setVisibility(View.INVISIBLE);
                save.setVisibility(View.VISIBLE);
                clear.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(firstname.getText().toString())){
                    firstname.setError("Input Firstname");
                }else if(TextUtils.isEmpty(lastname.getText().toString())){
                    lastname.setError("Input Lastname");
                }else if(TextUtils.isEmpty(productname.getText().toString())){
                    productname.setError("Input Product Name");
                }else if(TextUtils.isEmpty(price.getText().toString())){
                    price.setError("Input Price");
                } else if(TextUtils.isEmpty(quantity.getText().toString())){
                    quantity.setError("Input Quantity");
                }else if(TextUtils.isEmpty(size.getText().toString())){
                    size.setError("Input Size");
                } else if(TextUtils.isEmpty(date.getText().toString())){
                    date.setError("Input Date");
                }else{
                    dbo.updatePurchase(Integer.valueOf(idp.getText().toString()),firstname.getText().toString(),lastname.getText().toString(), productname.getText().toString(),Double.valueOf(price.getText().toString()), Integer.valueOf(quantity.getText().toString()), size.getText().toString(), date.getText().toString());
                    Toast.makeText(reservedinfo.this, "PRUCHASED UPDATED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    idp.setText("");
                    firstname.setText("");
                    lastname.setText("");
                    productname.setText("");
                    price.setText("");
                    quantity.setText("");
                    size.setText("");
                    date.setText("");
                    refreshTable();
                    Intent intent=new Intent(reservedinfo.this, reservedinfo.class);
//                    intent.putExtra("F",f);
//                    intent.putExtra("L", l);
                    startActivity(intent);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idp.setText("");
                firstname.setText("");
                lastname.setText("");
                productname.setText("");
                price.setText("");
                quantity.setText("");
                size.setText("");
                date.setText("");
                delete.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                save.setVisibility(View.INVISIBLE);
                clear.setVisibility(View.INVISIBLE);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idp.setText(purchaseid.get(position));
                firstname.setText(purchasefirst.get(position));
                lastname.setText(purchaselast.get(position));
                productname.setText(purchasename.get(position));
                price.setText(purchaseprice.get(position));
                quantity.setText(purchasequan.get(position));
                size.setText(purchasesize.get(position));
                date.setText(purchasedate.get(position));
                listViewposition = position;
            }
        });

    }
    class CustomerAdapter extends ArrayAdapter {

        ArrayList<String> arraypurchaseid;
        ArrayList<String> arraypurchasefirst;
        ArrayList<String> arraypurchaselast;
        ArrayList<String> arraypurchasename;
        ArrayList<String> arraypurchaseprice;
        ArrayList<String> arraypurchasesize;
        ArrayList<String> arraypurchasequan;
        ArrayList<String> arraypurchasedate;
        public CustomerAdapter(@NonNull Context context,
                               ArrayList<String> arraypurchaseid,
                                       ArrayList<String> arraypurchasefirst,
                                       ArrayList<String> arraypurchaselast,
                                       ArrayList<String> arraypurchasename,
                                       ArrayList<String> arraypurchaseprice,
                                       ArrayList<String> arraypurchasesize,
                                       ArrayList<String> arraypurchasequan,
                                       ArrayList<String> arraypurchasedate){
            super(context, R.layout.custompurchase, R.id.idtext,arraypurchaseid);
            this.arraypurchaseid= arraypurchaseid;
            this.arraypurchasefirst= arraypurchasefirst;
            this.arraypurchaselast= arraypurchaselast;
            this.arraypurchasename= arraypurchasename;
            this.arraypurchaseprice= arraypurchaseprice;
            this.arraypurchasesize= arraypurchasesize;
            this.arraypurchasequan= arraypurchasequan;
            this.arraypurchasedate= arraypurchasedate;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.custompurchase,parent, false);


            TextView ida=(TextView) row.findViewById(R.id.idtext);
            TextView fn=(TextView) row.findViewById(R.id.firsttext);
            TextView ln=(TextView) row.findViewById(R.id.lasttext);
            TextView nsb=(TextView) row.findViewById(R.id.ngalansabaligya);
            TextView ps=(TextView) row.findViewById(R.id.presyo);
            TextView pk=(TextView) row.findViewById(R.id.pilakabook);
            TextView s=(TextView) row.findViewById(R.id.sukod);
            TextView pt=(TextView) row.findViewById(R.id.petsa);

            ida.setText(arraypurchaseid.get(position));
            fn.setText(arraypurchasefirst.get(position));
            ln.setText(arraypurchaselast.get(position));
            nsb.setText(arraypurchasename.get(position));
            ps.setText(arraypurchaseprice.get(position));
            pk.setText(arraypurchasequan.get(position));
            s.setText(arraypurchasesize.get(position));
            pt.setText(arraypurchasedate.get(position));
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
                Intent home= new Intent(reservedinfo.this, admin.class);
//                home.putExtra("F",f);
//                home.putExtra("L", l);
                startActivity(home);
                break;

            case R.id.customerinfo:
                Intent customerinfo=new Intent(reservedinfo.this, customerinfo.class);
//                customerinfo.putExtra("F",f);
//                customerinfo.putExtra("L", l);
                startActivity(customerinfo);
                break;

            case R.id.admininfo:
                Intent admininfo=new Intent(reservedinfo.this, admininfo.class);
//                admininfo.putExtra("F",f);
//                admininfo.putExtra("L", l);
                startActivity(admininfo);
                break;

            case R.id.productinfo:
                Intent productinfo=new Intent(reservedinfo.this, productinfo.class);
//                productinfo.putExtra("F",f);
//                productinfo.putExtra("L", l);
                startActivity(productinfo);
                break;

            case R.id.purchasedinfo:
                Intent purchasedinfo=new Intent(reservedinfo.this,reservedinfo.class);
//                purchasedinfo.putExtra("F",f);
//                purchasedinfo.putExtra("L", l);
                startActivity(purchasedinfo);
                break;

            case R.id.sales:
                Intent sale=new Intent(reservedinfo.this, sales.class);
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
                        Intent logout=new Intent(reservedinfo.this,adminlogin.class);
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

    }
}