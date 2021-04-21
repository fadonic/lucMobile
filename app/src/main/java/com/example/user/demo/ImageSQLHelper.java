package com.example.user.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageSQLHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "images";

    public  ImageSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists imageTb ( id INTEGER PRIMARY KEY AUTOINCREMENT,property_id TEXT, image TEXT ,status TEXT)");
        db.execSQL("create table if not exists usersTb ( id INTEGER PRIMARY KEY AUTOINCREMENT,user_id TEXT, password TEXT, mac TEXT ,status TEXT)");
        db.execSQL("create table if not exists demandTb ( id INTEGER PRIMARY KEY AUTOINCREMENT,property_id TEXT, image TEXT ,status TEXT)");

    }

    public boolean insertImage(String property_id, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("property_id",property_id);
        values.put("image",image);
        values.put("status","D");
        db.insert("imageTb", null,values);
        db.close();
        return true;
    }

    public boolean insert_demand_Image(String property_id, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("property_id",property_id);
        values.put("image",image);
        values.put("status","D");
        db.insert("demandTb", null,values);
        db.close();
        return true;
    }

    public boolean insert_demand_Image_offline(String property_id, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("property_id",property_id);
        values.put("image",image);
        values.put("status","N");
        db.insert("demandTb", null,values);
        db.close();
        return true;
    }

    public boolean insertImage_offline(String property_id, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("property_id",property_id);
        values.put("image",image);
        values.put("status","N");
        db.insert("imageTb", null,values);
        db.close();
        return true;
    }




    public HashMap<String, String> fetchImageRecords(){
        HashMap<String, String> map = new HashMap<>();
        String query = "SELECT  * from imageTb where status = 'N'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {

                // String id = c.getString(1);
                String  property_id = c.getString(1);
                String  image = c.getString(2);
                map.put("name",property_id);
                map.put("image",image);
              // System.out.println("Map "+ map);
            } while (c.moveToNext());
        }
        return map;
    }

    public HashMap<String, String> fetch_demand_ImageRecords(){
        HashMap<String, String> map = new HashMap<>();
        String query = "SELECT  * from demandTb where status = 'N'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {

                // String id = c.getString(1);
                String  property_id = c.getString(1);
                String  image = c.getString(2);
                map.put("name",property_id);
                map.put("image",image);
                // System.out.println("Map "+ map);
            } while (c.moveToNext());
        }
        return map;
    }


    public boolean insertUser(String user_id, String password, String mac){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id",user_id);
        values.put("password",password);
        values.put("mac",mac);
        values.put("status","Y");
        db.insert("usersTb", null,values);
        db.close();
        return true;
    }


    public boolean update_image_status(String property_id){
        String query = "UPDATE imageTb set status = 'Y' where property_id = '"+property_id+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }

    public boolean update_image_status_offline(String property_id){
        String query = "UPDATE imageTb set status = 'N' where property_id = '"+property_id+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }

    public boolean update_demand_image_status(String property_id){
        String query = "UPDATE demandTb set status = 'Y' where property_id = '"+property_id+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }

    public boolean update_demand_image_status_offline(String property_id){
        String query = "UPDATE demandTb set status = 'N' where property_id = '"+property_id+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }



    public int loginUser(String user_id, String password, String mac) {
        int count = 0;
        String query = "SELECT  count(*) FROM usersTb where user_id = '"+user_id+"' AND password = '"+password+"' AND  mac = '"+mac+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                String num = cursor.getString(0);
                count = Integer.parseInt(num);
            } while (cursor.moveToNext());
        }

        return count;
    }

    public int Retrive() {
        int count = 0;
        String query = "SELECT  * FROM usersTb";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                String one = cursor.getString(1);
                String two = cursor.getString(2);
                String three = cursor.getString(3);
                Log.d(ImageSQLHelper.class.getSimpleName(),"one :"+one+" two :"+two+" three :"+three);
            } while (cursor.moveToNext());
        }

        return count;
    }

    public int getCounts() {
        int count = 0;
        String query = "SELECT  count(*) FROM usersTb";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                String num = cursor.getString(0);
                count = Integer.parseInt(num);
            } while (cursor.moveToNext());
        }

        return count;
    }

    public int getdemandCounts() {
        int count = 0;
        String query = "SELECT  count(*) FROM demandTb where status = 'N'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                String num = cursor.getString(0);
                count = Integer.parseInt(num);
            } while (cursor.moveToNext());
        }

        return count;
    }

    public String get_user_identity() {
        String user = "";
        String query = "SELECT * FROM usersTb";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                user = cursor.getString(1);
            } while (cursor.moveToNext());
        }

        return user;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS imageTb");
        this.onCreate(db);
    }
}
