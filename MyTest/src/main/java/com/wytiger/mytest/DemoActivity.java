package com.wytiger.mytest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wytiger.common.utils.image.ImageUtil;


public class DemoActivity extends Activity {
    TextView tv_text;
    ImageView iv_image1;
    ImageView iv_image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        tv_text = (TextView) findViewById(R.id.tv_text);
        iv_image1 = (ImageView)findViewById(R.id.iv_image1);
        iv_image2 = (ImageView)findViewById(R.id.iv_image2);

        Bitmap bitmap1 = ImageUtil.getScaleImage(this,R.drawable.icon_demo_landscape,200,100);
        iv_image1.setImageBitmap(bitmap1);
        Bitmap bitmap2 = ImageUtil.getScaleImage(this,R.drawable.icon_demo_portrait,100,200);
        iv_image2.setImageBitmap(bitmap2);



    }

}
