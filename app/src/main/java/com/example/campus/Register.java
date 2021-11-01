package com.example.campus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private EditText regName;
    private EditText regEmail;
    private EditText regRoll;
    private EditText regPw;
    private TextView login_text;
    private Button regButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName=findViewById(R.id.regName);
        regEmail=findViewById(R.id.regEmail);
        regRoll=findViewById(R.id.regRoll);
        regPw= findViewById(R.id.regPw);
        login_text=findViewById(R.id.login_text);
        regButton=findViewById(R.id.regButton);
        DB=new DBHelper(this);

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Register.this,LogIn.class);
                startActivity(intent);
            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName=regName.getText().toString();
                String inputEmail= regEmail.getText().toString();
                String inputRoll=regRoll.getText().toString();
                String inputPw= regPw.getText().toString();

                if(inputName.isEmpty() || inputPw.isEmpty() || inputEmail.isEmpty()|| inputRoll.isEmpty()){
                    Toast.makeText(Register.this,"Please enter all details", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean firstCheck= DB.userCheck(inputEmail);
                    Boolean check= DB.insertStudent(inputName, inputEmail, inputRoll,inputPw);
                    if (firstCheck==false){
                        if(check==true){
                            Toast.makeText(Register.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(Register.this,LogIn.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Register.this,"Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Register.this,"You've already registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}