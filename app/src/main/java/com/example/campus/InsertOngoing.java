package com.example.campus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertOngoing extends AppCompatActivity {

    EditText company;
    ArrayAdapter<String> aa;
//    String[] companyName = {"Select Company","Apple", "Dell", "Microsoft", "IBM"};
    EditText desc,cutoff;
    Button addOngoing;
    int imgid;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ongoing);

        company = findViewById(R.id.companySpinner);
        desc = findViewById(R.id.descEditText);
        cutoff = findViewById(R.id.cutoffEditText);
        addOngoing = findViewById(R.id.addOngoingButton);
        db = new DBHelper(this);

//        aa = new ArrayAdapter<String>(this, R.layout.custom_spinner, companyName);
//        company.setAdapter(aa);
//        company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getSelectedItemPosition() != 0)
//                    imgid = adapterView.getSelectedItemPosition();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        addOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String com = company.getText().toString();
                String des = desc.getText().toString();
                String cut = cutoff.getText().toString();
                int[] images = {R.drawable.building, R.drawable.building, R.drawable.building, R.drawable.building};
//                if (imgid == 0){
//                    Toast.makeText(InsertOngoing.this, "Please select a company!", Toast.LENGTH_SHORT).show();
//                }
                if ( des.equals("") || cut.equals("") ){
                    Toast.makeText(InsertOngoing.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }else{
                    db.insertOngoing(2, com, cut, des);
                    Intent i = new Intent(getApplicationContext(), checkHome.class);
                    i.putExtra("load", 1);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
