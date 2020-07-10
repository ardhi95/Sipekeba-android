package co.id.sipekeba.ui.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import co.id.sipekeba.Activity.Account.ui.LoginActivity;
import co.id.sipekeba.Activity.Account.ui.RegisterActivity;
import co.id.sipekeba.AppApplication;
import co.id.sipekeba.AppController;
import co.id.sipekeba.Constants;
import co.id.sipekeba.MainActivity;
import co.id.sipekeba.R;
import co.id.sipekeba.UserFunctions;

public class AccountFragment extends Fragment {

    public static final String TAG = AccountFragment.class.getSimpleName();

    private AccountViewModel accountViewModel;
    public UserFunctions userFunctions;

    public ProgressDialog dialogLoading;

    //===== Widget =====//
    View view;
    Button
            btnLogin,
            btnRegister,
            btnSignout;
    TextView
            txtName,
            txtUsername,
            txtEmail,
            txtPob,
            txtDob,
            txtGender,
            txtAgama,
            txtPekerjaan,
            txtNasional;
    //===== Widget =====//

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserFunctions userFunctions = new UserFunctions(this.getContext());

        if (userFunctions.IsLogin()) {
            view = inflater.inflate(R.layout.fragment_account, container, false);
            txtName         = view.findViewById(R.id.txtName);
            txtUsername     = view.findViewById(R.id.txtUsername);
            txtEmail        = view.findViewById(R.id.txtEmail);
            txtPob          = view.findViewById(R.id.txtPob);
            txtDob          = view.findViewById(R.id.txtDob);
            txtGender       = view.findViewById(R.id.txtGender);
            txtAgama        = view.findViewById(R.id.txtAgama);
            txtPekerjaan    = view.findViewById(R.id.txtPekerjaan);
            txtNasional     = view.findViewById(R.id.txtNasional);

            btnSignout      = view.findViewById(R.id.sign_out_button);

            btnSignout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userFunctions.Logout();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                }
            });

            //===== Init data to view =====//
            getDetailUser(userFunctions.getUserId());
            //===== Init data to view =====//

        } else {
            view = inflater.inflate(R.layout.fragment_login, container, false);
            btnLogin = view.findViewById(R.id.btnLogin);
            btnRegister = view.findViewById(R.id.btnRegister);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
            });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), RegisterActivity.class);
                    i.putExtra("name", "Test");
                    startActivity(i);
                }
            });
        }



        return view;
    }

    private void getDetailUser(String id){
        dialogLoading = new ProgressDialog(this.getContext());
        dialogLoading.setCancelable(true);
        dialogLoading.setCanceledOnTouchOutside(true);
        dialogLoading.setMessage(getResources().getString(R.string.please_wait));
        dialogLoading.show();
//        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url  = Constants.Extra.api_url+"Users/get_detail_user";

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
                                JSONObject User 		    = 	data.getJSONObject("0");

                                txtName.setText(User.getString("nama"));
                                txtUsername.setText(User.getString("nama"));
                                txtEmail.setText(User.getString("email"));
                                txtPob.setText(User.getString("tempat_lahir"));
                                txtDob.setText(User.getString("tanggal_lahir"));
                                txtGender.setText(User.getString("jenis_kelamin").equalsIgnoreCase("m") ? "Laki-laki" : "Perempuan");
                                txtAgama.setText(User.getString("agama"));
                                txtPekerjaan.setText(User.getString("pekerjaan"));
                                txtNasional.setText(User.getString("kewarganegaraan"));

                            }
                            else
                            {
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error from apps with message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "ERROR:" + error.getMessage());
                        dialogLoading.dismiss();

                        Toast.makeText(getContext(), getResources().getString(R.string.failed_connect), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("token", Constants.Extra.token);
                params.put("id", id);
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