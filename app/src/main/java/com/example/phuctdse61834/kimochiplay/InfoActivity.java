package com.example.phuctdse61834.kimochiplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        setTitle("Profile");

        // get JSON String and parse to Actress
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("JSON_VALUE");
        Gson gson = new Gson();
        Actress actress = gson.fromJson(jsonString, Actress.class);

        // get Profile UI
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        TextView txtName = (TextView)findViewById(R.id.textView3);
        TextView txtJapName = (TextView)findViewById(R.id.textView4);
        TextView txtHip = (TextView)findViewById(R.id.text_hip);
        TextView txtWaist = (TextView)findViewById(R.id.text_waist);
        TextView txtBust = (TextView)findViewById(R.id.text_bust);
        TextView txtBirth = (TextView)findViewById(R.id.text_birthday);
        TextView txtHeight = (TextView)findViewById(R.id.text_height);

        // now put info to view
        txtName.setText(actress.getName());
        txtJapName.setText(actress.getJapName());
        txtBust.setText(actress.getBust());
        txtWaist.setText(actress.getWaist());
        txtHip.setText(actress.getHip());
        txtBirth.setText(dateToString(actress.getBirthday()));
        txtHeight.setText(actress.getHeight());
        Picasso.with(this)
                .load(actress.getImgURL())
                .placeholder(R.drawable.up_ban)
                .error(R.drawable.up_ban)
                .into(imageView);
    }

    private String checkNullString(String s){
        return s != null ? s : "";
    }

    private String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = "";
        try {
            dateStr = df.format(date);
        }catch (Exception e){

        }
        return dateStr;
    }
}
