package co.id.sipekeba;

public class Constants {

    static final String IS_LOGIN = "IS_LOGIN";
    static final int splashInterval = 3200;
    public static final String SESSION_TOKEN = "TOKEN";

    public static class Extra {
//        public static final String api_url                        = "http://sipekebaapi.myindotek.com/";
//        public static final String api_url                        = "http://192.168.1.10/sipekebaApi/";
        public static final String api_url                        = "http://192.168.43.211/sipekebaApi/";

        public static final String token                            = "f0d4c3356f99d32dec2fa6bf46270dd9";
        public static final int TIME_OUT_REQUEST                    = 5000;
        public static final String RECEIVER_REFRESH                 = "RECEIVER_REFRESH";
        public static final String RECEIVER_REFRESH_COUNTER         = "RECEIVER_REFRESH_COUNTER";

        //==================== PUSH NOTIF ================//
        // global topic to receive app wide push notifications
        public static final String TOPIC_GLOBAL = "global";

        // broadcast receiver intent filters
        public static final String REGISTRATION_COMPLETE = "registrationComplete";
        public static final String PUSH_NOTIFICATION = "pushNotification";

        // id to handle the notification in the notification tray
        public static final int NOTIFICATION_ID = 100;
        public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

        public static final String SHARED_PREF = "SIPEKEBA";
        //==================== PUSH NOTIF ================//
    }
}
