package co.id.sipekeba;


import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import androidx.multidex.MultiDexApplication;


public class AppApplication extends MultiDexApplication {

    public static final String TAG = Application.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppApplication mInstance;

    //======== LOGIN PROFILE =======/
    public static UserFunctions UserFunction;
    static String loginId          =   "";
    static String loginName        =   "";
    static String loginAvatar      =   "";
    static String loginEmail       =   "";
    static boolean isLogin         =   false;
    //======== LOGIN PROFILE =======/

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppApplication getInstance() {

        //=================== GET LOGIN PROFILE ===================/
        UserFunction                        =   new UserFunctions(mInstance.getApplicationContext());
        isLogin                             =   UserFunction.IsLogin();
        if(isLogin){
            HashMap<String, String> user 	=	UserFunction.getUserDetails();
            loginId							=	user.get(UserFunction.USER_ID);
            loginName                       =   user.get(UserFunction.FULLNAME);
            loginEmail                      =   user.get(UserFunction.EMAIL);
            loginAvatar                     =   user.get(UserFunction.AVATAR);
        }
        //=================== GET LOGIN PROFILE ===================/
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public String convertDate(String date, String startFormat, String endFormat) {
        DateFormat inputFormat = new SimpleDateFormat(startFormat);
        DateFormat outputFormat = new SimpleDateFormat(endFormat);
        Date parsed;
        String outputText = "";

        try {
            parsed = inputFormat.parse(date);
            outputText = outputFormat.format(parsed);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return outputText;
    }
}
