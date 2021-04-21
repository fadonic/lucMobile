package com.example.user.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {
        Button button;
        String name, last, id, tin, lga, phn;
        TextView detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);


        detail = (TextView) findViewById(R.id.details);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            name = b.get("s_name").toString();
            last = b.get("s_last").toString();
            tin = b.get("s_tin").toString();
            lga = b.get("s_lga").toString();
            id = b.get("s_prop").toString();
            phn = b.get("s_phn").toString();

            detail.setText("Name : " + name + '\n' +
                    "Last-name : " + last + '\n' +
                    "Tin : " + tin + '\n' +
                    "Property ID : " + id + '\n' +
                    "Local Govt : " + lga+ '\n' +
                    "Phone : " + phn);

        }

        button = (Button) findViewById(R.id.home_6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessActivity.this, MainActivity.class));
            }
        });
    }
}
