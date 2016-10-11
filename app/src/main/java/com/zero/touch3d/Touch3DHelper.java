package com.zero.touch3d;

import android.app.Activity;
import android.view.View;

import java.util.List;

/**
 * @author linzewu
 * @date 16-10-11
 */
public class Touch3DHelper {
    
    
    public static void bindView(View bindView, final Activity activity, 
                                final TouchWidget.TouchListener touchListener) {
        bindView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TouchWidget touchWidget = new TouchWidget(activity);
                touchWidget.setTouchListener(touchListener);
                touchWidget.attachActivity(activity);
                return true;
            }
        });
    }
    
    
    
}
