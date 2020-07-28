package co.id.sipekeba.Activity.Account.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.sipekeba.AppController;
import co.id.sipekeba.R;
import co.id.sipekeba.Utilities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class RegisterActivity extends AppController {

    public static final String TAG = LoginActivity.class.getSimpleName();
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

    @BindView(R.id.btn_next)
    MaterialButton btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        beforeFilter(RegisterActivity.this);

        activity = this;
        initViews();
    }

    private void initViews() {

//        ArrayList<String> listArr = new ArrayList<>("Material", "Design", "Components", "Android");

//        val items = listOf("Material", "Design", "Components", "Android")
//        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
//        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        initMaterialEdit(inpName);
        initMaterialEdit(inpUsername);
        initMaterialEdit(inpEmail);
        initMaterialEdit(inpPassword);
        initMaterialEdit(inpRePassword);
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
                inpRePassword.getText().toString().length() > 1;
        btnNext.setEnabled(isReady);
    }

    @OnClick(R.id.met_dob)
    public void ClickDob() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker<Long> picker = builder.build();
        addSnackBarListeners(picker);
        picker.show(getSupportFragmentManager(), picker.toString());
    }

    @OnClick(R.id.btn_next)
    public void ClickRegister() {

        String email            = inpEmail.getText().toString();
        String name             = inpName.getText().toString();
        String username         = inpUsername.getText().toString();
        String password         = inpPassword.getText().toString();
        String repeat_password  = inpRePassword.getText().toString();


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
//            initRegister(email, password);
            Utilities.ShowToast(getApplicationContext(), "Oke next");
        }

    }

    private void addSnackBarListeners(MaterialDatePicker<?> materialCalendarPicker) {
        materialCalendarPicker.addOnPositiveButtonClickListener(
                selection -> {
                    Utilities.ShowToast(getApplicationContext(), materialCalendarPicker.getHeaderText());
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
}