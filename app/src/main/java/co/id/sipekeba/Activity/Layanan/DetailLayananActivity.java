package co.id.sipekeba.Activity.Layanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.id.sipekeba.Adapter.LayananAdapter;
import co.id.sipekeba.AppController;
import co.id.sipekeba.Constants;
import co.id.sipekeba.Models.Layanan;
import co.id.sipekeba.Models.Syarat;
import co.id.sipekeba.R;
import co.id.sipekeba.ui.account.AccountFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

public class DetailLayananActivity extends AppController {

    public static final String TAG = DetailLayananActivity.class.getSimpleName();
    //======== PARAMS =============/
    Intent ThisIntent;
    Bundle bundle;
    String
            layananId       =   "";
    //======== PARAMS =============/

    //========= Widget =========//
    TextView
            txtJenis,
            txtKeterangan;
    ListView
            mListView;
    //========= Widget =========//

    //======== RECYCLER VIEW =============/
    Boolean is_running = true;
    private List<Syarat> syaratList = new ArrayList<>();;
    //======== RECYCLER VIEW =============/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layanan);

        //============== PARAM ====================/
        ThisIntent  =   getIntent();
        bundle      =   ThisIntent.getExtras();

        if(bundle == null)
            bundle  =   new Bundle();

        if(ThisIntent.hasExtra("id"))
            layananId    =   bundle.getString("id");
        //============== PARAM ====================/

        txtJenis        = findViewById(R.id.txtJenis);
        txtKeterangan   = findViewById(R.id.txtKeterangan);
        mListView       = findViewById(R.id.mRecyclerView);

        getDetailLayanan();
    }

    private void getDetailLayanan(){
        dialogLoading = new ProgressDialog(DetailLayananActivity.this);
        dialogLoading.setCancelable(true);
        dialogLoading.setCanceledOnTouchOutside(true);
        dialogLoading.setMessage(getResources().getString(R.string.please_wait));
        dialogLoading.show();
//        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        String url  = Constants.Extra.api_url+"Layanan/getDetail";

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

                                JSONObject data 		    = 	json.getJSONObject("data");
                                JSONObject objLayanan 		= 	data.getJSONObject("Layanan");
                                JSONArray objSyarat         =   data.optJSONArray("Syarat");

                                String jenisBarang          = objLayanan.getJSONObject("0").getString("jenis_barang");
                                String keterangan           = objLayanan.getJSONObject("0").getString("keterangan");

                                txtJenis.setText(jenisBarang);
                                txtKeterangan.setText(keterangan);

                                List<String> syaratItems    = new ArrayList<>();
                                for (int i = 0; i < objSyarat.length(); i++) {
                                    JSONObject obj      =   objSyarat.getJSONObject(i);
                                    syaratItems.add(obj.getString("syarat"));
                                }
                                ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, syaratItems);
                                mListView.setAdapter(itemsAdapter);
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
                params.put("id", layananId);
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