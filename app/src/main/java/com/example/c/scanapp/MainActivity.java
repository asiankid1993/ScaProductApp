package com.example.c.scanapp;

/**
 * Created by C on 3/08/2018.
 * App created to scan QR/Bar code of products
 * Due to the short amount of time given, App is only designed in vertical view, not horizontal view
 * App can only work for Android 6.0 and up
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button QRbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        QRbtn = (Button)findViewById(R.id.QRbutton);
        QRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRScannerPage();

            }
        });
    }

    public void QRScannerPage(){
        Intent intent;
        intent = new Intent(this, Scanner.class);
        startActivity(intent);
    }
}
