package com.zero.touch3d;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
    }

    private void initMenu() {
        mMenuCommand = MenuCommand.MENU_COMMAND;
        for (String menuCommand : mMenuCommand) {
            Button button = new Button(getContext());
            button.setText(menuCommand);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mLinearLayout.addView(button, layoutParams);
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
                    && view instanceof Button) {
                return (String) ((Button)view).getText();
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
                    && view instanceof Button) {
                return (String) ((Button)view).getText();
            }
        }
        return null;
    }
}
