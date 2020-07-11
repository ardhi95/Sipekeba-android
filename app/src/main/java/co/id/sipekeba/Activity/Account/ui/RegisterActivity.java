package co.id.sipekeba.Activity.Account.ui;

import androidx.appcompat.app.AppCompatActivity;
import co.id.sipekeba.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnNext = findViewById(R.id.btnNext);

    }
}