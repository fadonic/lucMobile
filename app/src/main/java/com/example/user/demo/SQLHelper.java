package com.example.user.demo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users";

    public  SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "create table Record" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "agent_id TEXT" +
                ",mac TEXT" +
                ",title TEXT" +
                ",firstname TEXT" +
                ",middlename TEXT ," +
                "lastname TEXT," +
                "tin_type TEXT," +
                "tin TEXT," +
                "phone TEXT," +
                "property_id TEXT," +
                "email TEXT," +
                "area TEXT," +
                "street TEXT," +
                "house_no TEXT," +
                "landsize TEXT" +
                ",area_class TEXT,"+
                "lga TEXT," +
                "zone TEXT," +
                "category TEXT," +
                "building_type TEXT," +
                "building_structure TEXT," +
                "occupation TEXT," +
                "place_of_work TEXT," +
                "landmark TEXT," +
                "property_name TEXT," +
                "longs TEXT," +
                "lat TEXT," +
                "area_code TEXT," +
                "lga_code TEXT," +
                "date TEXT," +
                "property_type TEXT," +
                "status TEXT)";


        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS Record");
        this.onCreate(db);
    }


    public boolean addRecord(DataClass record) {
        System.out.println("Build type"+record.getBuilding_type());
        System.out.println("Structure type"+record.getStructure_type());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("agent_id", record.getStaff());
        values.put("title", record.getTitle());
        values.put("firstname", record.getfirstname());
        values.put("middlename", record.getmiddlename());
        values.put("lastname", record.getlastname());
        values.put("tin_type", record.getTintype());
        values.put("tin", record.getTin());
        values.put("phone", record.getPhone());
        values.put("property_id", record.getProperty_id());
        values.put("email", record.getEmail());
        values.put("area", record.getArea());
        values.put("street", record.getStreet());
        values.put("house_no", record.getHouse());
        values.put("landsize", record.getLandsize());
        values.put("area_class", record.getArea_class());
        values.put("lga", record.getLga());
        values.put("zone", record.getZone());
        values.put("category", record.getCategory());
        values.put("landmark", record.getLandmark());
        values.put("property_name", record.getProperty_name());
        values.put("longs", record.getLongs());
        values.put("lat", record.getLat());
        values.put("mac", record.getMac());
        values.put("area_code", record.getAreacode());
        values.put("lga_code", record.getLgacode());
        values.put("building_structure", record.getStructure_type());
        values.put("building_type", record.getBuilding_type());
        values.put("occupation", record.getOccupation());
        values.put("place_of_work", record.getPlace_of_work());
        values.put("date", getDate("yyyy/MM/dd HH:mm:ss"));
        values.put("property_type", record.getProperty_type());
        values.put("status", "D");

        // insert
        db.insert("Record",null, values);
        db.close();
        return true;
    }


    public boolean addRecord_offline(DataClass record) {
        System.out.println("Build type"+record.getBuilding_type());
        System.out.println("Structure type"+record.getStructure_type());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("agent_id", record.getStaff());
        values.put("title", record.getTitle());
        values.put("firstname", record.getfirstname());
        values.put("middlename", record.getmiddlename());
        values.put("lastname", record.getlastname());
        values.put("tin_type", record.getTintype());
        values.put("tin", record.getTin());
        values.put("phone", record.getPhone());
        values.put("property_id", record.getProperty_id());
        values.put("email", record.getEmail());
        values.put("area", record.getArea());
        values.put("street", record.getStreet());
        values.put("house_no", record.getHouse());
        values.put("landsize", record.getLandsize());
        values.put("area_class", record.getArea_class());
        values.put("lga", record.getLga());
        values.put("zone", record.getZone());
        values.put("category", record.getCategory());
        values.put("landmark", record.getLandmark());
        values.put("property_name", record.getProperty_name());
        values.put("longs", record.getLongs());
        values.put("lat", record.getLat());
        values.put("mac", record.getMac());
        values.put("area_code", record.getAreacode());
        values.put("lga_code", record.getLgacode());
        values.put("building_structure", record.getStructure_type());
        values.put("building_type", record.getBuilding_type());
        values.put("occupation", record.getOccupation());
        values.put("place_of_work", record.getPlace_of_work());
        values.put("date", getDate("yyyy/MM/dd HH:mm:ss"));
        values.put("property_type", record.getProperty_type());
        values.put("status", "N");

        // insert
        db.insert("Record",null, values);
        db.close();
        return true;
    }


    public List<DataClass> allPlayers() {

        List<DataClass> players = new LinkedList<>();
        String query = "SELECT  * FROM Record order by date desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DataClass player = null;

        if (cursor.moveToFirst()) {
            do {
                player = new DataClass();
                player.setProperty_id(cursor.getString(10));
                player.setfirstname(cursor.getString(4));
                player.setlastname(cursor.getString(6));
                player.setTin(cursor.getString(8));
                player.setLga(cursor.getString(17));
                player.setDate(cursor.getString(30));

                players.add(player);
            } while (cursor.moveToNext());
        }

        return players;
    }

    public static String getDate(String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        return formatter.format(calendar.getTime());
    }


    public ArrayList<DataClass> fetchRecords() {

        ArrayList<DataClass> players = new ArrayList<>();
        String query = "SELECT  * FROM Record order by date desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DataClass player = null;

        if (cursor.moveToFirst()) {
            do {
                player = new DataClass();
                player.setProperty_id(cursor.getString(10));
                player.setfirstname(cursor.getString(4));
                player.setlastname(cursor.getString(6));
                player.setTin(cursor.getString(8));
                player.setLga(cursor.getString(17));
                player.setDate(cursor.getString(30));


                players.add(player);
            } while (cursor.moveToNext());
        }

        return players;
    }


    public int getOfflineCounts() {
        int count = 0;
        String query = "SELECT  count(*) FROM Record where status = 'N'";
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

    public int getOnlineCounts() {
        int count = 0;
        String query = "SELECT  count(*) FROM Record";
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

    public String getDataFromAnyColumn(int column) {
        String num = "";
        String query = "SELECT  * FROM Record";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                num = cursor.getString(column);

            } while (cursor.moveToNext());
        }

        return num;
    }

    public String getAllRecords() {


        JsonObject jsonResponse = new JsonObject();
        String query = "SELECT * FROM Record ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Gson gs = null;
        JsonArray rec = new JsonArray();
        DataRecoveryClass data  =  null;

        if (cursor.moveToFirst()) {
            do {

                data = new DataRecoveryClass(cursor.getString(1),cursor.getString(3),cursor.getString(4)
                        ,cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),
                        cursor.getString(13),cursor.getString(14),cursor.getString(15),
                        cursor.getString(16),cursor.getString(17),cursor.getString(18), cursor.getString(19),
                        cursor.getString(24),
                        cursor.getString(25),cursor.getString(26), cursor.getString(27),cursor.getString(2),
                        cursor.getString(28),cursor.getString(29),cursor.getString(20),cursor.getString(21),
                        cursor.getString(22),cursor.getString(23),cursor.getString(30),cursor.getString(31));

                gs = new Gson();
                String roots = gs.toJson(data);
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(roots);
                rec.add(root);


            } while (cursor.moveToNext());
        }

        return rec.toString();
    }

    public String getAllOfflineRecords() {


        JsonObject jsonResponse = new JsonObject();
        String query = "SELECT * FROM Record";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Gson gs = null;
        JsonArray rec = new JsonArray();
        DataRecoveryClass data  =  null;

        if (cursor.moveToFirst()) {
            do {

                data = new DataRecoveryClass(cursor.getString(1),cursor.getString(3),cursor.getString(4)
                        ,cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                        cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),
                        cursor.getString(13),cursor.getString(14),cursor.getString(15),
                        cursor.getString(16),cursor.getString(17),cursor.getString(18), cursor.getString(19),
                        cursor.getString(24),
                        cursor.getString(25),cursor.getString(26), cursor.getString(27),cursor.getString(2),
                        cursor.getString(28),cursor.getString(29),cursor.getString(20),cursor.getString(21),
                        cursor.getString(22),cursor.getString(23),cursor.getString(30),cursor.getString(31));

                gs = new Gson();
                String roots = gs.toJson(data);
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(roots);
                rec.add(root);


            } while (cursor.moveToNext());
        }

        return rec.toString();
    }

    public boolean update_property_status_offline(String property_id){
        System.out.println("Property ID "+property_id);
        String query = "UPDATE Record set status = 'N' where property_id = '"+property_id+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }

    public boolean deleteProperty(){
        String query = "Delete from Record where lat = '7.62808'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }



    public boolean update_all_property_status(){
        Log.d(SQLHelper.class.getSimpleName(), "Hello guy.......");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "Y");
        db.update("RECORD", values,null,null);
        return true;
    }

    public boolean update_property_status_online(String property_id){
        String query = "UPDATE Record set status = 'Y' where property_id = '"+property_id+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }


}
