package com.example.campus;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class EditProfile extends AppCompatActivity {
    private ImageView pfp;
    private EditText name;
    private EditText pw;
    private EditText pw2;
    private Button submit;
     EditText cgp;
     EditText res;
    DBHelper DB;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        pfp = findViewById(R.id.pfp);
        name = findViewById(R.id.nameEditText);
        pw = findViewById(R.id.passEditText);
        pw2 = findViewById(R.id.cnfPassEditText);
        cgp=findViewById(R.id.cgp);
        res=findViewById(R.id.res);
        submit = findViewById(R.id.editSubmit);

        DB = new DBHelper(this);
        pref = getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);
        String ema = pref.getString("KEY_USER", null);


//        byte[] bytesPP=DB.getImage("SELECT PP FROM Student WHERE Email='"+ema+"';");
//        if(bytesPP!=null){
//            Bitmap bitmap=covertByteToBitmapp(bytesPP);
//            pfp.setImageBitmap(bitmap);
//        }

        if(ContextCompat.checkSelfPermission(EditProfile.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(EditProfile.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        pfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName=name.getText().toString();
                String inputPw= pw.getText().toString();
                String inputPw2= pw2.getText().toString();

                byte[] bytesPP=convertImage(pfp);

                if (name.getText().toString().trim().equals("") || pw.getText().toString().trim().equals("") || pw2.getText().toString().trim().equals("")) {
                    Toast.makeText(EditProfile.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!pw.getText().toString().trim().equals(pw2.getText().toString().trim())) {
                    Toast.makeText(EditProfile.this, "Confirm password does not match!", Toast.LENGTH_SHORT).show();
                    pw.setText("");
                    pw2.setText("");
                } else {
                    DB.updateProfile(pref.getString("KEY_USER", null), name.getText().toString().trim(), pw.getText().toString().trim(),bytesPP,cgp.getText().toString().trim(),res.getText().toString());
                    Toast.makeText(EditProfile.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), checkHome.class));
                    finish();
                }
            }
        });

    }
    private byte[] convertImage(ImageView imageView){
        DB = new DBHelper(this);
        Bitmap bitmap=((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        DB = new DBHelper(this);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
        pfp.setImageBitmap(captureImage);
        }
    }

}
