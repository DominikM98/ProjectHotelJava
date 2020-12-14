package com.example.hotelmanagementapp;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ChatActivity extends AppCompatActivity {

    TextView userMsg, botMsg;
    EditText userTxt;
    String text;
    String tel_recepcji = "999 000 999";
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    public void sendMsg(View view) {
        userMsg = findViewById(R.id.userMsg);
        userTxt = findViewById(R.id.enterMessage);
        text = userTxt.getText().toString();
        userMsg.setText(text);
        userMsg.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), getString(R.string.wait), Toast.LENGTH_SHORT).show();
        bot();
    }

    @SuppressLint("SetTextI18n")
    private void bot() {
        botMsg = findViewById(R.id.botMsg);
        botMsg.setVisibility(View.VISIBLE);
        if(search("rezerwacja", "rezerwacją", "rezerw", "recepcja")) {
            botMsg.setText(" Witaj! \n Jeśli masz jakieś pytania odnośnie rezerwacji, skontaktuj się z recepcją, nr tel:" + tel_recepcji);
        }
        else if(search("logowanie", "logować", "logowac", "login")) {
            botMsg.setText(" Witaj! \n Zalogowanie do aplikacji jest konieczne w celu autoryzacji i ochrony danych osobowych. \n \n " +
                    " Jeśli nie masz konta skontaktuj się z recepcją, nr tel:" + tel_recepcji + "\n \n Jeśli nie pamiętasz hasła użyj przyciusku 'Zapomniałem hasła'");
        }
        else if(search("hasła", "hasło", "haslo", "hasla")) {
            botMsg.setText(" Witaj! \n Jeżeli zapomniałeś hasłą możesz użyć przycisku pod logowaniem. \n \n Jeśli mimo to nie możesz się zalogować lub nie zakładałeś konta skontaktuj" +
                    " się z recepcją nr tel: " +tel_recepcji);
        }
        else if(search("kod rabatowy", "rabat", "zniżka", "znizka")) {
            botMsg.setText(" Witaj! \n Jeżeli chciałbyć uzyskać kod rabatowy rozwiąż zadania dostępne w zakładce 'Kod rabatowy'.\n Kod obejmuje wszystkie dostępne usługi :)" +
                    "\n \n Jeśli masz problem z naliczaniem rabatu skontaktuj się administratorem: hoteljava@admin.pl lub z recepcją nr tel: " +tel_recepcji);
        }
        else if(search("restauracja", "jedzenie", "zamów", "zamow")) {
            botMsg.setText(" Witaj! \n W zakładce 'Restauracja' możesz zamówić i opłacić zamówienie z hotelowej restauracji.\n" +
                    " Jesli posiadasz kod rabatowy, obejmuje on również zamówienia w restauracji \n \n " +
                    "Jeśli masz problem ze swoim zamówieniem, skontaktuj się z kuchnią numerem wewnętrzym lub z recepcją nr tel: " +tel_recepcji);
        }
        else {
            botMsg.setText(" Witaj! \n Nie rozpoznałem tematu rozmowy - czy możesz powtórzyć w jakiej sprawie masz pytanie? \n \n " +
                    "Jeśli nadal widzisz tą wiadomość skontaktuj się z recepcją: " +tel_recepcji+ "\n Lub z administratorem systemu: hoteljava@admin.pl");
        }
    }

    private boolean search(String x, String y, String z, String a) {
        if(text.contains(x) || text.contains(y) || text.contains(z) || text.contains(a)) {
            return true;
        }
        else {
            return false;
        }
    }
}