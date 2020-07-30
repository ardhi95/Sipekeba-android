package co.id.sipekeba.Activity.Layanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.id.sipekeba.Activity.Account.ui.LoginActivity;
import co.id.sipekeba.AppController;
import co.id.sipekeba.Constants;
import co.id.sipekeba.Models.Layanan;
import co.id.sipekeba.R;
import co.id.sipekeba.ui.account.AccountFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayananActivity extends AppController {

    public static final String TAG = LayananActivity.class.getSimpleName();

    //======== Widget ========/
    RecyclerView
            mRecyclerView;
    //======== Widget ========/

    //======== RECYCLER VIEW =============/
    LayananAdapter adapter;
    Boolean is_running = true;
    private List<Layanan> layananList = new ArrayList<>();;
    //======== RECYCLER VIEW =============/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan);

        // Init widget
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        // Set List Recycleview
        adapter = new LayananAdapter(LayananActivity.this,layananList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LayananActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        // Init Data
        getDataLayanan();

    }


    private void getDataLayanan(){
        dialogLoading = new ProgressDialog(LayananActivity.this);
        dialogLoading.setCancelable(true);
        dialogLoading.setCanceledOnTouchOutside(true);
        dialogLoading.setMessage(getResources().getString(R.string.please_wait));
        dialogLoading.show();
//        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        String url  = Constants.Extra.api_url+"Layanan/getList";

        Log.d(TAG,"URL === "+url);

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

                            int respStatus                  =   json.getInt("code");
                            String message                  =   json.getString("message");

                            if(respStatus == 200)
                            {
                                Log.d(TAG, message);
                                JSONArray data      =   json.optJSONArray("data");
                                if (data != null) {
                                    if (data.length() > 0) {
                                        for (int i = 0; i < data.length(); i++) {
                                            JSONObject obj      =   data.getJSONObject(i);
                                            String id           =   obj.getString("id");
                                            String id_admin     =   obj.getString("id_admin");
                                            String jenisBarang  =   obj.getString("jenis_barang");
                                            String keterangan   =   obj.getString("keterangan");
                                            String created      =   obj.getString("created");
                                            String modified     =   obj.getString("modified");
                                            String status       =   obj.getString("status");

                                            Layanan layanan = new Layanan(
                                                    id,
                                                    id_admin,
                                                    jenisBarang,
                                                    keterangan,
                                                    created,
                                                    modified,
                                                    status
                                            );
                                            layananList.add(layanan);
                                        }
                                    } else {
                                        dialogLoading.dismiss();
                                        //HIDE RECYCLERVIEW
                                        mRecyclerView.setVisibility(View.GONE);

                                        //SHOW EMPTY LYT
//                                        errorLyt.setVisibility(View.VISIBLE);
//
//                                        //SHOW TEXT
//                                        errorTxt.setText(getResources().getString(R.string.data_is_empty));
                                    }
                                }

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error from apps with message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "ERROR:" + error.getMessage());
                        dialogLoading.dismiss();

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_connect), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("token", Constants.Extra.token);
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