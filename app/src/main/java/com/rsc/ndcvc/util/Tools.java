package com.rsc.ndcvc.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Tools {
    private static String STORE_LOGIN_ID = "acc_id_001";
    private static String STORE_LOGIN_ID2 = "acc_id_002";
    private static String STORE_NAME = "acc_id_store";

    //save preference
    public static boolean setUser(Context ctx, String email, String pass) {
        SharedPreferences pref = ctx.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        pref.edit().putString(STORE_LOGIN_ID, email).apply();
        pref.edit().putString(STORE_LOGIN_ID2, pass).apply();
        return true;
    }

    //delete preference
    public static boolean deleteUser(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        pref.edit().clear().apply();
        return true;
    }

    //delete preference
    public static boolean isLogged(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        return !pref.getString(STORE_LOGIN_ID, "").isEmpty() && !pref.getString(STORE_LOGIN_ID2, "").isEmpty();
    }

    //get details
    public static String getEmail(Context ctx) {
        return ctx.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE).getString(STORE_LOGIN_ID, "");
    }

    public static String getPass(Context ctx) {
        return ctx.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE).getString(STORE_LOGIN_ID2, "");
    }

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
