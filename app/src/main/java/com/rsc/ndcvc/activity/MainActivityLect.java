package com.rsc.ndcvc.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rsc.ndcvc.R;
import com.rsc.ndcvc.databinding.ActivityMainLectBinding;
import com.rsc.ndcvc.util.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivityLect extends AppCompatActivity {

    private ActivityMainLectBinding bdx;
    private Context ctx;
    private JSONObject userObj;

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
            Objects.requireNonNull(getSupportActionBar()).setSubtitle("Lecturer");
            checkPolicies();
        }
    }

    //check account status
    void checkPolicies() {

    }
}