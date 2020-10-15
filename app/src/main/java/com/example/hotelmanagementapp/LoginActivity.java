package com.example.hotelmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView login,pass;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        noInternet();
    }
    //Action after pressing the button
    public void forgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this,forgotActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Przypominanie hasła",Toast.LENGTH_SHORT).show();
    }
    //Action after pressing the button
    public void signIn(View view) {
        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passwordText);
        String str = login.getText().toString();
        String passw = pass.getText().toString();
        //Checking if view is empty and not enough data
        if(TextUtils.isEmpty(str)){
            login.setError("Wprowadź numer telefonu");
            if(TextUtils.isEmpty(passw)){
                pass.setError("Wprowadź hasło");
                return;
            }
            return;
        }else if(login.length() < 9){
            login.setError("Numer jest za krótki");
        }else if(login.length() > 13){
            login.setError("Numer jest za długi");
        }else if(passw.length() < 8){
            pass.setError("Minimum 8 liter");
        }else{
            start();
            Toast.makeText(getApplicationContext(),"Logowanie udane",Toast.LENGTH_SHORT).show();
        }
    }
    public void start() {
        Intent intent = new Intent(LoginActivity.this,reservationActivity.class);
        startActivity(intent);
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
