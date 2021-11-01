package com.example.campus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    private TextView regText;
    private TextView forgotPw;
    private EditText emailid;
    private EditText pw;
    private Button login;
    DBHelper DB;
    SessionManager sessionManager;
    SharedPreferences pref;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        regText= findViewById(R.id.reg_text);
        emailid=findViewById(R.id.emailid);
        pw=findViewById(R.id.pw);
        login=findViewById(R.id.login);
//        forgotPw=findViewById(R.id.forgotpw);
        DB=new DBHelper(this);

        sessionManager = new SessionManager(getApplicationContext());
        pref = getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);

        regText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LogIn.this,Register.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName=emailid.getText().toString();
                String inputPw= pw.getText().toString();

                String checkl;
                if ((checkl=pref.getString("KEY_USER", null)) != null ) {
                    if(!checkl.equals("admin")){
                        Toast.makeText(LogIn.this, "Welcome back, " + DB.getName(checkl), Toast.LENGTH_LONG).show();
                    }
                    startActivity(new Intent(getApplicationContext(), checkHome.class));
                    finish();
                }

                if(inputName.isEmpty() || inputPw.isEmpty()){
                    Toast.makeText(LogIn.this,"Please enter all details", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean check= DB.loginCheck(inputName,inputPw);
                    if(check==true){
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("KEY_USER", emailid.getText().toString().trim());
                        edit.apply();
                        Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), checkHome.class));
                        finish();
                    }else{
                        Toast.makeText(LogIn.this,"Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}