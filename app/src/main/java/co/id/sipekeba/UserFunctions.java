package co.id.sipekeba;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;


public class UserFunctions {
    private static final String PREF_NAME = "SIPEKEBA";
    private static final String TAG = "UserFunctions";

    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public final String IS_LOGIN = "IS_LOGIN";


    //============= PROFILE ===========/
    public final String USER_ID = "USER_ID";
    public final String FULLNAME = "FIRST_NAME";
    public final String EMAIL = "EMAIL";
    public final String AVATAR = "AVATAR";
    //============= PROFILE ===========/


    public UserFunctions(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean IsLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setLoginStatus(boolean status
    ) {
        editor.putBoolean(IS_LOGIN, status);
        editor.commit();
    }

    public void Logout() {
        editor.clear();
        editor.commit();
    }

    public void createLoginSession(
            String user_id,
            String fullname,
            String email,
            String avatar
    ) {
        editor.putString(USER_ID, user_id);
        editor.putString(FULLNAME, fullname);
        editor.putString(EMAIL, email);
        editor.putString(AVATAR, avatar);
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(USER_ID, pref.getString(USER_ID, ""));
        user.put(FULLNAME, pref.getString(FULLNAME, ""));
        user.put(EMAIL, pref.getString(EMAIL, ""));
        user.put(AVATAR, pref.getString(AVATAR, ""));
        return user;
    }

    public String getUserId()
    {
        return pref.getString(USER_ID, "");
    }

    public String getFullname()
    {
        return pref.getString(FULLNAME, "");
    }

    public String getEmail()
    {
        return pref.getString(EMAIL, "");
    }

    public String getAvatar()
    {
        return pref.getString(AVATAR, "");
    }

}