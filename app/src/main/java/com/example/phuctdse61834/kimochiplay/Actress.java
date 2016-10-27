package com.example.phuctdse61834.kimochiplay;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PhucTDSE61834 on 10/26/2016.
 */

public class Actress {
    private String id;
    private String name;
    private String japName;
    private String bust;
    private String waist;
    private String hip;
    private String height;
    private Date birthday;
    private String imgURL;

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    // JSON key
    public static final String JSON_ARRAY = "result";
    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_JAPNAME = "japanName";
    private static final String JSON_BUST = "bust";
    private static final String JSON_WAIST = "waist";
    private static final String JSON_HIP = "hip";
    private static final String JSON_HEIGHT = "height";
    private static final String JSON_BIRTHDAY = "birthday";
    private static final String JSON_IMGURL = "imageUrl";

    public Actress() {
    }

    public Actress(JSONObject jo) throws JSONException{
        id = jo.getString(JSON_ID);
        name = jo.getString(JSON_NAME);
        japName = jo.getString(JSON_JAPNAME);
        bust = jo.getString(JSON_BUST);
        waist = jo.getString(JSON_WAIST);
        hip = jo.getString(JSON_HIP);
        height = jo.getString(JSON_HEIGHT);
        birthday = convertDate(jo.getString(JSON_BIRTHDAY));
        imgURL = jo.getString(JSON_IMGURL);
    }

    public String getHeight() {
        return height;
    }

    public String getHip() {
        return hip;
    }

    public String getWaist() {
        return waist;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public void setHip(String hip) {
        this.hip = hip;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJapName() {
        return japName;
    }

    public void setJapName(String japName) {
        this.japName = japName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Nullable
    private Date convertDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try{
            Date date = sdf.parse(dateString);
            return date;
        }
        catch (ParseException e){
            return null;
        }
    }
}
