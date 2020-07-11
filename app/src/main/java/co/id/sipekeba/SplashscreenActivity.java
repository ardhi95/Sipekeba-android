package co.id.sipekeba;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.sipekeba.Utils.BaseActivity;
import co.id.sipekeba.Utils.Preferences.AppPreferences;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import static co.id.sipekeba.Constants.splashInterval;

public class SplashscreenActivity extends BaseActivity {
    @BindView(R.id.versionCode)
    TextView versionCode;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versionCode.setText(String.format("Version v%s", version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(() -> {
            if (!AppPreferences.getInstance(getApplicationContext()).getPref(Constants.IS_LOGIN, false)){
                startActivity(new Intent(activity, MainActivity.class));
            }else{
                //todo to is login true
            }

            finish();
        }, splashInterval);
    }

}