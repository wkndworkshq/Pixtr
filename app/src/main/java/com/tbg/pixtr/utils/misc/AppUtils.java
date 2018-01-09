package com.tbg.pixtr.utils.misc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Created by kausthubhadhikari on 25/12/17.
 */

public class AppUtils {

    /**
     * Convert PX to DP.
     *
     * @param size
     * @return
     */
    public int pxToDp(int size) {
        return (int) (size * Resources.getSystem().getDisplayMetrics().density);
    }


    /**
     * Get URI from a downloaded in Glide Image.
     *
     * @param context
     * @param bitmap
     * @return
     */
    public Uri getImageURI(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, UUID.randomUUID().toString() + ".png", "Pixtr Images");
        return Uri.parse(path);
    }


}
