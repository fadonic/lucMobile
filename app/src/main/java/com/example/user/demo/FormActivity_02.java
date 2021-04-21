package com.example.user.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FormActivity_02 extends AppCompatActivity {

    Button prev, next;
    String staff, title, first , middle, last, tin, phone,  tntyp;
    TextView property_id, email, area, street, house, landsize;
    TextView stf, tle, fst , mid, lst, tn, phn, tintyp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_02);

        tle = (TextView) findViewById(R.id.ttle);
        fst = (TextView)findViewById(R.id.fst);
        mid  = (TextView) findViewById(R.id.mid);
        lst = (TextView) findViewById(R.id.lst);
        tn = (TextView) findViewById(R.id.tn);
        tintyp = (TextView) findViewById(R.id.tntyp);
        phn =  (TextView)findViewById(R.id.phn);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if(b!=null) {
            title = b.get("title").toString();
            first = b.get("first").toString();
            middle = b.get("middle").toString();
            last = b.get("last").toString();
            tntyp = b.get("tintype").toString();
            tin = b.get("tin").toString();
            phone = b.get("phone").toString();
            tle.setText(title);
            fst.setText(first);
            mid.setText(middle);
            lst.setText(last);
            tn.setText(tin);
            phn.setText(phone);
            tintyp.setText(tntyp);

        }

        property_id = (EditText) findViewById(R.id.property);
        email =  (EditText) findViewById(R.id.email);
        area = (EditText) findViewById(R.id.area);
        street  = (EditText) findViewById(R.id.street);
        house = (EditText) findViewById(R.id.house);
        landsize = (EditText) findViewById(R.id.size);

        prev = (Button) findViewById(R.id.prev_01);
        next = (Button) findViewById(R.id.next_02);

       next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(TextUtils.isEmpty(property_id.getText().toString())) {
                   property_id.setError("Property ID Cannot Be Empty.");
               }else if(TextUtils.isEmpty(email.getText().toString())) {
                   email.setError("Email Cannot Be Empty.");

               }else if(TextUtils.isEmpty(area.getText().toString())) {
                   area.setError("Area Cannot Be Empty.");

               }else if(TextUtils.isEmpty(street.getText().toString())) {
                   street.setError("Street Cannot Be Empty.");
               }else {
                  Intent intent1 = new Intent(FormActivity_02.this, FormActivity_03.class);
                   intent1.putExtra("titles", tle.getText());
                   intent1.putExtra("firsts", fst.getText());
                   intent1.putExtra("middles", mid.getText());
                   intent1.putExtra("lasts", lst.getText());
                   intent1.putExtra("lasts", lst.getText());
                   intent1.putExtra("tin_types", tintyp.getText());
                   intent1.putExtra("tins", tn.getText());
                   intent1.putExtra("phones", phn.getText());
                   intent1.putExtra("property_id", property_id.getText());
                   intent1.putExtra("email", email.getText());
                   intent1.putExtra("area", area.getText());
                   intent1.putExtra("street", street.getText());
                   intent1.putExtra("house", house.getText());
                   intent1.putExtra("land_size", landsize.getText());
                   startActivity(intent1);
               }
           }
       });

       prev.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(FormActivity_02.this, FormActivity.class);
               startActivity(intent);
           }
       });

    }
    }
