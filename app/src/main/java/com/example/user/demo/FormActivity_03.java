package com.example.user.demo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class FormActivity_03 extends AppCompatActivity {

    Button prev, submit, submit_offline;
    String staff, title, first, middle, last, tin, tintype, phone, property_id, email, area, street, house, landsize;
    EditText landmark, property_name, occupation, place_of_work;
    Spinner area_class, lga, zone, category, building_type, structue_type;
    TextView sf, te, ft, md, lt, tin2, tintyp2, phn2, prop, eml, ara, str, hse, lze;
    ImageButton camera_button;
    ImageView imageView;
    GPSTracker gps;
    Bitmap bitmap;
    boolean internet_status = false;
    static String filename;
    Uri file;
    String property;
    double lat, longs;
    String mac = "";
    String agent_id;
    ProgressDialog progressDialog;
    private String url = "https://luc.ekitistaterevenue.com/luc_api.php";
    private SQLHelper sqlHelper;
    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="https://luc.ekitistaterevenue.com/image_upload/upload.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String TAG = FormActivity_03.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_03);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //get mac address
        mac = new InternetConnectionClass(getApplicationContext()).getMacAddr();
        agent_id = new ImageSQLHelper(this).get_user_identity();



        area_class = (Spinner) findViewById(R.id.area_class);
        lga = (Spinner) findViewById(R.id.lga);
        zone = (Spinner) findViewById(R.id.zone);
        category = (Spinner) findViewById(R.id.category);
        landmark = (EditText) findViewById(R.id.landmark);
        property_name = (EditText) findViewById(R.id.property_name);
        building_type = (Spinner) findViewById(R.id.building_type);
        structue_type = (Spinner)findViewById(R.id.land_structure);
        occupation = (EditText) findViewById(R.id.occupation);
        place_of_work = (EditText) findViewById(R.id.place_of_work);

        //camera
        camera_button = (ImageButton) findViewById(R.id.image_button);
        imageView = (ImageView) findViewById(R.id.image_view);

       camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            camera_button.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }



        area_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3){
                    zone.setSelection(3);
                }else if (position == 4){
                    zone.setSelection(3);
                }else if (position == 5){
                    zone.setSelection(3);
                }else if (position == 6){
                    zone.setSelection(3);
                }else if (position == 7){
                    zone.setSelection(3);
                }else if (position == 8){
                    zone.setSelection(3);
                }else if (position == 9){
                    zone.setSelection(3);
                }else if (position == 10){
                    zone.setSelection(3);
                }else if (position == 11){
                    zone.setSelection(3);
                }else if (position == 12){
                    zone.setSelection(4);
                }else if (position == 13){
                    zone.setSelection(4);
                }else if (position == 14){
                    zone.setSelection(4);
                }else if (position == 15){
                    zone.setSelection(4);
                }else if (position == 0){
                    zone.setSelection(0);
                }else if (position == 1){
                    zone.setSelection(0);
                }else if (position == 2){
                    zone.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        lga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    category.setSelection(0);
                }else if (position == 1){
                    category.setSelection(0);
                }else if (position == 2){
                    category.setSelection(1);
                }else if (position == 3){
                    category.setSelection(1);
                }else if (position == 4){
                    category.setSelection(1);
                }else if (position == 5){
                    category.setSelection(1);
                }else if (position == 6){
                    category.setSelection(1);
                }else if (position == 7){
                    category.setSelection(1);
                }else if (position == 8){
                    category.setSelection(1);
                }else if (position == 9){
                    category.setSelection(1);
                }else if (position == 10){
                    category.setSelection(2);
                }else if (position == 11){
                    category.setSelection(2);
                }else if (position == 12){
                    category.setSelection(2);
                }else if (position == 13){
                    category.setSelection(2);
                }else if (position == 14){
                    category.setSelection(2);
                }else if (position == 15){
                    category.setSelection(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        te = (TextView) findViewById(R.id.ttle2);
        ft = (TextView) findViewById(R.id.fst2);
        md = (TextView) findViewById(R.id.mid2);
        lt = (TextView) findViewById(R.id.lst2);
        tin2 = (TextView) findViewById(R.id.tn2);
        tintyp2 = (TextView) findViewById(R.id.tntyp2);
        phn2 = (TextView) findViewById(R.id.phn2);

        prop = (TextView) findViewById(R.id.pp_02);
        eml = (TextView) findViewById(R.id.eml);
        ara = (TextView) findViewById(R.id.ara);
        str = (TextView) findViewById(R.id.str);
        hse = (TextView) findViewById(R.id.hse);
        lze = (TextView) findViewById(R.id.land);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            title = b.get("titles").toString();
            first = b.get("firsts").toString();
            middle = b.get("middles").toString();
            last = b.get("lasts").toString();
            tintype = b.get("tin_types").toString();
            tin = b.get("tins").toString();
            phone = b.get("phones").toString();

            property_id = b.get("property_id").toString();
            email = b.get("email").toString();
            area = b.get("area").toString();
            street = b.get("street").toString();
            house = b.get("house").toString();
            landsize = b.get("land_size").toString();

            te.setText(title);
            ft.setText(first);
            md.setText(middle);
            lt.setText(last);
            tintyp2.setText(tintype);
            tin2.setText(tin);
            phn2.setText(phone);

            prop.setText(property_id);
            eml.setText(email);
            ara.setText(area);
            str.setText(street);
            hse.setText(house);
            lze.setText(landsize);

        }


        prev = (Button) findViewById(R.id.prev_02);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormActivity_03.this, FormActivity_02.class));
            }
        });


        //offline
        submit_offline = (Button)findViewById(R.id.submit_offline);
        submit = (Button)findViewById(R.id.submit);

        getInternetStatus();

        submit_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setEnabled(false);
                gps = new GPSTracker(FormActivity_03.this);
                sqlHelper = new SQLHelper(FormActivity_03.this);
                if(gps.canGetLocation()) {
                    lat = gps.getLatitude();
                    longs = gps.getLongitude();
                    // String state =   pushRecordsOnline("2222", "Mr", "Jon", "Main", "Doe","45667","0808543334", "54345", "dtuzzy@yahoo.com","test","test","test","test","test", "test", "test", "test","test", "test", 5.88090, 4.9876, "5543456654");
                    final String area_class_value = getValueAtPostion(area_class.getSelectedItemPosition());
                    final String lga_value = getValueAtPostionLga(lga.getSelectedItemPosition());
                    final String building = building_type.getSelectedItem().toString();
                    final String structure = structue_type.getSelectedItem().toString();
                     new SQLHelper(FormActivity_03.this).addRecord_offline(new DataClass(agent_id, te.getText().toString(), ft.getText().toString(), md.getText().toString(), lt.getText().toString(),tintyp2.getText().toString(), tin2.getText().toString(), phn2.getText().toString(), prop.getText().toString(), eml.getText().toString(), ara.getText().toString(), str.getText().toString(), hse.getText().toString(), lze.getText().toString(), area_class.getSelectedItem().toString(), lga.getSelectedItem().toString(), zone.getSelectedItem().toString(), category.getSelectedItem().toString(), landmark.getText().toString(), property_name.getText().toString(), longs, lat, mac, area_class_value, lga_value, building,structure, occupation.getText().toString(), place_of_work.getText().toString(), area_class.getSelectedItem().toString()));
                    String image = getStringImage(bitmap);
                    //Getting Image Name
                    String name = prop.getText().toString();
                    new ImageSQLHelper(FormActivity_03.this).insertImage_offline(name, image);
                    // pushRecordsOnline(agent_id, te.getText().toString(), ft.getText().toString(), md.getText().toString(), lt.getText().toString(),tintyp2.getText().toString(), tin2.getText().toString(), phn2.getText().toString(), prop.getText().toString(), eml.getText().toString(), ara.getText().toString(), str.getText().toString(), hse.getText().toString(), lze.getText().toString(), area_class.getSelectedItem().toString(), lga.getSelectedItem().toString(), zone.getSelectedItem().toString(), category.getSelectedItem().toString(), landmark.getText().toString(), property_name.getText().toString(), longs, lat, mac, area_class_value, lga_value, building,structure, occupation.getText().toString(), place_of_work.getText().toString(), area_class.getSelectedItem().toString());
                    Intent intent1 = new Intent(FormActivity_03.this, SuccessActivity.class);
                    intent1.putExtra("s_name", ft.getText().toString());
                    intent1.putExtra("s_last", lt.getText().toString());
                    intent1.putExtra("s_tin", tin2.getText().toString());
                    intent1.putExtra("s_lga", lga.getSelectedItem().toString());
                    intent1.putExtra("s_prop", prop.getText().toString());
                    intent1.putExtra("s_phn", phn2.getText().toString());
                    startActivity(intent1);
                } else {
                    gps.showSettingsAlert();
                }


            }
        });

        //online
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setEnabled(false);
                gps = new GPSTracker(FormActivity_03.this);
                sqlHelper = new SQLHelper(FormActivity_03.this);
                if(gps.canGetLocation()) {
                    lat = gps.getLatitude();
                    longs = gps.getLongitude();
                    // String state =   pushRecordsOnline("2222", "Mr", "Jon", "Main", "Doe","45667","0808543334", "54345", "dtuzzy@yahoo.com","test","test","test","test","test", "test", "test", "test","test", "test", 5.88090, 4.9876, "5543456654");
                    final String area_class_value = getValueAtPostion(area_class.getSelectedItemPosition());
                    final String lga_value = getValueAtPostionLga(lga.getSelectedItemPosition());
                    final String building = building_type.getSelectedItem().toString();
                    final String structure = structue_type.getSelectedItem().toString();
                    //  new SQLHelper(FormActivity_03.this).addRecord(new DataClass(agent_id, te.getText().toString(), ft.getText().toString(), md.getText().toString(), lt.getText().toString(),tintyp2.getText().toString(), tin2.getText().toString(), phn2.getText().toString(), prop.getText().toString(), eml.getText().toString(), ara.getText().toString(), str.getText().toString(), hse.getText().toString(), lze.getText().toString(), area_class.getSelectedItem().toString(), lga.getSelectedItem().toString(), zone.getSelectedItem().toString(), category.getSelectedItem().toString(), landmark.getText().toString(), property_name.getText().toString(), longs, lat, mac, area_class_value, lga_value, building,structure, occupation.getText().toString(), place_of_work.getText().toString()));
                    //
                    pushRecordsOnline(agent_id, te.getText().toString(), ft.getText().toString(), md.getText().toString(), lt.getText().toString(),tintyp2.getText().toString(), tin2.getText().toString(), phn2.getText().toString(), prop.getText().toString(), eml.getText().toString(), ara.getText().toString(), str.getText().toString(), hse.getText().toString(), lze.getText().toString(), area_class.getSelectedItem().toString(), lga.getSelectedItem().toString(), zone.getSelectedItem().toString(), category.getSelectedItem().toString(), landmark.getText().toString(), property_name.getText().toString(), longs, lat, mac, area_class_value, lga_value, building,structure, occupation.getText().toString(), place_of_work.getText().toString(), area_class.getSelectedItem().toString());

                } else {
                    gps.showSettingsAlert();
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                camera_button.setEnabled(true);
            }
        }
    }


    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                ///  Uri filePath = getOutputMediaFile();
                try {
                    Bitmap bitmaps = MediaStore.Images.Media.getBitmap(getContentResolver(), file);
                    bitmap = getResizedBitmap(bitmaps, 500);
                    //Setting the Bitmap to ImageView
                    imageView.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "EKIRSLUC");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        filename =  "IMG_"+ timeStamp + ".jpg";
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }






    private void uploadImage(final String propertyID, final String firstname, final String lastname,final String tin, final String lga,final String phone){
        //Showing the progress dialog

        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        new ImageSQLHelper(FormActivity_03.this).update_image_status(property_id);
                        Toast.makeText(FormActivity_03.this, s , Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(FormActivity_03.this, SuccessActivity.class);
                        intent1.putExtra("s_name", firstname);
                        intent1.putExtra("s_last", lastname);
                        intent1.putExtra("s_tin", tin);
                        intent1.putExtra("s_lga", lga);
                        intent1.putExtra("s_prop", propertyID);
                        intent1.putExtra("s_phn", phone);
                        startActivity(intent1);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        new ImageSQLHelper(FormActivity_03.this).update_image_status_offline(property_id);
                        new SQLHelper(FormActivity_03.this).update_property_status_offline(property_id);

                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(FormActivity_03.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(FormActivity_03.this, SuccessActivity.class);
                        intent1.putExtra("s_name", firstname);
                        intent1.putExtra("s_last", lastname);
                        intent1.putExtra("s_tin", tin);
                        intent1.putExtra("s_lga", lga);
                        intent1.putExtra("s_prop", propertyID);
                        intent1.putExtra("s_phn", phone);
                        startActivity(intent1);
                    }

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = propertyID;

                //Creating parameters
                Map<String,String> params = new Hashtable<>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);


                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //set Retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



    public  void  pushRecordsOnline(String staff, String  title, final String  first, String  middle, final String last, String tintype, final String tin, final String  phone, final String  property_id, String  email, String  area, String street, String house, String landsize, String area_class, final String lga, String zone, String category, String landmark, String property_name, double longs, double lat, String mac, String areacode, String lgacode, String building_type, String structrure, String occupation, String place_of_work, String property_type) {
        Log.d(MainActivity.class.getSimpleName(),"Building type : "+ building_type);
        Log.d(MainActivity.class.getSimpleName(),"Structure type : "+ structrure);
        this.property = property_id;
        new SQLHelper(FormActivity_03.this).addRecord(new DataClass(staff,title,first,middle,last,tintype,tin,phone,property_id,email,area,street,house,landsize,area_class,lga,zone,category,landmark,property_name,longs, lat, mac, areacode,lgacode,building_type,structrure,occupation,place_of_work, property_type));
       // String image = getStringImage(bitmap);
       // new ImageSQLHelper(FormActivity_03.this).insertImage(property,image);

        Log.d(MainActivity.class.getSimpleName(),"Land Size logger class : "+ landsize);
          progressDialog = new ProgressDialog(FormActivity_03.this);
        progressDialog.setMessage("Please wait..."); // Setting Message
        progressDialog.setTitle("Sending Property Record."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        Map<String, String> params = new HashMap<>();
        params.put("tin", tin);
        params.put("tin_type", tintype);
        params.put("title", title);
        params.put("firstname", first);
        params.put("middlename", middle);
        params.put("lastname", last);
        params.put("gender", "");
        params.put("address", area);
        params.put("email", email);
        params.put("phone", phone);
        params.put("contact_name", "");
        params.put("contact_phone", "");
        params.put("property_id", property_id);
        params.put("area", area);
        params.put("street", street);
        params.put("house_no", house);
        params.put("plot_no", "fdgrvrgr");
        params.put("land_mark", landmark);
        params.put("property_type", area_class);
        params.put("property_name", property_name);
        params.put("area_size", landsize);
        params.put("longitude", ""+longs);
        params.put("latitude", ""+lat);
        params.put("category", category);
        params.put("lga_code", lgacode);
        params.put("lga", lga);
        params.put("area_code", areacode);
        params.put("area_class", area_class);
        params.put("zone", zone);
        params.put("agent_id", staff);
        params.put("mac", mac);
        params.put("building_type", building_type);
        params.put("structure_type",structrure);
        params.put("tin_type", tintype);
        params.put("occupation", occupation);
        params.put("place_of_work", place_of_work);
        JsonObjectRequest loginForm = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      progressDialog.setMessage(response.toString());
                            progressDialog.dismiss();
                        new SQLHelper(FormActivity_03.this).update_property_status_online(property);
                        uploadImage(property, first, last, tin, lga , phone);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                new SQLHelper(FormActivity_03.this).update_property_status_offline(property);
                new ImageSQLHelper(FormActivity_03.this).update_image_status_offline(property);

                progressDialog.setMessage(error.toString());
                progressDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.e(TAG, error.getMessage());
                } else if (error instanceof AuthFailureError) {
                    Log.e(TAG, error.getMessage());
                } else if (error instanceof ServerError) {
                    Log.e(TAG, error.getMessage());
                } else if (error instanceof NetworkError) {
                    Log.e(TAG, error.getMessage());
                } else if (error instanceof ParseError) {
                    Log.e(TAG, error.getMessage());
                }
            }
        });
        loginForm.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(loginForm,TAG);


    }

    public String getValueAtPostion(int position){
        String value = "";

        if (position == 0){
            value = "200";
        }else if(position == 1){
            value = "201";
        }else if(position == 2){
            value = "202";
        }else if(position == 3){
            value = "203";
        }
        else if(position == 4){
            value = "204";
        }
        else if(position == 5){
            value = "205";
        }
        else if(position == 6){
            value = "206";
        }
        else if(position == 7){
            value = "207";
        }
        else if(position == 8){
            value = "208";
        }
        else if(position == 9){
            value = "209";
        }
        else if(position == 10){
            value = "210";
        }
        else if(position == 11){
            value = "211";
        }
        else if(position == 12){
            value = "212";
        }
        else if(position == 13){
            value = "213";
        }
        else if(position == 14){
            value = "214";
        }
        else if(position == 15){
            value = "215";
        }


        return value;
    }


    public String getValueAtPostionLga(int position){
        String value = "";

        if (position == 0){
            value = "100";
        }else if(position == 1){
            value = "101";
        }else if(position == 2){
            value = "102";
        }else if(position == 3){
            value = "103";
        }
        else if(position == 4){
            value = "104";
        }
        else if(position == 5){
            value = "105";
        }
        else if(position == 6){
            value = "106";
        }
        else if(position == 7){
            value = "107";
        }
        else if(position == 8){
            value = "108";
        }
        else if(position == 9){
            value = "109";
        }
        else if(position == 10){
            value = "110";
        }
        else if(position == 11){
            value = "111";
        }
        else if(position == 12){
            value = "112";
        }
        else if(position == 13){
            value = "113";
        }
        else if(position == 14){
            value = "114";
        }
        else if(position == 15){
            value = "115";
        }


        return value;
    }

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void getInternetStatus(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            submit.setVisibility(View.VISIBLE);
                            submit_offline.setVisibility(View.INVISIBLE);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                submit.setVisibility(View.INVISIBLE);
                submit_offline.setVisibility(View.VISIBLE);

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}


