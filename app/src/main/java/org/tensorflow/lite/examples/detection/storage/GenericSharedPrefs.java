package org.tensorflow.lite.examples.detection.storage;

import android.content.Context;
import android.content.SharedPreferences;


import org.tensorflow.lite.examples.detection.App;

import static android.content.Context.MODE_PRIVATE;

public class GenericSharedPrefs {
    private static final String MY_APP = "tesis.user_data";
    private static final Boolean RESPONSE_NEGATIVE_BOOLEAN = false;
    private static final Float RESPONSE_NEGATIVE_FLOAT = 0.0f;
    private static final int RESPONSE_NEGATIVE_INTEGER = -1;
    private static final String RESPONSE_NEGATIVE_STRING = "";

    static boolean getBoolean(String key) {
        return getBoolean(key, RESPONSE_NEGATIVE_BOOLEAN);
    }

    static boolean getBoolean(String key, boolean defaultValue) {
        if (getInstance() == null) {
            return false;
        }
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    public static Context getInstance() {
        return App.getInstance();
    }

    static void putBoolean(String key, boolean value) {
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static int getInt(String key) {
        if (getInstance() == null) {
            return RESPONSE_NEGATIVE_INTEGER;
        }
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        return settings.getInt(key, RESPONSE_NEGATIVE_INTEGER);
    }

    public static int getInt(String key, int defaultValue) {
        if (getInstance() == null) {
            return defaultValue;
        }
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    static void putInt(String key, int value) {
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    static long getLong(String key) {
        if (getInstance() == null) {
            return 0;
        }
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        return settings.getLong(key, 0);
    }

    static void putLong(String key, long value) {
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        if (getInstance() == null) {
            return RESPONSE_NEGATIVE_STRING;
        }
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        return settings.getString(key, RESPONSE_NEGATIVE_STRING);
    }

    public static Float getFloat(String key) {
        if (getInstance() == null) {
            return RESPONSE_NEGATIVE_FLOAT;
        }
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        return settings.getFloat(key, RESPONSE_NEGATIVE_FLOAT);
    }

    public static String getString(String key, String defaultValue) {
        if (getInstance() == null) {
            return defaultValue;
        }
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    static void putString(String key, String value) {
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    static void putFloat(String key, Float value) {
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void clearUserPreferences() {
        SharedPreferences settings = getInstance().getSharedPreferences(MY_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }
}