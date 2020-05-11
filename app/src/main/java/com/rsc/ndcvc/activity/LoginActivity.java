package com.rsc.ndcvc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rsc.ndcvc.R;
import com.rsc.ndcvc.Urls;
import com.rsc.ndcvc.databinding.ActivityLoginBinding;
import com.rsc.ndcvc.util.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private String BUNDLE_TRAN_NAME = "user_data";
    private ActivityLoginBinding bdx;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind data here
        this.bdx = DataBindingUtil.setContentView(this, R.layout.activity_login);
        this.ctx = this;
        //load main
        start();
    }

    void start() {
        bdx.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepare login
                String email = bdx.loginEmailid.getText().toString();
                String pass = bdx.loginPassword.getText().toString();
                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(ctx, "No valid user credentials", Toast.LENGTH_LONG).show();
                    return;
                }
                //proceed to login
                loginUser(email, pass);
                pd.setMessage("Processing login...");
            }
        });
        //check if login do automatic login
        if (Tools.isLogged(ctx)) {
            String email = Tools.getEmail(ctx);
            String pass = Tools.getPass(ctx);
            loginUser(email, pass);
            pd.setMessage("Verifying logged user...");
        }
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

    //do login
    void loginUser(String email, String password) {
        showProgress("Preparing...");
        try {
            AndroidNetworking.post(Urls.URL_LOGIN)
                    .addBodyParameter("email", email)
                    .addBodyParameter("pass", password)
                    .addHeaders("rdx-locker", Urls.BASE_TOKEN)
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //object received
                            if (response != null) {
                                try {
                                    //check if logged
                                    if (response.getBoolean("status")) {
                                        //is logged in
                                        pd.setMessage("Successfully logged in...");
                                        //check and open new page
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    pd.hide();
                                                    if (response.getJSONObject("data").getInt("utype") == 2) {
                                                        //save logged user
                                                        Tools.setUser(ctx, email, password);
                                                        //open lecturers page
                                                        Intent il = new Intent(ctx, MainActivityLect.class);
                                                        il.putExtra(BUNDLE_TRAN_NAME, response.getJSONObject("data").toString());
                                                        startActivity(il);
                                                        finish();
                                                    } else if (response.getJSONObject("data").getInt("utype") == 0) {
                                                        //save logged user
                                                        Tools.setUser(ctx, email, password);
                                                        //student page
                                                        Intent il = new Intent(ctx, MainActivity.class);
                                                        il.putExtra(BUNDLE_TRAN_NAME, response.getJSONObject("data").toString());
                                                        startActivity(il);
                                                        finish();
                                                    } else {
                                                        Tools.showToast(ctx, "Super user not allowed on mobile...");
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    Tools.showToast(ctx, "Error login...");
                                                    pd.hide();
                                                }
                                            }
                                        }, 2000);
                                    } else {
                                        Tools.showToast(ctx, "Invalid login details");
                                        pd.hide();
                                        Tools.deleteUser(ctx);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Tools.showToast(ctx, "Error login...");
                                    pd.hide();
                                }
                            } else {
                                Log.e("LOGIN_ERROR-INN", response.toString());
                                pd.hide();
                                Tools.showToast(ctx, "Unable to login at the moment");
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            //error occurred
                            pd.hide();
                            Log.e("LOGIN_ERROR-IN", Objects.requireNonNull(anError.getMessage()));
                            Tools.showToast(ctx, "Unable to login at the moment");
                        }
                    });
        } catch (Exception ex) {
            Log.e("LOGIN_ERROR", Objects.requireNonNull(ex.getMessage()));
            pd.hide();
            Tools.showToast(ctx, "Unable to login at the moment");
        }
    }
}
