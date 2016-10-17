package com.zero.touch3d.blurry;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

/**
 * @author linzewu
 * @date 16-10-14
 */
public class BlurryController {
    
    private Context mContext;
    private IBlurry mIBlurry;
    
    public BlurryController(Context context) {
        mContext = context;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mIBlurry = BlurryFactory.createRenderScript();
        } else {
            mIBlurry = BlurryFactory.createFastBlur();
        }
    }
    
    public void startBlurry(Bitmap bitmap) {
        new Thread(new BlurryRunnable(bitmap)).start();
    }
    
    public void stopBlurry() {
        
    }
    
    private BlurryListener mBlurryListener;
    public void setBlurryListener(BlurryListener blurryListener) {
        this.mBlurryListener = blurryListener;
    }
    
    public interface BlurryListener {
        void onFinish(Bitmap bitmap);
    }
    
    public class BlurryRunnable implements Runnable {
        
        private Bitmap mBitmap;
        
        public BlurryRunnable(Bitmap bitmap) {
            mBitmap = bitmap;
        }
        
        @Override
        public void run() {
            Bitmap bitmap = mIBlurry.getBlurryBitmap(mContext, mBitmap);
            if (mBlurryListener != null) {
                mBlurryListener.onFinish(bitmap); 
            }
        }
    }
}
