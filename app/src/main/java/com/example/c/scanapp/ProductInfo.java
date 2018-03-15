package com.example.c.scanapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by C on 3/12/2018.
 */

public class ProductInfo extends AppCompatActivity implements AsyncResponse{
    TextView productName, productAmount, productPrice,productCode;
    Button update;

    private String codeResult ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product);

        String productResult = getIntent().getStringExtra("result");
        String code = getIntent().getStringExtra("productCode");
        codeResult = code;
        productName = (EditText) findViewById(R.id.ProductName);
        productAmount = (EditText) findViewById(R.id.ProductAmount);
        productPrice = (EditText) findViewById(R.id.ProductPrice);
        productCode = (TextView) findViewById(R.id.ProductCode);
        update = (Button) findViewById(R.id.update);

        String[] productResults = productResult.split(",");

        //product name
        String[] nameResult = productResults[0].split(":");

        //product amount
        String[] amountResult = productResults[1].split(":");

        //product price
        String[] priceResult = productResults[2].split(":");

        //Set data given from API into individual Text Fields
        productName.setText(nameResult[1]);
        productAmount.setText(amountResult[1]);
        productPrice.setText(priceResult[1]);
        productCode.setText(code);

        //Update Button to call update function
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    public void updateData(){
        //Get updated Name, Amount, and Price
        String name = productName.getText().toString();
        String amount = productAmount.getText().toString();
        String price = productPrice.getText().toString();

        //Send Post request to API to make updates to Database
        CheckProduct checkProduct = new CheckProduct(this);
        checkProduct.execute("update", name, amount, price, codeResult);

        //Result returned from API
        String scanResult = "";
        try {
            scanResult = checkProduct.get().toString();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        //Print out what was returned from API
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        if(scanResult.equals("Update Successfully")) {
            builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //redirect to Scanner page
                    finish();
                }
            });
        }else{
            builder.setPositiveButton("Cancel",null);
        }


        builder.setMessage(scanResult);
        AlertDialog alert1 = builder.create();
        alert1.show();


    }

    @Override
    public void processFinish(String output){

    }



}