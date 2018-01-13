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
        return sharedPreferences.getString(AppConstants.SHARED_PREF_AUTO_UPDATE_ID, "");
    }

}
