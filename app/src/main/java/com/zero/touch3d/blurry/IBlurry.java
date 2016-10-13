package com.zero.touch3d.blurry;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * @author linzewu
 * @date 16-10-13
 */
public interface IBlurry {
    
    Bitmap getBlurryBitmap(Context context, Bitmap bitmap);
    
}
