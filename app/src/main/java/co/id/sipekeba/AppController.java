package co.id.sipekeba;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class AppController extends AppCompatActivity {

    private static final String TAG = "AppController";
    public AppCompatActivity activity;
    public Context context;

    public ProgressDialog dialogLoading;
    public UserFunctions UserFunction;

    //======== LOGIN PROFILE =======/
    String loginId          =   "";
    String loginName        =   "";
    String loginAvatar      =   "";
    String loginEmail       =   "";
    boolean isLogin         =   false;
    //======== LOGIN PROFILE =======/

    public void beforeFilter(AppCompatActivity CurrentActivity) {
        this.activity = CurrentActivity;
        this.context = activity.getApplicationContext();
        UserFunction = new UserFunctions(this.context);

        //=================== GET LOGIN PROFILE ===================/
        isLogin                             =   UserFunction.IsLogin();
        if(isLogin){
            HashMap<String, String> user 	=	UserFunction.getUserDetails();
            loginId							=	user.get(UserFunction.USER_ID);
            loginName                       =   user.get(UserFunction.FULLNAME);
            loginAvatar                     =   user.get(UserFunction.AVATAR);
            loginEmail                      =   user.get(UserFunction.EMAIL);
        }
        //=================== GET LOGIN PROFILE ===================/

    }


    public void showAlertDialog(String title, String message,
                                Boolean status) {

        final Dialog dialog 	=	new Dialog(activity, R.style.AppTheme_dialog_style);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_alert_message);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView headerTxt		=	(TextView) dialog.findViewById(R.id.headerTxt);
        TextView contentTxt		=	(TextView) dialog.findViewById(R.id.contentTxt);
        TextView okTxt			=	(TextView) dialog.findViewById(R.id.okTxt);

        ImageView alertIconimg	=	(ImageView) dialog.findViewById(R.id.alertIconimg);
        LinearLayout closeLyt	=	(LinearLayout) dialog.findViewById(R.id.closeLyt);

        alertIconimg.setImageResource(status ? R.drawable.ic_oke : R.drawable.ic_alert);
        headerTxt.setText(title.toUpperCase());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            contentTxt.setText(Html.fromHtml(message, Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH).toString());
        }
        else {
            contentTxt.setText(Html.fromHtml(message).toString());
        }

//        headerTxt.setTypeface(AppApplication.getInstance().Light);
        headerTxt.setBackgroundResource(status ? R.drawable.bg_dialog_header_success : R.drawable.bg_dialog_header_failed);
        closeLyt.setBackgroundResource(status ? R.color.selector_close_alert_dialog_success : R.color.selector_close_alert_dialog_failed);

        closeLyt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        okTxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
