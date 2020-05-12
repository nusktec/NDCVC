package com.rsc.ndcvc.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.rsc.ndcvc.R;
import com.rsc.ndcvc.Urls;
import com.rsc.ndcvc.databinding.ActivityMainLectBinding;
import com.rsc.ndcvc.util.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivityLect extends AppCompatActivity {

    private ActivityMainLectBinding bdx;
    private Context ctx;
    private JSONObject userObj;
    private JSONObject userData;
    private String permit;
    private String uid;
    private String uname;
    private String vtoken;
    private String class_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx = this;
        this.bdx = DataBindingUtil.setContentView(this, R.layout.activity_main_lect);
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
            Objects.requireNonNull(getSupportActionBar()).setSubtitle("Lecturer: " + userObj.getString("ucountry"));
            permit = userObj.getString("upermit");
            uid = userObj.getString("uid");
            uname = userObj.getString("uname");
            checkPolicies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_lecturer, menu);
        return true;
    }

    //check account status
    void checkPolicies() throws JSONException {
        showProgress("Checking Account Status");
        //loading account info
        if (userObj.getInt("ustatus") == 0) {
            Tools.showToast(ctx, "Your account has been locked !");
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
        class_name = uname + "~" + uid + "~" + "200|CLASS-" + permit;
        AndroidNetworking.post(Urls.URL_ADM_DATA)
                .addHeaders("rdx-locker", Urls.BASE_TOKEN)
                .setTag(this)
                .addBodyParameter("permit", permit)
                .addBodyParameter("vtoken", class_name)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                userData = response.getJSONObject("data");
                                vtoken = response.getJSONObject("data").getString("vtoken");
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
        //get button loaded
        bdx.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classStatus("1", true);
            }
        });

        bdx.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    //apply online data
    void main() throws JSONException {
        bdx.txtId.setText(permit);
        bdx.classTotal.setText(userData.getString("att"));
        bdx.classAttend.setText(userData.getString("yes"));
    }

    //prepare menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_lect_logout) {
            logout();
        }
        if (id == R.id.menu_lect_endclass) {
            classStatus("0", false);
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

    //end class
    void classStatus(String ss, boolean callLive) {
        showProgress("Changing class status");
        AndroidNetworking.post(Urls.URL_ADM_CLASS_STATUS)
                .addHeaders("rdx-locker", Urls.BASE_TOKEN)
                .setTag(this)
                .addBodyParameter("permit", permit)
                .addBodyParameter("status", ss)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                Tools.showToast(ctx, response.getString("msg"));
                                pd.hide();
                                if (callLive && ss.equals("1")) {
                                    startClass();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Tools.showToast(ctx, "Please close the application and tray again");
                    }
                });
    }

    //start class
    void startClass() {
        Intent i = new Intent(this, VideoActivity.class);
        i.putExtra("vtoken", vtoken);
        i.putExtra("uname", uname);
        i.putExtra("classname", class_name);
        i.putExtra("islect", true);
        startActivity(i);
    }
}