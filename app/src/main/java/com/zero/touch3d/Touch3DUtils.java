package com.zero.touch3d;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author linzewu
 * @date 16-10-14
 */
public class Touch3DUtils {
    private static float sStatusHeight;
    private static float sScreenWidth;
    private static float sScreenHeight;

    /**
     * 初始化相关参数
     * @param activity
     */
    public static void initTouch3D(Activity activity) {
        Rect rectangle= new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        sStatusHeight = rectangle.top;
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
    }
    
    public static float getStatusHeight() {
        return sStatusHeight;
    } 
    
    public static float getScreenWidth() {
        return sScreenWidth;
    }
    
    public static float getScreenHeight() {
        return sScreenHeight;
    }
    
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

    /**
     * 震动
     * @param context
     */
    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }

}
