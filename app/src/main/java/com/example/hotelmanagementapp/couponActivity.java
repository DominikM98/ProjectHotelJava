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
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class couponActivity extends AppCompatActivity {

    EditText javaQ,numberQ;
    TextView hintQ, fullDisc,answerQ,percent;
    String answer,summarize,uc;
    Integer discount = 0,i = 1,number,digit;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        generateNumber();
        noInternet();
    }

    public void questions(View view){
        javaQuestion();
        numberQuestion();

        if(discount > 8){
            Toast.makeText(getApplicationContext(),"Nie może być więcej niż 8% rabatu!",Toast.LENGTH_SHORT).show();
            discount = 8;
        }
        fullDisc = findViewById(R.id.fullDiscount);
        summarize = discount.toString();
        fullDisc.setText(summarize+"%");

    }

    public void goToMenuRestaurant(View view){
        Intent intent = new Intent(this,SnapActivity.class);
        intent.putExtra("Discount",summarize);
        startActivity(intent);
    }

    public void javaQuestion(){
        javaQ = findViewById(R.id.javaQuestion);
        answerQ = findViewById(R.id.answerView);
        answer = javaQ.getText().toString();

        if(answer.isEmpty()){
            answerQ.setText("Brak odpowiedzi");
        }else if (answer.equals("James Gosling")){
            answerQ.setText("Gratulacje!");
            discount = 2;
            disableEditText(javaQ);
        }else{
            answerQ.setText("Błąd! On/Ona nie jest twórcą");
            disableEditText(javaQ);
        }
    }

    public void numberQuestion(){
        numberQ = findViewById(R.id.numberQuestion);
        hintQ = findViewById(R.id.hintQuestion);
        percent = findViewById(R.id.percentDiscount);
        String empty = numberQ.getText().toString();

        number = Integer.parseInt(numberQ.getText().toString());
        if(empty.isEmpty()){
            numberQ.setError("Brak liczby");
        }else if(number > 10 || number < 1){
            hintQ.setText("Liczba jest poza zakresem!");
        }else if (number == digit){
            hintQ.setText("Wylosowałeś dobrą liczbę za "+i+" razem. Otrzymujesz (poniżej):");
            if(i == 1){
                percent.setText("6% rabatu");
                discount = discount + 6;
            }else if(i == 2){
                percent.setText("4% rabatu");
                discount = discount + 4;
            } else if(i == 3){
                percent.setText("2% rabatu");
                discount = discount + 2;
            }else{
                percent.setText("0% rabatu");
                discount = discount +0;
            }
        }else if(number != digit){
            i +=1;
            hintQ.setText("Nie trafiłeś. Spróbuj ponownie :)");
            if (number > digit){
                hintQ.setText("Twoja liczba jest większa niż wylosowana");
            }else if(number < digit){
                hintQ.setText("Twoja liczba jest mniejsza niż wylosowana");
            }
        }
    }

    public void generateNumber(){
        Random random = new Random();
        int min = 1;
        int max = 10;
        digit = random.nextInt(max - min +1) +min;
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
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
