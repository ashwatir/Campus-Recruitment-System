package com.example.campus;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context) {
        super(context, "Campus10.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE IF NOT EXISTS Student(Id integer primary key autoincrement,Name TEXT, Email TEXT,Rollno TEXT, Pw TEXT,PP BLOB,CGPA text, Resume text)");
        DB.execSQL("CREATE TABLE IF NOT EXISTS Newsfeed(Id integer primary key autoincrement, Heading text not null, JobTitle text, Subject text, Description text, Date text not null, Type text not null, Year text);");
        DB.execSQL("CREATE TABLE IF NOT EXISTS Ongoing(Id integer primary key autoincrement, Imageid integer not null, Company text, Cutoff text, Description text);");
        DB.execSQL("CREATE TABLE IF NOT EXISTS Previous(Id integer primary key autoincrement, Company text, Cutoff text)");
        DB.execSQL("CREATE TABLE IF NOT EXISTS Story(Id integer primary key autoincrement, Name text, Company text,Pack text, Story text,YOP text)");
        DB.execSQL("CREATE TABLE IF NOT EXISTS Apply(Id integer primary key autoincrement, Name text, CompanyName text,CGPA text, Resume text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {

        DB.execSQL("drop Table if exists Student");
        DB.execSQL("drop Table if exists Newsfeed");
        DB.execSQL("drop Table if exists Ongoing");
        DB.execSQL("drop Table if exists Previous");
        DB.execSQL("drop Table if exists Story");

    }
    public Boolean insertStudent(String name,String email,String rollno,String pw){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Email",email);
        contentValues.put("Rollno",rollno);
        contentValues.put("Pw",pw);
        long result=DB.insert("Student",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean loginCheck(String email, String pw){
        SQLiteDatabase DB= this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("select * from Student where Email= ? and Pw = ?",new String[]{email,pw});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    public Boolean userCheck(String email){
        SQLiteDatabase DB= this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("select * from Student where Email= ? ",new String[]{email});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }


    public String getName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select Name from Student where Email = '"+email+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("Name"));
    }
    public String getPPValue(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select PP from Student where Email = '"+email+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("PP"));
    }
    public String getCgpa(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select CGPA from Student where Email = '"+email+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("CGPA"));
    }
    public String getResume(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select Resume from Student where Email = '"+email+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("Resume"));
    }

    public Boolean compCheck(String name, String comp){
        SQLiteDatabase DB= this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("select * from Apply where Name= ? and CompanyName=?",new String[]{name,comp});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
//    public void deleteTemp(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("Delete from Apply where Id = " + id);
//    }

    public Cursor viewNewsfeed() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Heading, JobTitle, Subject, Date, Type, Year, Description, Id from Newsfeed", null);
    }
    public boolean insertNewsfeed(String heading, String jobTitle, String subject, String description, String date, String type, String year) {
        SQLiteDatabase db = this.getWritableDatabase();;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Heading", heading);
        contentValues.put("JobTitle", jobTitle);
        contentValues.put("Subject", subject);
        contentValues.put("Description", description);
        contentValues.put("Date", date);
        contentValues.put("Type", type);
        contentValues.put("Year", year);
        long result = db.insert("Newsfeed", null, contentValues);
        return !(result == -1);
    }
    public void deleteNewsfeed(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from Newsfeed where Id = " + id);
    }
    public Cursor viewOngoing() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Imageid, Company, Cutoff, Description, Id from Ongoing", null);
    }
    public Cursor viewPrevious() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Company, Cutoff, Id from Previous", null);
    }
    public void deleteOngoing(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from Ongoing where Id = " + id);
    }

    public void deletePrevious(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from Previous where Id = " + id);
    }
    public boolean insertPrevious(String company, String cutoff) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Company", company);
        contentValues.put("Cutoff", cutoff);
        long result = db.insert("Previous", null, contentValues);
        return !(result == -1);
    }
    public boolean insertOngoing(int imageId, String company, String cutoff, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Imageid", imageId);
        contentValues.put("Company", company);
        contentValues.put("Cutoff", cutoff);
        contentValues.put("Description", description);
        long result = db.insert("Ongoing", null, contentValues);
        return !(result == -1);
    }

    public boolean updateStory(String name,String company,String pack,String story,String yop){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Company",company);
        contentValues.put("Pack",pack);
        contentValues.put("Story",story);
        contentValues.put("YOP",yop);
        long result=DB.insert("Story",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor viewStory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Name, Company, Pack, Story, YOP, Id from Story;", null);
    }
    public void deleteStory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from Story where Id = " + id);
    }
    public void updateProfile(String email, String name, String password, byte[] pp, String cgp,String res) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update Student Set Name = '"+name+"', Pw = '"+password+"', PP = '"+pp+"',CGPA = '"+cgp+"',Resume = '"+res+"' where Email = '"+email+"'");
    }
//    public byte[] getPP(String query){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Bitmap bt=null;
//        Cursor cursor=db.rawQuery("Select PP from Student where Email = '"+query+"';", null);
//        if(cursor.moveToNext()){
//            byte[] imag= cursor.getBlob(0);
//            return imag;
//        }
//
//        return new byte[0];
//    }
    public Boolean StudentEntry(String name,String Company,String CGPA,String Resume){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("CompanyName",Company);
        contentValues.put("CGPA",CGPA);
        contentValues.put("Resume",Resume);
        long result=DB.insert("Apply",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public ArrayList<UserModel> getAllLocalUser(){
        SQLiteDatabase DB= this.getWritableDatabase();
        ArrayList<UserModel>list=new ArrayList<>();
        Cursor cursor=DB.rawQuery("SELECT * FROM " +DatabaseContract.StudentTable.TABLE_NAME,null);

        if(cursor.moveToFirst()){
            do {


                String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.StudentTable.Name));
                String comp = cursor.getString(cursor.getColumnIndex(DatabaseContract.StudentTable.COMP));
                String cgpa = cursor.getString(cursor.getColumnIndex(DatabaseContract.StudentTable.CGPA));
                String res = cursor.getString(cursor.getColumnIndex(DatabaseContract.StudentTable.Resume));
                list.add(new UserModel(name,comp,cgpa,res));
            } while (cursor.moveToNext());
        }

        return list;
    }
//    public byte[] getImage(String query){
//        SQLiteDatabase DB= this.getWritableDatabase();
//        Cursor cursor=DB.rawQuery("Select PP from Student where Email = 'ashwatisrao21@gmail.com';", null);
//        if(cursor.moveToFirst()){
//            return cursor.getBlob(0);
//        }
//        return null;
//
//    }
@SuppressLint("Range")
public byte[] getPPImage(String email) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery("Select PP from Student where Email = '"+email+"';", null);
    c.moveToFirst();
    return c.getBlob(c.getColumnIndex("PP"));
}

}
