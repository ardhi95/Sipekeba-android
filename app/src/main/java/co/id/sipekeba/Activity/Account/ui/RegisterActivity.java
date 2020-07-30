package co.id.sipekeba.Activity.Account.ui;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnItemClick;
import co.id.sipekeba.Adapter.KeyValueArrayAdapter;
import co.id.sipekeba.AppController;
import co.id.sipekeba.Constants;
import co.id.sipekeba.MainActivity;
import co.id.sipekeba.R;
import co.id.sipekeba.Utilities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppController {

    public static final String TAG = RegisterActivity.class.getSimpleName();
    private Snackbar snackbar;

    @BindView(R.id.met_name)
    MaterialEditText inpName;
    @BindView(R.id.met_email)
    MaterialEditText inpEmail;
    @BindView(R.id.met_username)
    MaterialEditText inpUsername;
    @BindView(R.id.met_password)
    MaterialEditText inpPassword;
    @BindView(R.id.met_repeat_password)
    MaterialEditText inpRePassword;

    @BindView(R.id.met_dob)
    MaterialEditText inpDob;
    @BindView(R.id.met_pob)
    MaterialEditText inpPob;
    @BindView(R.id.met_pekerjaan)
    MaterialEditText inpPekerjaan;
    @BindView(R.id.met_alamat)
    MaterialEditText inpAlamat;

    @BindView(R.id.btn_next)
    MaterialButton btnNext;

    KeyValueArrayAdapter
            adapter,
            religionAdpt,
            nasAdpt;

    Spinner
            spinnerGender,
            spinnerReligion,
            spinnerNasionality;

    String
            email,
            name,
            username,
            password,
            repeat_password,
            gender,
            religion,
            nasionality,
            pob,
            dob,
            pekerjaan,
            alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        beforeFilter(RegisterActivity.this);

        activity = this;
        initViews();

        spinnerGender       = (Spinner) findViewById(R.id.spinner_gender);
        spinnerReligion     = (Spinner) findViewById(R.id.spinner_religion);
        spinnerNasionality  = (Spinner) findViewById(R.id.spinner_nasionality);
        // Spinner Drop down Gender
        List<String> genderCat    = new ArrayList<>();
        List<String> genderVal    = new ArrayList<>();
        genderVal.add("");
        genderCat.add("Pilih Jenis Kelamin");

        genderVal.add("m");
        genderCat.add("Laki-laki");

        genderVal.add("f");
        genderCat.add("Perempuan");

        adapter = new KeyValueArrayAdapter(RegisterActivity.this,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        adapter.setEntries(genderCat);
        adapter.setEntryValues(genderVal);
        spinnerGender.setAdapter(adapter);
        // Spinner Drop down Gender

        // Spinner Drop down Religion
        List<String> religionCat    = new ArrayList<>();
        List<String> religionVal    = new ArrayList<>();
        religionVal.add("");
        religionCat.add("Pilih Agama");

        religionVal.add("Islam");
        religionCat.add("Islam");

        religionVal.add("Kristen Protestan");
        religionCat.add("Kristen Protestan");

        religionVal.add("Kristen Katolik");
        religionCat.add("Kristen Katolik");

        religionVal.add("Hindu");
        religionCat.add("Hindu");

        religionVal.add("Buddha");
        religionCat.add("Buddha");

        religionVal.add("Konghucu");
        religionCat.add("Konghucu");

        religionAdpt = new KeyValueArrayAdapter(RegisterActivity.this,android.R.layout.simple_list_item_1);
        religionAdpt.setDropDownViewResource(android.R.layout.simple_list_item_1);
        religionAdpt.setEntries(religionCat);
        religionAdpt.setEntryValues(religionVal);
        spinnerReligion.setAdapter(religionAdpt);
        // Spinner Drop down Religion

        // Spinner Drop down Nasionality
        List<String> nasCat    = new ArrayList<>();
        List<String> nasVal    = new ArrayList<>();
        nasVal.add("");
        nasCat.add("Pilih Kewarganegaraan");

        nasVal.add("WNI");
        nasCat.add("Warga Negara Indonesia");

        nasVal.add("WNA");
        nasCat.add("Warga Negara Asing");

        nasAdpt = new KeyValueArrayAdapter(RegisterActivity.this,android.R.layout.simple_list_item_1);
        nasAdpt.setDropDownViewResource(android.R.layout.simple_list_item_1);
        nasAdpt.setEntries(nasCat);
        nasAdpt.setEntryValues(nasVal);
        spinnerNasionality.setAdapter(nasAdpt);
        // Spinner Drop down Religion

    }

    private void initViews() {
        initMaterialEdit(inpName);
        initMaterialEdit(inpUsername);
        initMaterialEdit(inpEmail);
        initMaterialEdit(inpPassword);
        initMaterialEdit(inpRePassword);
        initMaterialEdit(inpPob);
        initMaterialEdit(inpDob);
        initMaterialEdit(inpPekerjaan);
        initMaterialEdit(inpAlamat);
    }

    private void initMaterialEdit(MaterialEditText materialEditText){
        materialEditText.addTextChangedListener(new TextWatcher() {
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

    private void enableSubmitIfReady() {
        boolean isReady = inpName.getText().toString().length() > 1 &&
                inpUsername.getText().toString().length() > 1 &&
                inpEmail.getText().toString().length() > 1 &&
                inpPassword.getText().toString().length() > 1 &&
                inpRePassword.getText().toString().length() > 1 &&
                inpPob.getText().toString().length() > 1 &&
                inpDob.getText().toString().length() > 1 &&
                inpPekerjaan.getText().toString().length() > 1 &&
                inpAlamat.getText().toString().length() > 1 &&
                spinnerGender.getSelectedItem().toString().length() > 1 &&
                spinnerReligion.getSelectedItem().toString().length() > 1 &&
                spinnerNasionality.getSelectedItem().toString().length() > 1;
        btnNext.setEnabled(isReady);
    }

    @OnFocusChange(R.id.met_dob)
    public void ClickDob() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker<Long> picker = builder.build();
        addSnackBarListeners(picker);
        picker.show(getSupportFragmentManager(), picker.toString());
    }

    @OnClick(R.id.btn_next)
    public void ClickRegister() {

        email            = inpEmail.getText().toString();
        name             = inpName.getText().toString();
        username         = inpUsername.getText().toString();
        password         = inpPassword.getText().toString();
        repeat_password  = inpRePassword.getText().toString();

        // adapter.getEntryValue(spinnerStatus.getSelectedItemPosition()).toString();
        gender           = adapter.getEntryValue(spinnerGender.getSelectedItemPosition()).toString();
        religion         = religionAdpt.getEntryValue(spinnerReligion.getSelectedItemPosition()).toString();
        nasionality      = nasAdpt.getEntryValue(spinnerNasionality.getSelectedItemPosition()).toString();
        pob              = inpPob.getText().toString();
        dob              = convertDate(inpDob.getText().toString());
        pekerjaan        = inpPekerjaan.getText().toString();
        alamat           = inpAlamat.getText().toString();

        if (name.isEmpty() || username.isEmpty()) {
            inpName.setError("This fields is required");
        }else if(password.isEmpty()){
            inpPassword.setError("Password Empty");
        } else if (!Utilities.IsValidEmail(email)) {
            inpEmail.setError("Email not valid");
        } else if (!Utilities.isValidPassword(password)) {
            inpPassword.setError("Password not valid, length must be more than 5");
        } else if (!Utilities.isValidRepeatPassword(password, repeat_password)) {
            inpRePassword.setError("password tidak sama");
        } else {
            Log.d(TAG,gender);
            SendingDataRegister();
//            Utilities.ShowToast(getApplicationContext(), "Oke next");
        }


    }

    private void addSnackBarListeners(MaterialDatePicker<?> materialCalendarPicker) {
        materialCalendarPicker.addOnPositiveButtonClickListener(
                selection -> {

                    Utilities.ShowToast(getApplicationContext(), convertDate(materialCalendarPicker.getHeaderText()));
                    inpDob.setText(materialCalendarPicker.getHeaderText());
//                    snackbar.setText(materialCalendarPicker.getHeaderText());
//                    snackbar.show();
                });
        materialCalendarPicker.addOnNegativeButtonClickListener(
                dialog -> {
//                    snackbar.setText("Negative");
//                    snackbar.show();
                });
        materialCalendarPicker.addOnCancelListener(
                dialog -> {
//                    snackbar.setText("Cancel");
//                    snackbar.show();
                });
    }

    private String convertDate(String oldDateString){
        final String OLD_FORMAT = "MMM dd, yyyy";
        final String NEW_FORMAT = "dd/MM/yyyy";

        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);

        return newDateString;
    }


    void SendingDataRegister() {
        dialogLoading = new ProgressDialog(RegisterActivity.this);
        dialogLoading.setCancelable(true);
        dialogLoading.setCanceledOnTouchOutside(true);
        dialogLoading.setMessage(getResources().getString(R.string.please_wait));
        dialogLoading.show();
//        showDialog();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url  = Constants.Extra.api_url+"Users/register";

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


                                startActivity(new Intent(activity, LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                finish();

                            }
                            else
                            {
                                showAlertDialog(
                                        activity.getResources().getString(R.string.register_failed),
                                        message, false);
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            showAlertDialog(
                                    activity.getResources().getString(R.string.register_failed),
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
                params.put("password", repeat_password);
                params.put("email", email);
                params.put("nama", name);
                params.put("jenis_kelamin", gender);
                params.put("alamat", alamat);
                params.put("tempat_lahir", pob);
                params.put("tanggal_lahir", dob);
                params.put("agama", religion);
                params.put("pekerjaan", pekerjaan);
                params.put("kewarganegaraan", nasionality);
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