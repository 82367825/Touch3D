package com.zero.touch3d.blurry;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * RenderScript虚化图片
 * 效果最佳  仅限于API17以上
 * @author linzewu
 * @date 16-10-13
 */
public class BlurryRenderScript implements IBlurry {
    
    private static final int DEFAULT_RADIUS = 20;
    private int mRadius;  //半径大小
    
    
    public BlurryRenderScript() {
        mRadius = DEFAULT_RADIUS;
    }
    
    @Override
    public Bitmap getBlurryBitmap(Context context, Bitmap bitmap) {
        Bitmap targetBitmap = scaleBitmap(transform(bitmap));
        return getRenderScriptBitmap(context, mRadius, targetBitmap);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap getRenderScriptBitmap(Context context, int radius, Bitmap bitmapOriginal) {
        RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, bitmapOriginal);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmapOriginal);
        rs.destroy();
        return  bitmapOriginal;
    }


    private Bitmap scaleBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.1f, 0.1f);
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth() , bitmap.getHeight(), matrix, true);
    }

    private Bitmap restoreBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(10f, 10f);
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    
    
    private Bitmap transform(Bitmap bitmap) {
        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888) {
            return bitmap;
        } else {
            return bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
    } 
}
