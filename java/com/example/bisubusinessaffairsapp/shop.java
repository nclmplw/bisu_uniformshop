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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class shop extends AppCompatActivity {
    TextView text, name, quan, date, size, price;
    ImageButton clear, pos, nega;
    Button totalbtn;
    int quantity=0;
    double total = 0;
    ListView listView;

    String nowDate;

    ArrayList<String> getAllProduct;
    ArrayList<String> productid;
    ArrayList<String> prdctname;
    ArrayList<String> prdctprice;
    ArrayList<String> prdctsize;

    String orderColumn = "product_id", orderSort = "ASC";
    String orderBy = orderColumn + " " + orderSort;
    int listViewposition;
    int p;
    String f, l;
    DatabaseOperations dbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        dbo = new DatabaseOperations(this);
        pos=(ImageButton) findViewById(R.id.pos);
        text=(TextView) findViewById(R.id.text);
        nega=(ImageButton) findViewById(R.id.nega);
        name=(TextView) findViewById(R.id.name);
        quan=(TextView) findViewById(R.id.quan);
        date=(TextView) findViewById(R.id.date);
        size= (TextView) findViewById(R.id.size);
        price=(TextView) findViewById(R.id.price);
        totalbtn = (Button) findViewById(R.id.total);
        clear = (ImageButton) findViewById(R.id.clear);
        listView=(ListView) findViewById(R.id.listview);

        productid= new ArrayList<>();
        prdctname=new ArrayList<>();
        prdctprice=new ArrayList<>();
        prdctsize=new ArrayList<>();


        getAllProduct = dbo.getAllProduct();
        productid = dbo.getProductId("product_id", orderBy);
        prdctname = dbo.getProductName("qproductname", orderBy);
        prdctprice = dbo.getProductPrice("qprice", orderBy);
        prdctsize = dbo.getProductSize("qsize", orderBy);

        f = getIntent().getExtras().getString("F");
        l = getIntent().getExtras().getString("L");

        text.setText("Total: ₱ 0.00");
        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Empty");
                } else if (TextUtils.isEmpty(price.getText().toString())) {
                    price.setError("Empty");
                } else if (TextUtils.isEmpty(size.getText().toString())) {
                    size.setError("Empty");
                } else {
                    String pr=price.getText().toString();
                    p=Integer.parseInt(pr);
                    text.setText("₱ " + p);
                    quantity = quantity + 1;
                    quan.setText("" + quantity);
                    total = p * quantity;
                    text.setText("Total: ₱ " + total);
                }
            }
        });

        nega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Empty");
                } else if (TextUtils.isEmpty(price.getText().toString())) {
                    price.setError("Empty");
                } else if (TextUtils.isEmpty(size.getText().toString())) {
                    size.setError("Empty");
                } else if(!(quantity >=1)){
                    quan.setError("Click here - or +");
                }else{
                    String pr=price.getText().toString();
                    p=Integer.parseInt(pr);
                    quantity = quantity-1;
                    text.setText("₱ " + p);
                    quan.setText(""+quantity);
                    total=p*quantity;
                    text.setText("Total: ₱ " + total);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
                name.setText("");
                quan.setText("0");
                size.setText("");
                price.setText("");
                text.setText("Total: ₱ 0.00");
            }
        });

        //calendar
        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        nowDate = simpleDateFormat.format(now.getTime()).toString();
        date.setText(nowDate);

        CustomAdapter adapter = new CustomAdapter(this, productid, prdctname, prdctprice, prdctsize);
        listView.setAdapter(adapter);

        totalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Empty");
                } else if (TextUtils.isEmpty(price.getText().toString())) {
                    price.setError("Empty");
                } else if (TextUtils.isEmpty(size.getText().toString())) {
                    size.setError("Empty");
                } else {
                    double prc= Double.valueOf(price.getText().toString());
                    total = prc*quantity;
                    AlertDialog.Builder builder=new AlertDialog.Builder(shop.this);
                    builder.setCancelable(false);
                    builder.setTitle("Buy");
                    builder.setMessage("Are you sure you want to buy this product?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbo.insertPurchase(f, l, name.getText().toString(), prc, quantity, size.getText().toString(), nowDate);
                            dbo.insertSales( name.getText().toString(), quantity, total);
                            Toast.makeText(shop.this, "THANK YOU FOR SHOPPING WITH US!", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            price.setText("");
                            size.setText("");
                            quan.setText("0");
                            date.setText("");
                            Intent productinfo = new Intent(shop.this, home.class);
//                            productinfo.putExtra("F",f);
//                            productinfo.putExtra("L", l);
                            startActivity(productinfo);
                        }
                    }) .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name.setText(prdctname.get(position));
                price.setText(prdctprice.get(position));
                size.setText(prdctsize.get(position));
                listViewposition = position;
            }
        });

    }

    class CustomAdapter extends ArrayAdapter{
        ArrayList<String> arrayproductid;
        ArrayList<String> arrayprdctname;
        ArrayList<String> arrayprdctprice;
        ArrayList<String> arrayprdctsize;
        public CustomAdapter(@NonNull Context context,
                             ArrayList<String> arrayproductid,
                             ArrayList<String> arrayprdctname,
                             ArrayList<String> arrayprdctprice,
                             ArrayList<String> arrayprdctsize) {
            super(context, R.layout.customproduct,R.id.idtext,arrayproductid);
            this.arrayproductid=arrayproductid;
            this.arrayprdctname=arrayprdctname;
            this.arrayprdctprice=arrayprdctprice;
            this.arrayprdctsize=arrayprdctsize;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.customproduct,parent,false);

            TextView idt=(TextView) row.findViewById(R.id.idtext);
            TextView productnamet=(TextView) row.findViewById(R.id.pn);
            TextView pricet=(TextView) row.findViewById(R.id.p);
            TextView sizet=(TextView) row.findViewById(R.id.s);

            idt.setText(arrayproductid.get(position));
            productnamet.setText(arrayprdctname.get(position));
            pricet.setText(arrayprdctprice.get(position));
            sizet.setText(arrayprdctsize.get(position));
            return row;
        }


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
                Intent home = new Intent(shop.this, home.class);
                home.putExtra("F", f);
                home.putExtra("L", l);
                startActivity(home);
                break;

            case R.id.shop:
                Intent shop = new Intent(shop.this, shop.class);
                shop.putExtra("F", f);
                shop.putExtra("L", l);
                startActivity(shop);
                break;

            case R.id.aboutus:
                Intent aboutus = new Intent(shop.this, aboutus.class);
                startActivity(aboutus);
                break;

            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Log out");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(shop.this, login.class);
                        startActivity(logout);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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