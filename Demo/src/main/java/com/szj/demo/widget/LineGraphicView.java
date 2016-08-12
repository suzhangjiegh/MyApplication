package com.szj.demo.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.szj.demo.R;

import java.util.ArrayList;


public class LineGraphicView extends View {
    String TAG = "LineGraphicView";
    /**
     * 公共部分
     */
    public static final int CIRCLE_SIZE = 14;

    public static enum Linestyle {
        Line, Curve
    }

    public Context mContext;
    public Paint mPaint;
    public Resources res;
    public DisplayMetrics dm;

    /**
     * data
     */
    public Linestyle mStyle = Linestyle.Curve;

    public int canvasHeight;
    public int canvasWidth;
    public int bheight = 0;
    public int blwidh;
    public boolean isMeasure = true;
    /**
     * Y轴最大值
     */
    public int maxValue;
    /**
     * Y轴间距值
     */
    public int averageValue;
    public int marginTop = 80;
    public int marginBottom = 80;

    /**
     * 曲线上总点数
     */
    public Point[] mPoints;
    /**
     * 纵坐标值
     */
    public ArrayList<Double> yRawData;
    /**
     * 横坐标值
     */
    public ArrayList<String> xRawDatas;
    public ArrayList<Integer> xList = new ArrayList<Integer>();// 记录每个x的值
    public int spacingHeight;

    public LineGraphicView(Context context) {
        this(context, null);
        initView();
    }

    public LineGraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }


    private void initView() {
        this.res = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (isMeasure) {
            this.canvasHeight = getHeight();
            this.canvasWidth = getWidth();
            if (bheight == 0)
                //已经修改 TODO
                bheight = (int) (canvasHeight - marginBottom - marginTop);
            blwidh = dip2px(30);
            isMeasure = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {


        mPaint.setColor(res.getColor(R.color.black));
        mPaint.setStrokeWidth(dip2px(1f));


        drawAllXLine(canvas);
        // 画直线（纵向）
        drawAllYLine(canvas);


        // 点的操作设置
        mPoints = getPoints();

        mPaint.setColor(res.getColor(R.color.red));
        mPaint.setStrokeWidth(dip2px(2.0f));
        mPaint.setStyle(Style.STROKE);
        if (mStyle == Linestyle.Curve) {
            drawScrollLine(canvas);
        } else {
            drawLine(canvas);
        }

        mPaint.setStyle(Style.FILL);
        for (int i = 0; i < mPoints.length; i++) {
            canvas.drawCircle(mPoints[i].x, mPoints[i].y, CIRCLE_SIZE / 2, mPaint);
        }
    }

    /**
     * 画所有横向表格，包括X轴
     */
    private void drawAllXLine(Canvas canvas) {

        for (int i = 0; i < spacingHeight + 1; i++) {

            try {
                canvas.drawLine(blwidh, bheight - (bheight / spacingHeight) * i + marginTop, (canvasWidth -(canvasWidth - blwidh) / yRawData.size()-1),
                        bheight - (bheight / spacingHeight) * i + marginTop, mPaint);// Y坐标
            } catch (Exception e) {
                Log.i(TAG, "canvas spacingHeight =" + spacingHeight);
            }


            drawTextRight(String.valueOf(averageValue * i), blwidh / 2, bheight - (bheight / spacingHeight) * i + marginTop,
                    canvas);
        }

        drawBigText("近七天收入(元)", blwidh / 2, marginTop / 2, canvas);
    }

    /**
     * 画所有纵向表格，包括Y轴
     */
    private void drawAllYLine(Canvas canvas) {

        for (int i = 0; i < yRawData.size(); i++) {
            xList.add(blwidh + (canvasWidth - blwidh) / yRawData.size() * i);
            canvas.drawLine(blwidh + (canvasWidth - blwidh) / yRawData.size() * i, marginTop, blwidh
                    + (canvasWidth - blwidh) / yRawData.size() * i, bheight + marginTop, mPaint);
            drawText(xRawDatas.get(i), blwidh + (canvasWidth - blwidh) / yRawData.size() * i, bheight + dip2px(12) + marginTop,
                    canvas);// X坐标
        }
        //drawText("min", (canvasWidth - blwidh), bheight + dip2px(12) + marginTop, canvas);
    }

    private Point[] getPoints() {

        Point[] points = new Point[yRawData.size()];
        for (int i = 0; i < yRawData.size(); i++) {
            int ph = bheight - (int) (bheight * (yRawData.get(i) / maxValue));

            points[i] = new Point(xList.get(i), ph + marginTop);
        }
        return points;
    }

    private void drawScrollLine(Canvas canvas) {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;

            Path path = new Path();
            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);

            canvas.drawPath(path, mPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
        }
    }

    private void drawTextRight(String text, int x, int y, Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(12));
        p.setColor(res.getColor(R.color.black));
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, x, y, p);
    }

    private void drawText(String text, int x, int y, Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(12));
        p.setColor(res.getColor(R.color.black));
        p.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(text, x, y, p);
    }
    private void drawBigText(String text, int x, int y, Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(14));
      //  p.setTypeface(Typeface.DEFAULT_BOLD);
        p.setUnderlineText(true);
        p.setColor(res.getColor(R.color.black));
        p.setTextAlign(Paint.Align.LEFT);

        canvas.drawText(text, x, y, p);
    }
    //TODO
    /*
    修改
    spacingHeight =4将其4等分

    */

    public void setData(ArrayList<Double> y, ArrayList<String> x, int m, int a) {

        int max=0;
        for (int i = 0; i <y.size() ; i++) {
            if (y.get(i)>max){
                max= y.get(i).intValue();
            }
        }
        for (int i = 0; i <4 ; i++) {
            if (max%4==0){
                break;
            }
            max++;
        }


        maxValue = max;
        spacingHeight =4;
        averageValue = max/spacingHeight;

        mPoints = new Point[y.size()];
        xRawDatas = x;
        yRawData = y;



        Log.i(TAG, "s =" + spacingHeight + "maxValue =" + maxValue + "averageValue =" + averageValue);
        invalidateView();

    }

    public void setTotalvalue(int maxValue) {
        this.maxValue = maxValue;
        invalidateView();
    }

    public void setPjvalue(int averageValue) {
        this.averageValue = averageValue;
        invalidateView();
    }

    public void setMargint(int marginTop) {
        this.marginTop = marginTop;
        invalidateView();
    }

    public void setMarginb(int marginBottom) {
        this.marginBottom = marginBottom;
        invalidateView();
    }

    public void setMstyle(Linestyle mStyle) {
        this.mStyle = mStyle;
        invalidateView();
    }

    public void setBheight(int bheight) {
        this.bheight = bheight;
        invalidateView();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + 0.5f);
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
