package com.example.user.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FormActivity extends AppCompatActivity {

    Button next_01;
    TextView  first, middle, last, tin, phone, staffss;
    Spinner title,tintype;
    String staff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        title = (Spinner) findViewById(R.id.title_01);
        tintype = (Spinner)findViewById(R.id.tintype);
        first = (TextView)findViewById(R.id.firstName);
        middle = (TextView) findViewById(R.id.middleName);
        last = (TextView)findViewById(R.id.lastName);
        tin = (TextView) findViewById(R.id.tin);
        phone = (TextView) findViewById(R.id.phone);
        staffss = (TextView) findViewById(R.id.staffss);


        next_01 = (Button) findViewById(R.id.next_01);
        next_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(first.getText().toString())) {
                    first.setError("First-name Cannot Be Empty.");

                }else if(TextUtils.isEmpty(last.getText().toString())) {
                    last.setError("Last-name Cannot Be Empty.");

                }else if(TextUtils.isEmpty(phone.getText().toString())) {
                    phone.setError("Phone Cannot Be Empty.");
                }else if(TextUtils.isEmpty(tin.getText().toString())) {
                    tin.setError("EK-Tin Cannot Be Empty.");
                }else{

                    Intent i = new Intent(FormActivity.this, FormActivity_02.class);
                    i.putExtra("title", title.getSelectedItem().toString());
                    i.putExtra("tintype", tintype.getSelectedItem().toString());
                    i.putExtra("first", first.getText());
                    i.putExtra("middle", middle.getText());
                    i.putExtra("last", last.getText());
                    i.putExtra("tin", tin.getText());
                    i.putExtra("phone", phone.getText());
                    startActivity(i);
                }

            }
        });
    }
}
