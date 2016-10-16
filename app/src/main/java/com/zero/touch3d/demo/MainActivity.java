package com.zero.touch3d.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zero.touch3d.R;
import com.zero.touch3d.Touch3DUtils;
import com.zero.touch3d.TouchWidget;
import com.zero.touch3d.blurry.BlurryController;

/**
 * @author linzewu
 * @date 16-10-14
 */
public class MainActivity extends Activity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButton = (Button) findViewById(R.id.blurry);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBlurry();
            }
        });
        
    }
    
    private BlurryController mBlurryController;
    private Handler mHandler = new Handler();
    
    private void loadBlurry() {
        mBlurryController = new BlurryController(MainActivity.this);
        mBlurryController.setBlurryListener(new BlurryController.BlurryListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
                showBlurry(bitmap);
            }
        });
        mBlurryController.startBlurry(Touch3DUtils.takeScreenShot(MainActivity.this));
    }
    
    
    private void showBlurry(final Bitmap bitmap) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TouchWidget touchWidget = new TouchWidget(MainActivity.this);
                touchWidget.setBackgroundView(bitmap);
                touchWidget.setIconView(mButton);
                touchWidget.show(MainActivity.this);
                touchWidget.setTouchListener(new TouchWidget.TouchListener() {
                    @Override
                    public void onSelect(String command) {
                        Toast.makeText(MainActivity.this, command, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    
    
}
