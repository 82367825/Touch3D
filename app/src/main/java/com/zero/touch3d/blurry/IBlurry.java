package com.zero.touch3d.blurry;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * @author linzewu
 * @date 16-10-13
 */
public interface IBlurry {
    /**
     * 根据Bitmap进行虚化
     * @param context
     * @param bitmap
     * @return
     */
    Bitmap getBlurryBitmap(Context context, Bitmap bitmap);
    
}
