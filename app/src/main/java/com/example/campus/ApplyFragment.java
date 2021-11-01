package com.example.campus;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ApplyFragment extends Fragment {
    DBHelper db;
    EditText apRes, apCompName,apCGPA,Resume,CompanyName;
    Button apSubmit,pdf,temp;
    SharedPreferences pref;
    private ArrayList<UserModel> userList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_apply,container,false);
        View v = inflater.inflate(R.layout.fragment_apply, container,false);
        db = new DBHelper(getContext());
        pref = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        apCompName=v.findViewById(R.id.apCompName);
//        temp=v.findViewById(R.id.temp);
        apSubmit=v.findViewById(R.id.apSubmit);
        pdf=v.findViewById(R.id.pdf);

        String ema = pref.getString("KEY_USER", null);
        String uName= db.getName(ema);
        String uCgpa= db.getCgpa(ema);
        String uRes= db.getResume(ema);



        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

//        temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                db.deleteTemp(1);
//            }
//        });

        if (!pref.getString("KEY_USER", null).equals("admin")) {
            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"You are not an admin!",Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions, 1);
                        } else {
                            importData();
                        }
                    } else {
                        importData();
                    }
                }
            });
        }


        apSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputComp= apCompName.getText().toString();


                if(inputComp.isEmpty()){
                    Toast.makeText(getActivity(),"Fill the Company!",Toast.LENGTH_SHORT).show();
                }
                else{

                    if(!ema.equals("admin")){
                        if(uRes==null || uCgpa==null){
                            Toast.makeText(getActivity(),"Fill all details in EditProfile!",Toast.LENGTH_SHORT).show();
                        }else{
                            Boolean check1=db.compCheck(uName, inputComp);
                            if(check1==true){
                                Toast.makeText(getActivity(),"You've already applied for this company!",Toast.LENGTH_SHORT).show();
                            }else{
                                Boolean check=db.StudentEntry(uName, inputComp, uCgpa,uRes);
                                if(check==true){
                                    Toast.makeText(getActivity(),"Successful!",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(),"Try Again!",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }


                    }else{
                            Toast.makeText(getActivity(),"You are an admin!",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        return v;
    }
    private void importData() {
        db = new DBHelper(getContext());
        userList= db.getAllLocalUser();

        if(userList.size()>0){
            createXlFile();
        } else {
            Toast.makeText(getActivity(), "list are empty", Toast.LENGTH_SHORT).show();

        }


    }

    private void createXlFile() {


        // File filePath = new File(Environment.getExternalStorageDirectory() + "/Demo.xls");
        Workbook wb = new HSSFWorkbook();


        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("Demo Excel Sheet");
        //Now column and row
        Row row = sheet.createRow(0);

        cell = row.createCell(0);
        cell.setCellValue("Name");


        cell = row.createCell(1);
        cell.setCellValue("Company Name");


        cell = row.createCell(2);
        cell.setCellValue("Cgpa");

        cell = row.createCell(3);
        cell.setCellValue("Resume");


        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));
        sheet.setColumnWidth(3, (30 * 200));

        for (int i = 0; i < userList.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(userList.get(i).getName());

            cell = row1.createCell(1);
            cell.setCellValue((userList.get(i).getCompany()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(userList.get(i).getCGPA());

            cell = row1.createCell(3);
            cell.setCellValue(userList.get(i).getResume());


            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));
            sheet.setColumnWidth(3, (30 * 200));

        }
        String folderName = "Import Excel";
        String fileName = folderName + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(getActivity(), "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(getContext(), "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            importData();
        } else {
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
