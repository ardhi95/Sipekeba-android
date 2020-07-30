package co.id.sipekeba.Activity.Layanan;

import androidx.appcompat.app.AppCompatActivity;
import co.id.sipekeba.AppController;
import co.id.sipekeba.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailLayananActivity extends AppController {

    //======== PARAMS =============/
    Context context;
    Intent ThisIntent;
    Bundle bundle;
    String
            layananId       =   "";
    //======== PARAMS =============/

    //========= Widget =========//
    TextView
        txtJenis;
    //========= Widget =========//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layanan);

        //============== PARAM ====================/
//        ThisIntent  =   activity.getIntent();
//        bundle      =   ThisIntent.getExtras();
//
//        if(bundle == null)
//            bundle  =   new Bundle();
//
//        if(ThisIntent.hasExtra("id"))
//            layananId    =   bundle.getString("id");
        //============== PARAM ====================/

//        txtJenis    = (TextView) findViewById(R.id.txtJenis);
//        Log.d("Test", "Iniii : "+layananId);
//        txtJenis.setText(layananId);
    }
}