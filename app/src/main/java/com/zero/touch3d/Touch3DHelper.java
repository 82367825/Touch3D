package com.zero.touch3d;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import com.zero.touch3d.blurry.BlurryController;


/**
 * @author linzewu
 * @date 16-10-11
 */
public class Touch3DHelper {
    
    private Activity mActivity;
    private BlurryController mBlurryController;
    private TouchWidget mTouchWidget;
    private Handler mMainThreadHandler = new Handler();

    public Touch3DHelper() {
    }
    
    public void init(Activity activity) {
        mActivity = activity;
        mBlurryController = new BlurryController(activity);
        mTouchWidget = new TouchWidget(activity);
    }
    
    public void destroy() {
        mActivity = null;
    }
    
    public void hide() {
        if (mTouchWidget != null && mTouchWidget.isShowing()) {
            mTouchWidget.hide(mActivity);
        }
    }
    
    public boolean isShowing() {
        return mTouchWidget != null && mTouchWidget.isShowing();
    }
    
    public void registerView(View view, final TouchWidget.TouchListener touchListener) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Touch3DUtils.initTouch3D(mActivity);
                initTouchWidget(v, touchListener);
                return false;
            }
        });
    }
    
    
    private void initTouchWidget(View view, TouchWidget.TouchListener touchListener) {
        if (mTouchWidget == null) {
            mTouchWidget = new TouchWidget(mActivity);
        }
        mTouchWidget.setIconView(view);
        mTouchWidget.setTouchListener(touchListener);
        loadBlurryBitmap();
    }
    
    private void showTouchWidget(final Bitmap bitmap) {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                mTouchWidget.setBackgroundView(bitmap);
                mTouchWidget.show(mActivity);
            }
        });
    }
    
    private void loadBlurryBitmap() {
        mBlurryController.setBlurryListener(new BlurryController.BlurryListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
                showTouchWidget(bitmap);
            }
        });
        mBlurryController.startBlurry(Touch3DUtils.takeScreenShot(mActivity));
    }
    
}
