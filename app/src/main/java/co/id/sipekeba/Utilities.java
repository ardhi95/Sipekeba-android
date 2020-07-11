package co.id.sipekeba;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class Utilities {

    public static boolean DEBUG = true;

    public static ProgressDialog progressDialog;

    public static void showDialog(Context context, CharSequence message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void hideDialog(){
        try {
            progressDialog.dismiss();
        }catch (Exception ignored){}
    }


    static void ShowToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }

    public static void ShowToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public static boolean IsValidEmail(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 5;

    }

    public static boolean isValidRepeatPassword(String password, String repeat) {
        if (repeat.equals(password)){
            return true;
        }
        return false;
    }
    public static boolean isValidPhone(String password) {
        return password.length() > 10;

    }
}
