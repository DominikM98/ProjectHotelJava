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
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagementapp.LoginActivity;
import com.example.hotelmanagementapp.MainActivity;
import com.example.hotelmanagementapp.R;

public class OrderDetails extends AppCompatActivity {
    //Declaration of variables

    TextView order,priceView,sum,finalSummarizePrice;
    String bill, allDisc;
    Double finalPrice,disc,finalRebate,priceDiscount;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        //Find view by ID
        order = findViewById(R.id.orderView);
        priceView = findViewById(R.id.priceView);
        sum = findViewById(R.id.discountSummarize);

        //Make bundle function
        Bundle bundle = getIntent().getExtras();
        bill = bundle.getString("Choices ");
        finalPrice = bundle.getDouble("Price ");
        allDisc = bundle.getString("Discount");
        //Set text
        order.setText("\n"+bill);
        priceView.setText(finalPrice.toString()+" zł");
        sum.setText(allDisc);
        checkIsNull();

        cutPrice();
        noInternet();
    }
    public void cutPrice(){
        finalSummarizePrice = findViewById(R.id.cutView);
        String withoutNumber = sum.getText().toString();

        if(withoutNumber == "0"){
            finalSummarizePrice.setText(String.format("%.2f",finalPrice)+" zł");
        }else{
            disc = Double.parseDouble(allDisc);
            priceDiscount = (finalPrice - (finalPrice * (disc*0.01)));
            finalSummarizePrice.setText(String.format("%.2f",priceDiscount)+" zł");
        }
    }
    public void checkIsNull(){
        String s = sum.getText().toString();
        if(s.isEmpty()){
            sum.setText("0");
        }else{
            sum.setText(allDisc);
        }
    }
    //Action after pressing the button
    public void sendOrder(View view) {
       Intent intent = new Intent(OrderDetails.this, reservationActivity.class);
       startActivity(intent);
       Toast.makeText(getApplicationContext(),"Wysyłanie zamówienia...",Toast.LENGTH_SHORT).show();
    }
    //Action after pressing the button - go to another activity
    public void payForFood(View view) {
        Intent intent = new Intent(OrderDetails.this, paymentActivity.class);
        intent.putExtra("Key",Double.toString(finalRebate));
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
