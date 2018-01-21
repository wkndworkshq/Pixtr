package com.tbg.pixtr.db.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.tbg.pixtr.utils.misc.AppConstants;

/**
 * Created by kausthubhadhikari on 13/01/18.
 */

public class SharedPreferencesUtil {

    private Context context;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(AppConstants.PIXTR_PREF_KEY, Context.MODE_PRIVATE);
    }

    /**
     * Clear the preferences data.
     */
    public void clearData() {
        sharedPreferences.edit().clear().commit();
    }


    /**
     * Clear the AutoUpdateData.
     */
    public void clearAutoUpdateData() {
        sharedPreferences.edit().remove(AppConstants.SHARED_PREF_AUTO_UPDATE_ID).commit();
    }


    /**
     * Save the collection Id for auto update.
     *
     * @param collectionId
     */
    public void saveAutoUpdateId(int collectionId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.SHARED_PREF_AUTO_UPDATE_ID, "" + collectionId).commit();
    }


    /**
     * Retrieve the Collection Id for auto update.
     *
     * @return
     */
    public String getAutoUpdateId() {
        return sharedPreferences.getString(AppConstants.SHARED_PREF_AUTO_UPDATE_ID, "empty");
    }


    /**
     * Save the load quality.
     *
     * @param qualityFlag
     */
    public void setLoadQuality(int qualityFlag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AppConstants.SHARED_PREF_LOAD, qualityFlag).commit();
    }


    /***
     *  Retrieve the load quality.
     *
     */
    public int getLoadQuality() {
        return sharedPreferences.getInt(AppConstants.SHARED_PREF_LOAD, 2);
    }


    /**
     * Save the Wallpaper quality.
     *
     * @param quality
     */
    public void setWallpaperQuality(int quality) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AppConstants.SHARED_PREF_WALLPAPER, quality).commit();
    }


    /**
     * Retrieve the Wallpaper quality.
     *
     * @return
     */
    public int getWallpaperQuality() {
        return sharedPreferences.getInt(AppConstants.SHARED_PREF_WALLPAPER, 1);
    }


    /**
     * Save the download quality.
     *
     * @param quality
     */
    public void setDownloadQuality(int quality) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AppConstants.SHARED_PREF_DOWNLOAD, quality).commit();
    }


    /**
     * Retrieve the Download quality.
     *
     * @return
     */
    public int getDownloadQuality() {
        return sharedPreferences.getInt(AppConstants.SHARED_PREF_DOWNLOAD, 0);
    }


    /**
     * Set Tutorial launch flag.
     */
    public void setTutorialLoaded() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AppConstants.SHARED_PREF_TUTORIAL, true).commit();
    }


    /**
     * Retrieve the tutorial flag.
     *
     * @return
     */
    public boolean getTutorialLoaded() {
        return sharedPreferences.getBoolean(AppConstants.SHARED_PREF_TUTORIAL, false);
    }
}
