package com.zero.touch3d;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zero.touch3d.blurry.BlurryController;

/**
 * @author linzewu
 * @date 16-10-14
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.blurry);
        button.setOnClickListener(new View.OnClickListener() {
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
                touchWidget.setBackgroundBitmap(bitmap);
                FrameLayout decor = (FrameLayout)MainActivity.this.getWindow().getDecorView();
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
                decor.addView(touchWidget, lp);
            }
        });
    }
    
    
}
