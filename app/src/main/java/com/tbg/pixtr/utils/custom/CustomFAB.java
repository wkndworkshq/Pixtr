package com.tbg.pixtr.utils.custom;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.gordonwong.materialsheetfab.AnimatedFab;

/**
 * Created by kausthubhadhikari on 05/01/18.
 */

public class CustomFAB extends FloatingActionButton implements AnimatedFab {

    public CustomFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomFAB(Context context) {
        super(context);
    }

    public CustomFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void show() {
        show(0, 0);
    }

    @Override
    public void show(float v, float v1) {
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(INVISIBLE);
    }
}
