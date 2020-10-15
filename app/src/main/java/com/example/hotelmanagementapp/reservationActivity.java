package com.example.hotelmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class reservationActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        noInternet();
    }
    //Action after pressing the button
    public void LogOut(View view) {
        Intent intent = new Intent(reservationActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Wylogowywanie się",Toast.LENGTH_SHORT).show();
    }
    //Action after pressing the button
    public void aboutUs(View view) {
        Intent intent = new Intent(reservationActivity.this,aboutActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Ładowanie...",Toast.LENGTH_SHORT).show();
    }
    //Action after pressing the button
    public void goToRestaurant(View view) {
        Intent intent = new Intent(reservationActivity.this,SnapActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Czas coś zjeść!",Toast.LENGTH_SHORT).show();
    }
    //Action after pressing the button
    public void attraction(View view) {
        Intent intent = new Intent(reservationActivity.this, attractionActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Ładowanie...",Toast.LENGTH_SHORT).show();
    }
    //Action after pressing the button
    public void newReservation(View view) {
        Intent intent = new Intent(reservationActivity.this,createReservationLoginActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Ładowanie...",Toast.LENGTH_SHORT).show();
    }
    //Action after pressing the button
    public void coupon(View view) {
        Intent intent = new Intent(reservationActivity.this,couponActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Ładowanie...",Toast.LENGTH_SHORT).show();
    }
    public void noInternet(){
        webView = findViewById(R.id.noInternet);

        //Initialize webSetting
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button btn = dialog.findViewById(R.id.noInternetButton);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });

            dialog.show();
        }
    }
}
