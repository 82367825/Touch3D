package com.zero.touch3d;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 仿3DTouch效果
 * @author linzewu
 * @date 16-10-11
 */
public class TouchWidget extends FrameLayout {
    
    private boolean mIsShowing = false;
    
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
        mIconView.setY(iconView.getY() + Touch3DUtils.getStatusHeight());
        if (iconView.getX() > Touch3DUtils.getScreenHeight() / 2) {
            mMenuWidget.setX(iconView.getMeasuredWidth() - 50);
            mMenuWidget.setY(iconView.getMeasuredHeight());
        } else if (iconView.getX() < Touch3DUtils.getScreenHeight() / 2) {
            mMenuWidget.setX(iconView.getMeasuredWidth() - 50);
            mMenuWidget.setY(iconView.getMeasuredHeight());
        }
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
        animMenu();
        animIcon();
        mIsShowing = true;
    }

    /**
     * 公开方法：隐藏
     */
    public void hide(Activity activity) {
        disAttachActivity(activity);
    }

    /**
     * 公开方法：是否仍在展示
     */
    public boolean isShowing() {
        return mIsShowing;
    }
    
    private static final long DURATION_MENU_ANIM = 400;
    private static final long DURATION_ICON_ANIM = 300;

    private void animMenu() {
        if (mMenuWidget == null) {
            return ;
        }
        mMenuWidget.setPivotX(0f);
        mMenuWidget.setPivotY(0f);
        mMenuWidget.setScaleX(0f);
        mMenuWidget.setScaleY(0f);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.2f, 1f);
        valueAnimator.setDuration(DURATION_MENU_ANIM);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mMenuWidget.setScaleX(value);
                mMenuWidget.setScaleY(value);
            }
        });
        valueAnimator.setStartDelay(200);
        valueAnimator.start();
    }
    
    private void animIcon() {
        if (mIconView == null) {
            return ;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 1.2f);
        valueAnimator.setDuration(DURATION_ICON_ANIM);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mIconView.setScaleX(value);
                mIconView.setScaleY(value);
            }
        });
        valueAnimator.start();
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
                if (Touch3DUtils.isInView(mMenuWidget, event.getX(), event.getY())) {
                    mMenuWidget.onTouchMove(event.getX() - mMenuWidget.getX(),
                            event.getY() - mMenuWidget.getY());
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (Touch3DUtils.isInView(mMenuWidget, event.getX(), event.getY()) && 
                        mTouchListener != null) {
                    mTouchListener.onSelect(
                        mMenuWidget.onTouchUp(event.getX() - mMenuWidget.getX(), 
                            event.getY() - mMenuWidget.getY())
                    );
                }
                break;
        }
        return true;
    }

    public interface TouchListener {
        void onSelect(String command);
    }
    
    private TouchListener mTouchListener;
    public void setTouchListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
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
    }

    /**
     * 从某个Activity移除
     * @param activity
     */
    private void disAttachActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!isAttachedToWindow()) {
                return ;
            }
        }
        try {
            FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
            decor.removeView(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
