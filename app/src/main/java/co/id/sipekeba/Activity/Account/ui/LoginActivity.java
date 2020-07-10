package co.id.sipekeba.Activity.Account.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.sipekeba.AppController;
import co.id.sipekeba.Constants;
import co.id.sipekeba.MainActivity;
import co.id.sipekeba.R;
import co.id.sipekeba.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppController {

    public static final String TAG = LoginActivity.class.getSimpleName();
    ProgressDialog pDialog;

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtRegister)
    TextView txtRegister;
    @BindView(R.id.met_username)
    MaterialEditText metUsername;
    @BindView(R.id.met_password)
    MaterialEditText metPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        beforeFilter(LoginActivity.this);

        activity = this;
        initViews();
    }

    private void initViews() {

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        metUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        metPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


    }

    @OnClick(R.id.btnLogin)
    public void Clicklogin() {
        String password = metPassword.getText().toString();
        String username = metUsername.getText().toString();

        if (username.isEmpty()) {
            metUsername.setError("Insert username");
        } else if (!Utilities.isValidPassword(password)) {
            metPassword.setError("Password not valid");
        } else {
            SendingData(username, password);
        }
    }

    @OnClick(R.id.txtRegister)
    public void Register() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        i.putExtra("name", "Test");
        startActivity(i);
    }

    private void enableSubmitIfReady() {
        boolean isReady = metPassword.getText().toString().length() > 1 &&
                metUsername.getText().toString().length() > 1;
        btnLogin.setEnabled(isReady);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    void SendingData(String username, String password) {
        dialogLoading = new ProgressDialog(LoginActivity.this);
        dialogLoading.setCancelable(true);
        dialogLoading.setCanceledOnTouchOutside(true);
        dialogLoading.setMessage(getResources().getString(R.string.please_wait));
        dialogLoading.show();
//        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url  = Constants.Extra.api_url+"Users/login";

        // Request a string response from the provided URL.
        StringRequest jsonObjReq = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        dialogLoading.dismiss();
                        try
                        {
                            JSONObject json                 =   new JSONObject(response);
                            Log.e(TAG, "RESPONSE : " + json);

                            int status                      =   json.getInt("code");
                            String message                  =   json.getString("message");

                            if(status == 200)
                            {
                                JSONObject data 		    = 	json.getJSONObject("data");
                                JSONObject User 		    = 	data.getJSONObject("Users").getJSONObject("0");

//                                JSONObject Images		    =	data.getJSONObject("Images");

                                String user_id 			    = 	User.getString("id");
                                String email			    = 	User.getString("email");
                                String fullname	 		    = 	User.getString("nama");
//                                String aro_id			    = 	User.getString("aro_id");

                                String avatar			    =	"-";

//                                if(!Images.getString("id").equals("null") && !Images.getString("id").isEmpty())
//                                {
//                                    avatar	=	Images.getString("host")+Images.getString("url")+"?time="+Images.getString("modified");
//                                }

                                Log.d(TAG, "RESPONSE : "+user_id+" , "+email+" , "+fullname+" , "+avatar);
                                //CREATE LOGIN SESSION
                                UserFunction.createLoginSession(
                                        user_id,
                                        fullname,
                                        email,
                                        avatar
                                );

                                //DIRECT ACCOUNT
//                                DirectAccount();

                                Log.d(TAG, UserFunction.getFullname());

                                startActivity(new Intent(activity, MainActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                finish();

                            }
                            else
                            {
                                showAlertDialog(
                                        activity.getResources().getString(R.string.login_failed),
                                        message, false);
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            showAlertDialog(
                                    activity.getResources().getString(R.string.login_failed),
                                    "Error from apps with message: " + e.getMessage(), false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "ERROR:" + error.getMessage());
                        dialogLoading.dismiss();

                        showAlertDialog(
                                activity.getResources().getString(R.string.login_failed),
                                activity.getResources().getString(R.string.failed_connect), false);
                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("token", Constants.Extra.token);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        //SET TAG
        jsonObjReq.setTag(TAG);

        //SET TIMEOUT
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(Constants.Extra.TIME_OUT_REQUEST, 1, 1.0f));

        // Adding request to request queue
        queue.add(jsonObjReq);
    }


}