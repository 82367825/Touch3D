package com.zero.touch3d;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * 仿3DTouch效果
 * @author linzewu
 * @date 16-10-11
 */
public class TouchWidget extends FrameLayout {
    
    private static final int STATUS_FINGER_NOT_LEAVE = 0x01;
    private static final int STATUS_FINGER_LEAVE = 0x02;
    private int mStatusFinger = STATUS_FINGER_NOT_LEAVE;
    
    private ImageView mBackgroundView;
    private LayoutParams mBackgroundParams;
    private ImageView mIconView;
    private LayoutParams mIconParams;
    private MenuWidget mMenuWidget;
    private LayoutParams mMenuParams;
    private FrameLayout mContentLayout;
    private LayoutParams mContentParams;
    
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
        mBackgroundView = new ImageView(getContext());
        mBackgroundParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT);
        addView(mBackgroundView, mBackgroundParams);
        mContentLayout = new FrameLayout(getContext());
        mContentLayout.setBackgroundColor(0xBB000000);
        mContentParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mContentLayout, mContentParams);
        mIconView = new ImageView(getContext());
        mIconParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        mContentLayout.addView(mIconView, mIconParams);
        mMenuWidget = new MenuWidget(getContext());
        mMenuParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        mContentLayout.addView(mMenuWidget, mMenuParams);
    }

    /**
     * 公开方法: 设置图标
     * @param iconView
     */
    public void setIconView(View iconView) {
        if (mIconView == null) {
            return ;
        }
        mIconView.setImageBitmap(Touch3DUtils.takeViewShot(iconView));
        mIconView.setX(iconView.getX());
        mIconView.setY(iconView.getY());
        mMenuWidget.setX(iconView.getMeasuredWidth());
        mMenuWidget.setY(iconView.getMeasuredHeight());
    }

    /**
     * 公开方法: 设置背景
     * @param backgroundView
     */
    public void setBackgroundView(Bitmap backgroundView) {
        if (mBackgroundView == null) {
            return ;
        }
        mBackgroundView.setImageBitmap(backgroundView);
    }

    /**
     * 公开方法: 展示
     * @param activity
     */
    public void show(Activity activity) {
        attachActivity(activity);
        transferTouchEvent(activity);
    }
    
    public void hide() {
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInMenuWidget(event.getX(), event.getY())) {
                    mMenuWidget.onTouchMove(event.getX() - mMenuWidget.getX(),
                            event.getY() - mMenuWidget.getY());
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isInMenuWidget(event.getX(), event.getY())) {
                    mMenuWidget.onTouchUp(event.getX() - mMenuWidget.getX(), 
                            event.getY() - mMenuWidget.getY());
                }
                break;
        }
        return true;
    }

    public interface TouchListener {
        void onSelect(int command);
    }
    
    private TouchListener mTouchListener;
    public void setTouchListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
    }

    /**
     * 触摸点是否在菜单视图内
     * @param x
     * @param y
     * @return
     */
    private boolean isInMenuWidget(float x, float y) {
        return x > mMenuWidget.getX() && x < mMenuWidget.getX() + mMenuWidget.getWidth() 
                && y > mMenuWidget.getY() && y < mMenuWidget.getY() + mMenuWidget.getHeight();
    }
    
    /**
     * 挂载到某个Activity的最顶层 
     * @param activity
     */
    private void attachActivity(Activity activity) {
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
