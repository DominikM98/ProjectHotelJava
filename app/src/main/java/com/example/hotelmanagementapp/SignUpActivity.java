package com.example.hotelmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    TextView login,pass;
    WebView webView;
    User user;
    DatabaseReference dbReff;
    long maxID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passwordText);
        String l, p;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                l= null;
                p= null;
            } else {
                l= extras.getString("login");
                p= extras.getString("password");
            }
        } else {
            l= (String) savedInstanceState.getSerializable("login");
            p= (String) savedInstanceState.getSerializable("password");
        }
        login.setText(l);
        pass.setText(p);
        dbReff = FirebaseDatabase.getInstance().getReference().child("User");
        dbReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxID = (snapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    //onClick for sign up button
    public void signUp(View view){

        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passwordText);
        String lgn = login.getText().toString();
        String psw = pass.getText().toString();
        if(TextUtils.isEmpty(lgn)){
            login.setError("Wprowadź numer telefonu");
            if(TextUtils.isEmpty(psw)){
                pass.setError("Wprowadź hasło");
                return;
            }
            return;
        }else if(login.length() < 9){
            login.setError("Numer jest za krótki");
        }else if(login.length() > 13){
            login.setError("Numer jest za długi");
        }else if(psw.length() < 8){
            pass.setError("Minimum 8 liter");
        }else{

            // Dodawanie użytkownika do bazy

            user = new User(lgn, psw);
            dbReff.child(String.valueOf(maxID+1)).setValue(user);

            start();
            Toast.makeText(getApplicationContext(),"Konto zostało utworzone",Toast.LENGTH_SHORT).show();
        }

    }

    public void start() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
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