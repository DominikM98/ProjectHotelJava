package com.example.hotelmanagementapp;

import android.app.Dialog;
import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;

public class ContactSection extends AppCompatActivity {

    WebView webView;
    TextView receptionView, restaurantView, dyrView, errorView, marketingView;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_contact_section);

        receptionView = findViewById(R.id.receptionView);
        restaurantView = findViewById(R.id.restaurantView);
        dyrView = findViewById(R.id.dyrView);
        errorView = findViewById(R.id.errorView);
        marketingView = findViewById(R.id.marketingView);

        receptionView.setText("tel: +48 14 630 55 12 \n email: recepcja@hotel.java.pl");
        restaurantView.setText("tel: +48 14 630 55 13 \n email: restauracja@hotel.java.pl");
        dyrView.setText("tel: +48 14 630 55 01 \n email: dyrektor@hotel.java.pl");
        errorView.setText("email: zglos.blad@hotel.java.pl");
        marketingView.setText("tel: +48 14 630 55 10 \n email: marketing@hotel.java.pl");
        noInternet();
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
