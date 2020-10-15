package com.example.hotelmanagementapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tooltip.Tooltip;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class createReservationActivity extends AppCompatActivity {

    String [] howMuch = {"0","1","2","3","4"};
    String [] rooms = {"1","2","3","4"};
    String [] typeRooms = {"Standard","Standard dla palących","Aparatament","Apartament dla palących"};
    EditText arrival, departure, timeArrival,name,surname;
    Button sendButton;
    String getName, getSurname, getArrival, getDeparture,getAdult,getRooms,getTypeOfRoom,getChildren;
    WebView webView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);
        //Find view by ID
        arrival = findViewById(R.id.arrival);
        departure = findViewById(R.id.departure);
        timeArrival = findViewById(R.id.timeArrival);
        sendButton = findViewById(R.id.sendButton);
        //No possibility to enter text
        arrival.setInputType(InputType.TYPE_NULL);
        departure.setInputType(InputType.TYPE_NULL);
        timeArrival.setInputType(InputType.TYPE_NULL);
        //Action after pressing the button
        arrival.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDateArrival(arrival);
            }
        });
        departure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDateDeparture(departure);
            }
        });
        timeArrival.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showTimeArrival(timeArrival);
            }
        });
        //Function call
        howMuchPerson();
        howMuchRooms();
        typeOfRoom();
        noInternet();
    }
    //Drop-down menu for type of room
    private void typeOfRoom() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,typeRooms);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.android_material_design_spinner4);
        betterSpinner.setAdapter(arrayAdapter);
    }
    //Drop-down menu for number of people
    private void howMuchPerson(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,howMuch);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.android_material_design_spinner);
        betterSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,howMuch);
        MaterialBetterSpinner betterSpinner2 = (MaterialBetterSpinner)findViewById(R.id.android_material_design_spinner2);
        betterSpinner2.setAdapter(arrayAdapter2);
    }
    //Drop-down menu for number of rooms
    private void howMuchRooms(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,rooms);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.android_material_design_spinner3);
        betterSpinner.setAdapter(arrayAdapter);
    }
    //Arrival calendar
    private void showDateArrival(final EditText arrival){
        Toast.makeText(getApplicationContext(),"!!",Toast.LENGTH_SHORT).show();
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                arrival.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(createReservationActivity.this, dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    //Departure calendar
    private void showDateDeparture(final EditText departure){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                departure.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(createReservationActivity.this, dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    //Time Clock
    private void showTimeArrival(final EditText timeArrival) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                timeArrival.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(createReservationActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }
    //Checking if view is empty
    public void checkIsEmpty(View view) {
        arrival = findViewById(R.id.arrival);
        departure = findViewById(R.id.departure);
        name = findViewById(R.id.nameView);
        surname = findViewById(R.id.surnameView);
        MaterialBetterSpinner adult = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner);
        MaterialBetterSpinner children = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner2);
        MaterialBetterSpinner rooms = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner3);
        MaterialBetterSpinner typeOfRoom = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner4);

        getName = name.getText().toString();
        getSurname = surname.getText().toString();
        getArrival = arrival.getText().toString();
        getDeparture = departure.getText().toString();
        getAdult = adult.getText().toString();
        getChildren = children.getText().toString();
        getRooms = rooms.getText().toString();
        getTypeOfRoom = typeOfRoom.getText().toString();

        if(TextUtils.isEmpty(getName)){
            name.setError("Wprowadź swoje imię!");
            if(TextUtils.isEmpty(getSurname)) {
                surname.setError("Wprowadź swoje nazwisko!");
                if (TextUtils.isEmpty(getAdult)) {
                    adult.setError("Wybierz ilość dorosłych!");
                    if (TextUtils.isEmpty(getChildren)) {
                        children.setError("Wybierz ilość dzieci!");
                        if (TextUtils.isEmpty(getRooms)) {
                            rooms.setError("Wybierz ilość pokoi!");
                            if (TextUtils.isEmpty(getTypeOfRoom)) {
                                typeOfRoom.setError("Wybierz rodzaj/typ pokoju!");
                                if (TextUtils.isEmpty(getArrival)) {
                                    arrival.setError("Wprowadź datę przyjazdu!");
                                    if (TextUtils.isEmpty(getDeparture)) {
                                        departure.setError("Wprowadź datę wyjazdu!");
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }else{
            Intent intent = new Intent(createReservationActivity.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Wysyłanie rezerwacji",Toast.LENGTH_SHORT).show();
        }
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
