package com.example.campus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class InsertNewsfeed extends AppCompatActivity {

    private EditText heading,jobtitle,lastdate,subject,type,year1,desc;
    private Button submit;
    DBHelper db;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_newsfeed);

        db = new DBHelper(this);
        heading = findViewById(R.id.addNewsfeedHead);
        jobtitle = findViewById(R.id.addNewsfeedJob);
        lastdate = findViewById(R.id.addNewsfeedLastDate);
        subject = findViewById(R.id.addNewsfeedSubject);
        year1 = findViewById(R.id.addNewsfeedYear);
        type = findViewById(R.id.addNewsfeedType);
        desc = findViewById(R.id.addNewsfeedDesc);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int[] month = {calendar.get(Calendar.MONTH)};
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        lastdate.setFocusable(false);
        lastdate.setKeyListener(null);

        lastdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(InsertNewsfeed.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year, month[0],day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                month[0] = month[0] +1;
                String date=day+"/"+ month[0] +"/"+year;
                lastdate.setText(date);

            }
        };

        submit = findViewById(R.id.addNewsfeedSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String head, job, date, sub, yr, typ, des;

                head = heading.getText().toString();
                job = jobtitle.getText().toString();
                date = lastdate.getText().toString();
                sub = subject.getText().toString();
                yr = year1.getText().toString();
                typ = type.getText().toString();
                des = desc.getText().toString();

                db.insertNewsfeed(head, job, sub, des, date, typ, yr);
                Intent i = new Intent(getApplicationContext(), checkHome.class);
                i.putExtra("load", 0);
                startActivity(i);
                finish();
            }
        });

    }
}
