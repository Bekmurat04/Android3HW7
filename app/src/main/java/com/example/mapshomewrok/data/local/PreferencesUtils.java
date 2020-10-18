package com.example.mapshomewrok.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

public class PreferencesUtils {

    private static SharedPreferences mPreference;
    private final static String APP_PREF_NAME = "kg.geektech.lesson1";

    public static void init(Context context) {
        mPreference = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
    }

//    public static void saveFigure(LatLng latLngs){
//        mPreference.edit().put
//    }
//
//    public static LatLng getFigure(){
//
//    }
}
