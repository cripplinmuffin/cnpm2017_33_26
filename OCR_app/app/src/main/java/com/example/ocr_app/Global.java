package com.example.ocr_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class Global {
    public static ArrayList<Account> users = new ArrayList<>();

    public static void saveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        editor.putString("taskList", json);
        editor.apply();
    }

    public static void loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<Account>>() {}.getType();
        users = gson.fromJson(json, type);

        if(users == null)
            users = new ArrayList<>();
    }
}
