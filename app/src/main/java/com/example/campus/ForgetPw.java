package com.example.campus;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPw extends AppCompatActivity {
     EditText fpEmail;
     Button fpSend;
        String semail, spw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw);

        fpEmail=findViewById(R.id.fpEmail);
        fpSend=findViewById(R.id.fpSend);
        String subj="subkj";
        String body="body";

    }
}