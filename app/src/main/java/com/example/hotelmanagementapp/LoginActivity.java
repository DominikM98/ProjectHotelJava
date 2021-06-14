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
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    TextView login, pass;
    WebView webView;
    //DatabaseReference dbRef;
    static boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        noInternet();
        //dbRef = FirebaseDatabase.getInstance().getReference().child("User");
        loggedIn = false;
    }

    //Action after pressing the button
    public void forgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this,forgotActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Przypominanie hasła",Toast.LENGTH_SHORT).show();
    }

    //Action after pressing the button
    public void createAccount(View view){
        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passwordText);
        intent.putExtra("login", login.getText().toString());
        intent.putExtra("password", pass.getText().toString());
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Utwórz konto",Toast.LENGTH_SHORT).show();
    }

    public void tryLogin(){
        login = findViewById(R.id.loginText);
        pass = findViewById(R.id.passwordText);
        String lgn = login.getText().toString();
        String psw = pass.getText().toString();

        String url = "http://10.0.2.2:1485/api/users/login/"+lgn+"/"+psw;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String option = response.body().string();
                if(option.equals("OK")){
                    loggedIn = true;
                    start();
                }
                else{
                    //TOAST? NOT WORKING
                }
            }
        });

        /* Firebase stuff
        FirebaseDatabase.getInstance().getReference().child("User")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            //if login in database
                            if(user.getLogin().equals(login.getText().toString().trim())){
                                //if password matches start new activity
                                if(user.getPassword().equals(pass.getText().toString().trim())){
                                    loggedIn = true;
                                    start();
                                    Toast.makeText(getApplicationContext(),"Logowanie udane!",Toast.LENGTH_SHORT).show();
                                }
                                //otherwise notify about incorrect login or password
                                else{
                                    loggedIn = false;
                                    Toast.makeText(getApplicationContext(),"Błędne hasło!",Toast.LENGTH_SHORT).show();
                                }
                                //return when login was found and action was executed
                                return;
                            }
                            //when login not found
                            else{
                                loggedIn = false;
                                Toast.makeText(getApplicationContext(),"Nie ma takiego użytkownika",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });*/
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
            }
        }else if(login.length() < 9){
            login.setError("Numer jest za krótki");
        }else if(login.length() > 13){
            login.setError("Numer jest za długi");
        }else if(passw.length() < 8){
            pass.setError("Minimum 8 liter");
        }else{
            tryLogin();
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
