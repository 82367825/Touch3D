package com.zero.touch3d;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

/**
 * @author linzewu
 * @date 16-10-14
 */
public class Touch3DUtils {

    /**
     * 截取某个View的图面
     * @param view
     * @return
     */
    public static Bitmap takeViewShot(View view) {
        Bitmap bitmap = null;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 截取屏幕
     * @param activity
     * @return bitmap
     */
    public static Bitmap takeScreenShot(Activity activity) {
        Bitmap bitmap = null;
        View view = activity.getWindow().getDecorView();
        // 设置是否可以进行绘图缓存  
        view.setDrawingCacheEnabled(true);
        // 如果绘图缓存无法，强制构建绘图缓存  
        view.buildDrawingCache();
        // 返回这个缓存视图   
        bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 判断某个点是否在某个View内
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isInView(View view, float x, float y) {
        return x > view.getX() && x < view.getX() + view.getWidth() 
                && y > view.getY() && y < view.getY() + view.getHeight();
    }

}
