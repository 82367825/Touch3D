package com.zero.touch3d;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author linzewu
 * @date 16/10/16
 */
public class MenuWidget extends LinearLayout implements IMenu {
    
    private String[] mMenuCommand;
    
    private LinearLayout mLinearLayout;
    private LayoutParams mLayoutParams;
    
    public MenuWidget(Context context) {
        super(context);
        init();
        initMenu();
    }

    public MenuWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initMenu();
    }

    public MenuWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initMenu();
    }
    
    private void init() {
        mLinearLayout = new LinearLayout(getContext());
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        addView(mLinearLayout, mLayoutParams);
        mLinearLayout.setOrientation(VERTICAL);
        mLinearLayout.setBackgroundColor(0xFFFFFFFF);
    }

    private void initMenu() {
        mMenuCommand = MenuCommand.MENU_COMMAND;
        for (String menuCommand : mMenuCommand) {
            TextView textView = new TextView(getContext());
            textView.setText(menuCommand);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 50;
            layoutParams.rightMargin = 50;
            layoutParams.topMargin = 50;
            layoutParams.bottomMargin = 50;
            mLinearLayout.addView(textView, layoutParams);
        }
    }

    @Override
    public String onTouchMove(float x, float y) {
        if (mLinearLayout == null || mLinearLayout.getChildCount() == 0) {
            return null;
        }
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            View view = mLinearLayout.getChildAt(i);
            if (Touch3DUtils.isInView(view, x, y)
                    && view instanceof TextView) {
                return (String) ((TextView)view).getText();
            }
        }
        return null;
    }

    @Override
    public String onTouchUp(float x, float y) {
        if (mLinearLayout == null || mLinearLayout.getChildCount() == 0) {
            return null;
        }
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            View view = mLinearLayout.getChildAt(i);
            if (Touch3DUtils.isInView(view, x, y)
                    && view instanceof TextView) {
                return (String) ((TextView)view).getText();
            }
        }
        return null;
    }
}
