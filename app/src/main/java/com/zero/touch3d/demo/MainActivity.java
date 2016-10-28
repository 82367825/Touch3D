package com.zero.touch3d.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.zero.touch3d.R;
import com.zero.touch3d.Touch3DHelper;
import com.zero.touch3d.TouchWidget;


/**
 * @author linzewu
 * @date 16-10-14
 */
public class MainActivity extends Activity {

    private Button mButton;
    private Touch3DHelper mTouch3DHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTouch3DHelper = new Touch3DHelper();
        mTouch3DHelper.init(this);
        
        mButton = (Button) findViewById(R.id.blurry);
        mTouch3DHelper.registerView(mButton, new TouchWidget.TouchListener() {
            @Override
            public void onSelect(String command) {
                Toast.makeText(MainActivity.this, command, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public void onBackPressed() {
        if (mTouch3DHelper.isShowing()) {
            mTouch3DHelper.hide();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTouch3DHelper.destroy();
    }
}
