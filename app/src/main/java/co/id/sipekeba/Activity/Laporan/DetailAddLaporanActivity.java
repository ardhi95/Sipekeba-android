package co.id.sipekeba.Activity.Laporan;

import androidx.appcompat.app.AppCompatActivity;
import co.id.sipekeba.Activity.Layanan.DetailLayananActivity;
import co.id.sipekeba.R;

import android.content.Intent;
import android.os.Bundle;

public class DetailAddLaporanActivity extends AppCompatActivity {
    public static final String TAG = DetailAddLaporanActivity.class.getSimpleName();
    //======== PARAMS =============/
    Intent ThisIntent;
    Bundle bundle;
    String
            layananId       =   "",
            jenisBarang     =   "";
    //======== PARAMS =============/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_add_laporan);
    }
}