package com.tali.admin.bustracker;

import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 23.08.2016.
 */
public class Session {

    private static final String NAME = "name";
    private static final String MIDDLENAME = "middlename";
    private static final String REGION = "region";
    private static final String PHOTOPATH = "photoUrl";
    private static final String ID = "digitsId";
    private static final String NUMBER = "phoneNumber";
    private static final String LINK = "link";
    private static SharedPreferences preferences = App.getUserPreferences();

    public static String getUserInformation() {
        return "User{" +
                "name='" + getName() + '\'' +
                ", middleName='" + getMiddleName() + '\'' +
                ", region='" + getRegion() + '\'' +
                ", photoPath='" + getPhotoUri() + '\'' +
                ", number='" + getNumber() + '\'' +
                ", digitsId=" + getId() +
                '}';
    }

    public static void setUserInformation(String name, String middlename, String region, String photoPath, String number, String digitsId) {
        setName(name);
        setMiddleName(middlename);
        setRegion(region);
        setPhotoUri(photoPath);
        setNumber(number);
        setId(digitsId);
        pullToServer();
    }

    public static boolean isLogged(){
        if (getName().equals("")){
            return false;
        }else {
            return true;
        }
    }

    public static String getName() {
        if (preferences.contains(NAME)) {
            return preferences.getString(NAME, "");
        }
        return "";
    }

    public static void setName(String name) {
        getEditor().putString(NAME, name).commit();
    }

    public static String getLink() {
        if (preferences.contains(LINK)) {
            return preferences.getString(LINK, "");
        }
        return "";
    }

    public static void setLink(String link) {
        getEditor().putString(LINK, link).commit();
    }

    public static String getMiddleName() {
        if (preferences.contains(MIDDLENAME)) {
            return preferences.getString(MIDDLENAME, "");
        }
        return "";
    }

    public static void setMiddleName(String middleName) {
        getEditor().putString(MIDDLENAME, middleName).commit();
    }

    public static String getRegion() {
        if (preferences.contains(REGION)) {
            return preferences.getString(REGION, "");
        }
        return "";
    }

    public static void setRegion(String region) {
        getEditor().putString(REGION, region).commit();
    }

    public static String getPhotoUri() {
        if (preferences.contains(PHOTOPATH)) {
            return preferences.getString(PHOTOPATH, "");
        }
        return "";
    }

    public static void setPhotoUri(String photoPath) {
        getEditor().putString(PHOTOPATH, photoPath).commit();
    }

    public static long getId() {
        if (preferences.contains(ID)) {
            return preferences.getLong(ID, 0);
        }
        return 0;
    }

    public static void setId(String digitsId) {
        getEditor().putString(ID, digitsId).commit();
    }

    public static String getNumber() {
        if (preferences.contains(NUMBER)) {
            return preferences.getString(NUMBER, "");
        }
        return "";
    }

    public static void setNumber(String number) {
        getEditor().putString(NUMBER, number).commit();
    }

    private static SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    public static void clearUserPreferences() {
        getEditor().clear().commit();
    }

    public static void pullToServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add(NAME,getName())
                        .add(MIDDLENAME,getMiddleName())
                        .add(REGION,getRegion())
                        .add(PHOTOPATH,getPhotoUri())
                        .add(NUMBER,getNumber())
                        .add(ID,String.valueOf(getId()))
                        .build();
                Request request = new Request.Builder()
                        .url("http://176.126.167.231:86/import/")
                        .post(formBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
