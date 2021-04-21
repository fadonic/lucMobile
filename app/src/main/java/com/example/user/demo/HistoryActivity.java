package com.example.user.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    SQLHelper db;
    Button rtn;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textView = (TextView) findViewById(R.id.enumTxt);

        int count = new SQLHelper(this).getOnlineCounts();
        textView.setText("Total Records : "+count);

        db = new SQLHelper(this);
        List<DataClass> players = db.allPlayers();


        rtn = (Button) findViewById(R.id.home_04);
        rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, MainActivity.class));
            }
        });


        if (players != null) {
            String[] itemsNames = new String[players.size()];

            for (int i = 0; i < players.size(); i++) {
                itemsNames[i] = players.get(i).toString();
               // Log.d(HistoryActivity.class.getSimpleName(),itemsNames[i]);
            }

            // display like string instances
            ListView list = (ListView) findViewById(R.id.list);
            list.setAdapter(new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, itemsNames));

        }

    }
}
