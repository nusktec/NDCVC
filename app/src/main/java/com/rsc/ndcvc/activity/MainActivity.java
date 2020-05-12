package com.rsc.ndcvc.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rsc.ndcvc.R;
import com.rsc.ndcvc.Urls;
import com.rsc.ndcvc.adapters.AdpClass;
import com.rsc.ndcvc.databinding.ActivityMainBinding;
import com.rsc.ndcvc.models.ModelListClass;
import com.rsc.ndcvc.util.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding bdx;
    private Context ctx;
    private JSONObject userObj;
    private JSONObject userData;
    private String uid;
    private String uname;
    private String vtoken;
    private String class_name;
    private List<ModelListClass> modelListClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx = this;
        this.bdx = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //set up bundle data
        try {
            userObj = new JSONObject(Objects.requireNonNull(getIntent().getStringExtra(LoginActivity.BUNDLE_TRAN_NAME)));

        } catch (JSONException e) {
            Tools.showToast(ctx, "Please re-login");
            Tools.deleteUser(ctx);
            finish();
        }
        //start loading
        try {
            setToolBar();
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.showToast(ctx, "Please re-login");
            Tools.deleteUser(ctx);
            finish();
        }
    }

    //set toolbar
    void setToolBar() throws JSONException {
        setSupportActionBar(bdx.toolbar);
        if (userObj != null) {
            setTitle(Objects.requireNonNull(userObj.getString("uname")));
            Objects.requireNonNull(getSupportActionBar()).setSubtitle("Student: " + userObj.getString("ucountry"));
            uid = userObj.getString("uid");
            uname = userObj.getString("uname");
            class_name = uname + "~" + uid + "~" + "100|CLASS-" + "XXXXXXXXXX";
            checkPolicies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_student, menu);
        return true;
    }

    //check account status
    void checkPolicies() throws JSONException {
        showProgress("Checking Account Status");
        //loading account info
        if (userObj.getInt("ustatus") == 0) {
            Tools.showToast(ctx, "Your account is been locked !");
            Tools.deleteUser(ctx);
            startActivity(new Intent(ctx, LoginActivity.class));
            finish();
        }
        //start fetching
        loader();
    }

    ProgressDialog pd;

    void showProgress(String msg) {
        //check if user data this
        pd = new ProgressDialog(ctx);
        pd.setTitle("Please wait");
        pd.setMessage(msg);
        pd.setCancelable(false);
        pd.show();
    }

    //load main data
    void loader() throws JSONException {
        AndroidNetworking.post(Urls.URL_STD_CLASS_GET)
                .addHeaders("rdx-locker", Urls.BASE_TOKEN)
                .setTag(this)
                .addBodyParameter("uid", uid)
                .addBodyParameter("vtoken", class_name)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                userData = response.getJSONObject("data");
                                vtoken = userData.getString("vtoken");
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<ModelListClass>>() {
                                }.getType();
                                modelListClasses = gson.fromJson(userData.getJSONArray("classes").toString(), listType);
                                pd.hide();
                                main();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.setMessage("Server busy. code 100");
                        Tools.showToast(ctx, "Please close the application and tray again");
                    }
                });
    }

    void main() {
        if (modelListClasses.size() < 1) {
            bdx.classInfo.setVisibility(View.VISIBLE);
            return;
        }
        bdx.classInfo.setVisibility(View.GONE);
        //list to output
        AdpClass aClass = new AdpClass(modelListClasses, new AdpClass.onClassClick() {
            @Override
            public void goLive(ModelListClass md) {
                //click to open class
                if (md.getUlive().equals("0")) {
                    Tools.showToast(ctx, "No Lecturer present !");
                    return;
                }
                //start main activity
                startClass("CLASS-" + md.getCpermit());
            }
        });
        bdx.ryc.setAdapter(aClass);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_stud_mgr) {
            manageAccount();
        }
        if (id == R.id.menu_stud_add_class) {
            manageAccount();
        }
        if (id == R.id.menu_stud_logout) {
            logout();
        }
        if (id == R.id.menu_stud_reload) {
            try {
                checkPolicies();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //logout
    void logout() {
        AlertDialog.Builder al = new AlertDialog.Builder(ctx);
        al.setTitle("Logout");
        al.setMessage("Do you want to logout ?");
        al.setCancelable(false);
        al.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Tools.showToast(ctx, "Please re-login");
                Tools.deleteUser(ctx);
                dialog.dismiss();
                finish();
            }
        });
        al.create().show();
    }

    //open to manager account
    void manageAccount() {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Urls.URL_WEB_BASE + "/login?token=" + userObj.getString("utoken")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String title = "Short Link - NDCVC";
        Intent chooser = Intent.createChooser(intent, title);
        startActivity(chooser);
    }

    //start class
    void startClass(String cln) {
        Intent i = new Intent(this, VideoActivity.class);
        i.putExtra("vtoken", vtoken);
        i.putExtra("uname", uname);
        i.putExtra("classname", cln);
        i.putExtra("islect", false);
        startActivity(i);
    }
}