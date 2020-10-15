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

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class forgotActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        //Find view by ID
        TextView forgot = findViewById(R.id.forgotView);
        TextView forgot2 = findViewById(R.id.forgotView2);
        //Set text
        forgot.setText("Uwaga!\nTrwają prace nad tym elementem. Na szczęście w kążdej chwili możesz udać się na recepcję lub zadzwoń na ten numer telefonu: 14 655 87 65, podaj unikatowy kod i dowiedz się jakie jest twoje hasło! Twoje hasło odświerza się co 20 minut");
        forgot2.setText("Jeśli zapomniałeś lub zgubiłeś hasło udaj się na recepcję o hasło. \nNależy jak najszybciej zmienić hasło, udając się na recepcję, aby twoje hasło nie znalazło się w niepowołanych rękach.");
        //Function call
        refreshTime();
        noInternet();
    }
    //Refresh Time
    public void refreshTime(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        generateUniqueCode();
                    }
                });
            }
        },0,60*20000);
    }
    //generate Unique Code
    public void generateUniqueCode(){
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int number = random.nextInt(max - min +1) +min;
        TextView unique = findViewById(R.id.uniqueCode);
        String s = String.valueOf(number);
        unique.setText(s);
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
