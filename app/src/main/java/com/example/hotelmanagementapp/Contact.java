package com.example.hotelmanagementapp;

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

import androidx.appcompat.app.AppCompatActivity;

public class Contact extends AppCompatActivity {

    TextView aboutUs,contactView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_contact);

        aboutUs = findViewById(R.id.aboutUs);
        aboutUs.setText("Hotel 'Java' to jeden z najbardziej znanych hoteli na całym świecie. Nazwa pochodzi od języka programistycznego, który został użyty do oprogramowania systemu naszego hotelu.\n Niedaleko hotelu znajduje się sklep spożywczy Lidl oraz Auchan. Dodatkowo w wolnym czasie można udać się do lasku lipie");

        contactView = findViewById(R.id.contactView);
        contactView.setText("tel: +48 14 630 55 12 \n email: recepcja@hotel.java.pl");

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

    public void goToRating(View view) {
        Intent intent = new Intent(Contact.this,aboutActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Przechodzenie do oceny",Toast.LENGTH_SHORT).show();
    }

    public void howToDrive(View view) {
        Intent intent = new Intent(Contact.this,HowToGet.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Droga do nas",Toast.LENGTH_SHORT).show();
    }

    public void contactWithSection(View view) {
        Intent intent = new Intent(Contact.this,ContactSection.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Kontakt z działami",Toast.LENGTH_SHORT).show();
    }
}
