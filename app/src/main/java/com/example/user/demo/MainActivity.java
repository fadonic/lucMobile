package com.example.user.demo;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button button, history, demand;
    TextView  staffId;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String UPLOAD_URL ="https://luc.ekitistaterevenue.com/image_upload/upload.php";
    private String UPLOAD_URL_DEMAND ="https://luc.ekitistaterevenue.com/image_upload/demand_upload.php";
    private String url = "https://luc.ekitistaterevenue.com/luc_enum.php";
    private ImageView network, battery, location, sync_data;
    private String TAG = MainActivity.class.getSimpleName();
    boolean internet_status = false;

    private TextView batteryTxt;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            if (level <= 20) {
                battery.setBackgroundColor(Color.RED);
            } else {
                battery.setBackgroundColor(Color.GREEN);
            }

        }
    };

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        history = (Button)findViewById(R.id.history);
        demand = (Button) findViewById(R.id.demand);
        button = (Button) findViewById(R.id.proceed);
        staffId = (TextView) findViewById(R.id.staff);
        textview = (TextView) findViewById(R.id.textView);
        network = (ImageView) findViewById(R.id.network);

        location = (ImageView) findViewById(R.id.location);
        location.setBackgroundColor(Color.GREEN);
        sync_data = (ImageView) findViewById(R.id.sync_data);
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:MM");
        textview.setText(formatter.format(today));
        blink();
        battery = (ImageView) this.findViewById(R.id.battery);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        HashMap<String, String>  map = new ImageSQLHelper(this).fetchImageRecords();
        Log.d(MainActivity.class.getSimpleName(),  map.toString());
        HashMap<String, String>  demand_map = new ImageSQLHelper(this).fetch_demand_ImageRecords();

        Log.d(MainActivity.class.getSimpleName(),   demand_map.toString());



        getInternetStatus(map, demand_map);
        String agent_id = new ImageSQLHelper(this).get_user_identity();
        staffId.setText(""+agent_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FormActivity.class);
                startActivity(i);

            }
        });

        int total_records = new SQLHelper(this).getOnlineCounts();
        history.setText("History ("+total_records+")");
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);

            }
        });


        demand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DemandActivity.class);
                startActivity(i);

            }
        });



        //Internet Status
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean loop = true;
                try {
                    while (loop) {
                        final InternetConnectionClass detector = new InternetConnectionClass(getApplicationContext());

                        if (detector.isConnectingToInternet()) {
                            network.setBackgroundColor(Color.GREEN);

                        } else {
                            network.setBackgroundColor(Color.RED);

                        }
                    }

                    Thread.sleep(1000);
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        });
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sync:
                return true;
            case R.id.action_logout:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getInternetStatus(final HashMap<String,String> map,final HashMap<String,String> demand_map){
       final int demand_count = new ImageSQLHelper(this).getdemandCounts();
        final int data = new SQLHelper(this).getOfflineCounts();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if(data > 0){
                            sync_data.setBackgroundColor(Color.RED);
                            open(map);

                            if(demand_count > 0){
                                upload_demand_image(demand_map);
                            }

                        }else{
                            sync_data.setBackgroundColor(Color.GREEN);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    public void pushOfflineRecords() throws JSONException {

            String json = new SQLHelper(this).getAllOfflineRecords();
            Log.d(TAG, json);
            JsonArrayRequest loginForm = new JsonArrayRequest(com.android.volley.Request.Method.POST,
                    url, new JSONArray(json), new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, "Fridays response ....."+response.toString());
                        new SQLHelper(MainActivity.this).update_all_property_status();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Fridays error response ....."+error.toString());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Log.e(TAG, "" + error.getMessage());
                    } else if (error instanceof AuthFailureError) {
                        Log.e(TAG, "" + error.getMessage());
                    } else if (error instanceof ServerError) {
                        Log.e(TAG, "" + error.getMessage());
                    } else if (error instanceof NetworkError) {
                        Log.e(TAG, "" + error.getMessage());
                    } else if (error instanceof ParseError) {
                        Log.e(TAG, "" + error.getMessage());
                    }
                }
            });

        loginForm.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(loginForm,TAG);

    }

    private void blink() {
        final Handler hander = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(550);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hander.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textview.getVisibility() == View.VISIBLE){
                            //  textview.setVisibility(View.INVISIBLE);
                        }else{
                            textview.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }

    public void open(final HashMap<String, String>  map) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final int data_02 = new SQLHelper(this).getOfflineCounts();
        alertDialogBuilder.setMessage("You have "+data_02+" Records Offline!! Would You Like To Sync Them?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            pushOfflineRecords();
                            upload_Image(map);
                                Toast.makeText(getApplicationContext(), "Sync Completed ", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "IOException ", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    public void upload_Image(final HashMap<String, String> map) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG,"Image Sent............."+map.get("name")+"....."+s);
                        new ImageSQLHelper(MainActivity.this).update_image_status(map.get("name"));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        new ImageSQLHelper(MainActivity.this).update_image_status_offline(map.get("name"));
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String

                Map<String, String> params = new HashMap<>();
                //Creating parameters
                String image = map.get("image");
                String property = map.get("name");

                //Adding parameters
                params.put(KEY_IMAGE,image);
                params.put(KEY_NAME,property);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //set Retry Ploicy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    //upload demand
    public void upload_demand_image(final HashMap<String, String> map) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL_DEMAND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG,"Image Sent............."+map.get("name")+"....."+s);
                        new ImageSQLHelper(MainActivity.this).update_demand_image_status(map.get("name"));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        new ImageSQLHelper(MainActivity.this).update_demand_image_status_offline(map.get("name"));
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String

                Map<String, String> params = new HashMap<>();
                //Creating parameters
                String image = map.get("image");
                String property = map.get("name");

                //Adding parameters
                params.put(KEY_IMAGE,image);
                params.put(KEY_NAME,property);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
