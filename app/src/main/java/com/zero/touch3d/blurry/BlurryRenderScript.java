package com.zero.touch3d.blurry;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * RenderScript虚化图片
 * 仅限于API17以上
 * @author linzewu
 * @date 16-10-13
 */
public class BlurryRenderScript implements IBlurry {
    
    private static final float DEFAULT_RADIUS = 20;
    private float mRadius;  //半径大小
    
    
    public BlurryRenderScript() {
        mRadius = DEFAULT_RADIUS;
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public Bitmap getBlurryBitmap(Context context, Bitmap bitmap) {
        float radius = 20;
        RenderScript rs = RenderScript.create(context);

        Allocation overlayAlloc = Allocation.createFromBitmap(rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(bitmap);
        rs.destroy();
        return bitmap;
    }
}
