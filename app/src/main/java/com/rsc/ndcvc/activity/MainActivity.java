package com.rsc.ndcvc.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rsc.ndcvc.R;
import com.rsc.ndcvc.databinding.ActivityMainBinding;
import com.rsc.ndcvc.util.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding bdx;
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
            Objects.requireNonNull(getSupportActionBar()).setSubtitle("Student");
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
    void checkPolicies() {

    }
}