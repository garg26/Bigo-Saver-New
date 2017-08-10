package simplifii.framework.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


/**
 * Created by nitin on 24/08/15.
 */
public class Preferences {

    private static final String PREF_NAME = "Pioneer_Prefs";
    public static final String LOGIN_KEY = "isUserLoggedIn";
    private static final String ADDRESS_STATE = "address_state";
    private static final String ADDRESS_CITY = "address_city";
    private static final String ADDRESS_LOCALITY = "address_locality";
    private static final String ADDRESS_LAT = "address_latitude";
    private static final String ADDRESS_LONG = "address_longitude";
    private static final String ADDRESS_LANDMARK = "address_landmark";
    public static final String MASTER_API_JSON = "masterApiJson";
    public static final String PAST_CURRENT_API_JSON = "past_current_api";
    public static final String CATEGORY = "category";
    public static final String KEY_LNG = AppConstants.PARAMS.LNG;
    public static final String KEY_LAT = AppConstants.PARAMS.LAT;

    private static SharedPreferences xebiaSharedPrefs;
    private static SharedPreferences.Editor editor;
    private static Preferences sharedPreferenceUtil;

    public static void initSharedPreferences(Context context) {
        sharedPreferenceUtil = new Preferences();
        sharedPreferenceUtil.xebiaSharedPrefs = context.getSharedPreferences(
                PREF_NAME, Activity.MODE_PRIVATE);
        sharedPreferenceUtil.editor = sharedPreferenceUtil.xebiaSharedPrefs
                .edit();
    }

    public static Preferences getInstance() {

        return sharedPreferenceUtil;
    }

    private Preferences() {
        // TODO Auto-generated constructor stub
    }

    public static synchronized boolean saveData(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public static synchronized boolean saveData(String key, boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static synchronized boolean saveData(String key, long value) {
        editor.putLong(key, value);
        return editor.commit();
    }

    public static synchronized boolean saveData(String key, float value) {
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static synchronized boolean saveData(String key, int value) {
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean isUserLoggerIn() {
        return getData(LOGIN_KEY, false);
    }

	/*
     * public synchronized boolean saveData(String key, Set<String> value) {
	 * //editor.putStringSet(key, value); return editor.commit(); }
	 */

    public static synchronized boolean removeData(String key) {
        editor.remove(key);
        return editor.commit();
    }

    public static synchronized Boolean getData(String key, boolean defaultValue) {
        return xebiaSharedPrefs.getBoolean(key, defaultValue);
    }

    public static synchronized String getData(String key, String defaultValue) {
        return xebiaSharedPrefs.getString(key, defaultValue);
    }

    public static synchronized float getData(String key, float defaultValue) {

        return xebiaSharedPrefs.getFloat(key, defaultValue);
    }


    public static synchronized int getData(String key, int defaultValue) {
        return xebiaSharedPrefs.getInt(key, defaultValue);
    }

    public static synchronized long getData(String key, long defaultValue) {
        return xebiaSharedPrefs.getLong(key, defaultValue);
    }

    /*
     * public synchronized Set<String> getData(String key, Set<String> defValue)
     * {
     *
     * // return naukriSharedPreferences.getStringSet(key, defValue); return
     * null; }
     */
    public static synchronized void deleteAllData() {

        sharedPreferenceUtil = null;
        editor.clear();
        editor.commit();
    }

    public static double getLat() {
        return Double.parseDouble(Preferences.getData(AppConstants.PARAMS.LAT, "0.0"));
    }

    public static double getLng() {
        return Double.parseDouble(Preferences.getData(AppConstants.PARAMS.LAT, "0.0"));
    }


    public static String getAppLink() {
        String link = getData(AppConstants.PREF_KEYS.APP_LINK, null);
        if (TextUtils.isEmpty(link)) {
            link = AppConstants.APP_LINK;
        }
        return link;
    }


}
