package com.zero.touch3d;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * 仿3DTouch效果
 * @author linzewu
 * @date 16-10-11
 */
public class TouchWidget extends FrameLayout {
    
    public TouchWidget(Context context) {
        super(context);
        init();
    }

    public TouchWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        
    }
    
    public interface TouchListener {
        void onSelect(String command);
        void onCancel();
    }
    
    private TouchListener mTouchListener;
    public void setTouchListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
    }
    
    /**
     * 挂载到某个Activity的最顶层 
     * @param activity
     */
    public void attachActivity(Activity activity) {
        ViewParent parent = getParent();
        if(parent != null && parent instanceof ViewGroup) {
            ViewGroup parentView = (ViewGroup) parent;
            parentView.removeView(this);
        }
        FrameLayout decor = (FrameLayout)activity.getWindow().getDecorView();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        decor.addView(this, lp);
        transferTouchEvent(activity);
    }

    /**
     * 转移触摸事件
     */
    private void transferTouchEvent(final Activity activity) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), 
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0));
                activity.getWindow().getDecorView().dispatchTouchEvent(MotionEvent.obtain(SystemClock
                        .uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0));
            }
        }, 200);
    }
    
}
