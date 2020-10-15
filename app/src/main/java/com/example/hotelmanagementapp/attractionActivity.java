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
import android.widget.TextView;
import android.widget.Toast;

public class attractionActivity extends AppCompatActivity {
    //Declaration of variables
    Double price = 0.00;
    Integer hour = 0;
    TextView sauna,spa,pool,kindergarten,cinema,summarizeAdd,summarizeDel,summarize;
    Button payment;
    String sumPrice;
    double sumInt;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);
        payment = findViewById(R.id.paymentButton);

        noInternet();
    }
    //Action after pressing the button
    private boolean validation(TextView check) {
        if(check.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Nie wybrałeś liczby!",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void addToList(View view){
        //Find view by ID
        sauna = findViewById(R.id.saunaHour);
        spa = findViewById(R.id.spaHour);
        pool = findViewById(R.id.poolHour);
        kindergarten = findViewById(R.id.toyHour);
        cinema = findViewById(R.id.cinemaHour);
        summarizeAdd = findViewById(R.id.summarizePrice);
        //Action after pressing the button
        if(view == findViewById(R.id.saunaButton)){
            if(!validation(sauna)) {
                hour = Integer.parseInt(sauna.getText().toString());
                price = price + (15.00*hour);
            }
        }else if(view == findViewById(R.id.spaButton)){
            if(!validation(spa)) {
                hour = Integer.parseInt(spa.getText().toString());
                price = price + (100.00 * hour);
            }
        }else if(view == findViewById(R.id.poolButton)){
            if(!validation(pool)) {
                hour = Integer.parseInt(pool.getText().toString());
                price = price + (31.40 * hour);
            }
        }else if(view == findViewById(R.id.toyButton)){
            if(!validation(kindergarten)) {
                hour = Integer.parseInt(kindergarten.getText().toString());
                price = price + (0.00 * hour);
            }
        }else if(view == findViewById(R.id.cinemaButton)){
            if(!validation(cinema)) {
                hour = Integer.parseInt(cinema.getText().toString());
                price = price + (13.48 * hour);
            }
        }
        sumPrice = price.toString();
        //Set price text
        summarizeAdd.setText(sumPrice + " zł");
        sumInt = Double.parseDouble(sumPrice);
        if(sumInt>0.0) {
            payment.setEnabled(true);
        }
    }
    //Action after pressing the button
    public void deleteAttraction(View view) {
        //Find view by ID
        sauna = findViewById(R.id.saunaHour);
        spa = findViewById(R.id.spaHour);
        pool = findViewById(R.id.poolHour);
        kindergarten = findViewById(R.id.toyHour);
        cinema = findViewById(R.id.cinemaHour);
        summarizeDel = findViewById(R.id.summarizePrice);
        //Action after pressing the button
        if(view == findViewById(R.id.saunaDelete)){
            if(!validation(sauna)) {
                hour = Integer.parseInt(sauna.getText().toString());
                price = price - (15.00 * hour);
            }
        }else if(view == findViewById(R.id.spaDelete)){
            if(!validation(spa)) {
                hour = Integer.parseInt(spa.getText().toString());
                price = price - (100.00 * hour);
            }
        }else if(view == findViewById(R.id.poolDelete)){
            if(!validation(pool)) {
                hour = Integer.parseInt(pool.getText().toString());
                price = price - (31.40 * hour);
            }
        }else if(view == findViewById(R.id.toyDelete)){
            if(!validation(kindergarten)) {
                hour = Integer.parseInt(kindergarten.getText().toString());
                price = price - (0.00 * hour);
            }
        }else if(view == findViewById(R.id.cinemaDelete)){
            if(!validation(cinema)) {
                hour = Integer.parseInt(cinema.getText().toString());
                price = price - (13.48 * hour);
            }
        }

        sumPrice = price.toString();
        sumInt = Double.parseDouble(sumPrice);

        if(sumInt <= 0){
            payment.setEnabled(false);
            price = 0.0;
            summarizeDel.setText(price + " zł");
            Toast.makeText(getApplicationContext(),"Nie masz za co zapłacić",Toast.LENGTH_SHORT).show();
        }else if(sumInt > 0){
            payment.setEnabled(true);
            summarizeDel.setText(sumPrice+ " zł");
        }
    }
    //Action after pressing the button and go to another activity
    public void payForAttraction(View view) {
        Intent intent = new Intent(attractionActivity.this, paymentActivity.class);
        summarize = findViewById(R.id.summarizePrice);
        String sum = summarize.getText().toString();
        intent.putExtra("Key",sum);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Proszę czekać",Toast.LENGTH_SHORT).show();
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
