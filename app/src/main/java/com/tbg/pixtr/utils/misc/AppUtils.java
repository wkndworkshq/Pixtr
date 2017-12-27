package com.tbg.pixtr.utils.misc;

import android.content.res.Resources;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class AppUtils {

    public int pxToDp(int size) {
        return (int) (size * Resources.getSystem().getDisplayMetrics().density);
    }

}
