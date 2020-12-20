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

public class SnapActivity extends AppCompatActivity {
    //Declaration of variables
    String choices="",allDisc;
    Double price = 0.0;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap);

        discount();
        noInternet();
    }
    //Action after pressing the button
    public void addToList(View view){
        //Action after pressing the button
        if(view == findViewById(R.id.tatarButton)){
            choices = choices+"\t\t\t Tatar \n";
            price = price+15.00;
        }else if(view == findViewById(R.id.pastaButton)){
            choices = choices+"\t\t\t Pasta z pieczonej dyni z curry \n";
            price = price+13.00;
        }else if(view == findViewById(R.id.schabowyButton)){
            choices = choices+"\t\t\t Schabowy z ziemniakami i mizerią \n";
            price = price+21.00;
        }else if(view == findViewById(R.id.rosolButton)){
            choices = choices+"\t\t\t Rosół z makaronem \n";
            price = price+12.00;
        }else if(view == findViewById(R.id.grillButton)){
            choices = choices+"\t\t\t Grillowane mięsa z frytkami \n";
            price = price+24.50;
        }else if(view == findViewById(R.id.sernikButton)){
            choices = choices+"\t\t\t Sernik z lodami waniliowymi \n";
            price = price+11.95;
        }else if(view == findViewById(R.id.pepsiButton)){
            choices = choices+"\t\t\t Pepsi/7up/Mirinda 0.3ml \n";
            price = price+7.00;
        }else if(view == findViewById(R.id.jackButton)){
            choices = choices+"\t\t\t Jack Daniels 0.4cl \n";
            price = price+15.00;
        }
    }
    public void discount(){
        TextView sum = findViewById(R.id.textView100);
        Intent intent = getIntent();
        allDisc = intent.getStringExtra("Discount");
        sum.setText(allDisc);

        String s = sum.getText().toString();
        if(s.isEmpty()){
            allDisc = "0";
            sum.setText(allDisc+"%");
            allDisc = intent.getStringExtra("Discount");
        }else{
            allDisc = intent.getStringExtra("Discount");
            sum.setText(allDisc+"%");
        }
    }

    //Action after pressing the button and data transfer
    public void placeOrder(View view){
        Intent intent = new Intent(SnapActivity.this, OrderDetails.class);
        Bundle bundle = new Bundle();
        bundle.putString("Choices ",choices);
        bundle.putDouble("Price ",price);
        bundle.putString("Discount",allDisc);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Ładowanie rachunku",Toast.LENGTH_SHORT).show();
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
