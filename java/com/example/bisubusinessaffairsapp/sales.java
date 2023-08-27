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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class sales extends AppCompatActivity {
    TextView ids, idtext, prtext, number, amount;
    EditText productname, num, amt;
    ImageButton insert, delete, update, save, clear;
    ListView listView;
    ArrayList<String> getAllSales;
    ArrayList<String> salesid;
    ArrayList<String> prodctname;
    ArrayList<String> salesnum;
    ArrayList<String> salesamt;
    String orderColumn = "sale_id",orderSort = "ASC";
    String orderBy = orderColumn +" "+orderSort;
    int listViewposition;


//    String f,l;
    DatabaseOperations dbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        idtext=(TextView) findViewById(R.id.idtext);
        prtext=(TextView) findViewById(R.id.prtext);
        number=(TextView) findViewById(R.id.number);
        amount=(TextView) findViewById(R.id.amount);
        insert=(ImageButton) findViewById(R.id.insert);
        ids= (TextView) findViewById(R.id.ids);
        delete=(ImageButton) findViewById(R.id.delete);
        update=(ImageButton) findViewById(R.id.update);
        save=(ImageButton) findViewById(R.id.save);
        clear=(ImageButton) findViewById(R.id.clear);
        listView=(ListView) findViewById(R.id.listview);


        dbo = new DatabaseOperations(this);
//        f=getIntent().getExtras().getString("F");
//        l=getIntent().getExtras().getString("L");

         getAllSales=new ArrayList<>();
         salesid=new ArrayList<>();
         prodctname=new ArrayList<>();
         salesnum=new ArrayList<>();
         salesamt=new ArrayList<>();

         getAllSales=dbo.getAllSales();
        salesid=dbo.getSalesId("sale_id", orderBy);
        prodctname=dbo.getSalePN("qproductname", orderBy);
        salesnum=dbo.getNumSold("qnumsold", orderBy);
        salesamt=dbo.getAmntSold("qamntsold", orderBy);

        CustomAdapter adapter=new CustomAdapter(this, salesid, prodctname, salesnum, salesamt);
        listView.setAdapter(adapter);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbo.deleteProduct(Integer.valueOf(ids.getText().toString()))){
                    Toast.makeText(sales.this, "PRODUCT DELETED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    ids.setText("");
                    productname.setText("");
                    num.setText("");
                    amt.setText("");
                    refreshTable();

                    Intent sale=new Intent(sales.this, sales.class);
                    startActivity(sale);
                }else{
                    Toast.makeText(sales.this, "DELETE FAILED", Toast.LENGTH_SHORT).show();
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
                if(TextUtils.isEmpty(productname.getText().toString())){
                    productname.setError("Input Product Name");
                }else if(TextUtils.isEmpty(num.getText().toString())){
                    num.setError("Input Number of Sold Product");
                }else if(TextUtils.isEmpty(amt.getText().toString())){
                    amt.setError("Input Amount of Sold Product");
                }else{
                    dbo.updateSales(Integer.valueOf(ids.getText().toString()),productname.getText().toString(),Integer.valueOf(num.getText().toString()),Double.valueOf(amt.getText().toString()));
                    Toast.makeText(sales.this, "PRODUCT UPDATED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    ids.setText("");
                    productname.setText("");
                    num.setText("");
                    amt.setText("");
                    refreshTable();

                    Intent sale=new Intent(sales.this, productinfo.class);
                    startActivity(sale);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ids.setText("");
                productname.setText("");
                num.setText("");
                amt.setText("");
                delete.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                save.setVisibility(View.INVISIBLE);
                clear.setVisibility(View.INVISIBLE);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ids.setText(salesid.get(position));
                productname.setText(prodctname.get(position));
                num.setText(salesnum.get(position));
                amt.setText(salesamt.get(position));
                listViewposition = position;
            }
        });
    }

    class CustomAdapter extends ArrayAdapter {
        ArrayList<String> arraysalesid;
        ArrayList<String> arrayprdctname;
        ArrayList<String> arraynumsale;
        ArrayList<String> arrayamtsale;

        public CustomAdapter(@NonNull Context context,
                             ArrayList<String> arraysalesid,
                             ArrayList<String> arrayprdctname,
                             ArrayList<String> arraynumsale,
                             ArrayList<String> arrayamtsale) {
            super(context, R.layout.customsales, R.id.idtext, arraysalesid);
            this.arraysalesid = arraysalesid;
            this.arrayprdctname = arrayprdctname;
            this.arraynumsale = arraynumsale;
            this.arrayamtsale = arrayamtsale;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.customsales, parent, false);

            TextView idt = (TextView) row.findViewById(R.id.idtext);
            TextView productnamet = (TextView) row.findViewById(R.id.prtext);
            TextView pricet = (TextView) row.findViewById(R.id.number);
            TextView sizet = (TextView) row.findViewById(R.id.amount);

            idt.setText(arraysalesid.get(position));
            productnamet.setText(arrayprdctname.get(position));
            pricet.setText(arraynumsale.get(position));
            sizet.setText(arrayamtsale.get(position));
            return row;
        }

    }
        public void refreshTable(){

            getAllSales=dbo.getAllSales();
            salesid=dbo.getSalesId("sale_id", orderBy);
            prodctname=dbo.getSalePN("qproductname", orderBy);
            salesnum=dbo.getNumSold("qnumsold", orderBy);
            salesamt=dbo.getAmntSold("qamntsold", orderBy);

            CustomAdapter adapter=new CustomAdapter(this, salesid, prodctname, salesnum, salesamt);
            listView.setAdapter(adapter);
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
                Intent home= new Intent(sales.this, admin.class);
//                home.putExtra("ID",intid);
                startActivity(home);
                break;

            case R.id.customerinfo:
                Intent customerinfo=new Intent(sales.this, customerinfo.class);
//                customerinfo.putExtra("ID",intid);
                startActivity(customerinfo);
                break;

            case R.id.admininfo:
                Intent admininfo=new Intent(sales.this, admininfo.class);
//                admininfo.putExtra("ID",intid);
                startActivity(admininfo);
                break;

            case R.id.productinfo:
                Intent productinfo=new Intent(sales.this, productinfo.class);
//                productinfo.putExtra("ID",intid);
                startActivity(productinfo);
                break;

            case R.id.purchasedinfo:
                Intent purchasedinfo=new Intent(sales.this,reservedinfo.class);
//                purchasedinfo.putExtra("ID",intid);
                startActivity(purchasedinfo);
                break;

            case R.id.sales:
                Intent sale=new Intent(sales.this, sales.class);
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
                        Intent logout=new Intent(sales.this,adminlogin.class);
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