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

//        // 获取状态栏高度  
//        Rect frame = new Rect();
//        // 测量屏幕宽和高  
//        view.getWindowVisibleDisplayFrame(frame);
//        int statusHeight = frame.top;
//        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
//        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
//        // 根据坐标点和需要的宽和高创建bitmap  
//        bitmap = Bitmap.createBitmap(bitmap, 0, statusHeight, width, height - statusHeight);
        return bitmap;
    }

}
