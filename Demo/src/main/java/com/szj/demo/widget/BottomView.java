package com.szj.demo.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.szj.demo.R;

/**
 *
 * Created by Administrator on 2016/7/28.
 */
public class BottomView extends View{

    public DisplayMetrics dm;

    private Resources res;
    private  int mColor = Color.BLUE;

    private Paint p;


    private  int mItemSize =2;

    private int width;
    private int height;


    private int myHeight=200;


    private Context mContext;


    private String[]textArray ={"第一个","第二个","第三个"};

    public BottomView(Context context) {
        super(context);
        mContext=context;
        initView();
    }


    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView();
    }

    public BottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView();
    }


    int indexLength;
    private void initView() {
        res=this.getResources();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        dm = new DisplayMetrics();
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(mColor);
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(mItemSize);

        width=getWidth();
        height=myHeight;

        indexLength=getWidth()/textArray.length;



        for (int i = 0; i <textArray.length ; i++) {
            drawText(canvas,i);

        }

    }
    float textSize=30;
   int bitHeight;
    int bitWidth;

    private void drawText(Canvas canvas,int i) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(textSize);
        mColor=mColor+40;
        p.setColor(mColor);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawRect(indexLength*i,0,indexLength*(i+1),height,p);

        p.setColor(0xffffffff);




        Bitmap mBitmap = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
    /*    mBitmap.setWidth(bitWidth);
        mBitmap.setHeight(bitHeight);
        */
        bitWidth=mBitmap.getWidth();
        bitHeight=mBitmap.getHeight();
        canvas.drawBitmap(mBitmap,indexLength*i+(indexLength-bitWidth)/2,(height-bitHeight-textSize)/2,p);
        mBitmap.recycle();


        canvas.drawText(textArray[i],indexLength*i+indexLength/2,(height+bitHeight+textSize)/2,p);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + 0.5f);
    }
}
