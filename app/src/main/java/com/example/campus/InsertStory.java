package com.example.campus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InsertStory extends AppCompatActivity {

    EditText name, company, pack, story,yop;
    Button submit;
    SharedPreferences pref;
    DBHelper DB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_story_layout);
        DB = new DBHelper(this);
        pref = getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);
        name = findViewById(R.id.storyName);
        company = findViewById(R.id.storyCompany);
        pack = findViewById(R.id.storyPackage);
        story = findViewById(R.id.story);
        yop = findViewById(R.id.storyYop);
        submit = findViewById(R.id.storySubmit);
        final DBHelper databaseHelper = new DBHelper(getApplicationContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Company = company.getText().toString();
                String Pack = pack.getText().toString();
                String Story = story.getText().toString();
                String Yop=yop.getText().toString();
                String ema = pref.getString("KEY_USER", null);
                String uName= DB.getName(ema);

                databaseHelper.updateStory(uName,Company,Pack, Story,Yop);
                Intent i = new Intent(getApplicationContext(), checkHome.class);
                i.putExtra("load", 2);
                startActivity(i);
                finish();
            }
        });
    }
}
