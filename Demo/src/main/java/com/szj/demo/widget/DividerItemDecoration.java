package com.szj.demo.widget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.szj.demo.R;

/**
 *
 * Created by Administrator on 2016/7/27.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private   Resources res;
    private  int mItemSize =2;
    private  int mColor =Color.BLUE;
    private  int indexNum=40;

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        res = parent.getResources();
        drawHorizontal(c, parent);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(mColor);
         /*设置填充*/
        p.setStyle(Paint.Style.FILL);

     /*   final int top = parent.getPaddingTop() ;
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() ;
        final int childSize = parent.getChildCount() ;

        final View child = parent.getChildAt( 0) ;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int left = child.getRight() + layoutParams.rightMargin ;
        final int right = left + mItemSize ;
         c.drawRect(left,top,right,bottom,p);
*/

        p.setStrokeWidth(mItemSize);
        final int left = parent.getPaddingLeft() ;
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() ;
        final int childSize = parent.getChildCount() ;

        int index =(right-left)/indexNum;

        for (int i = 0; i <childSize-1 ; i++) {
            final View child = parent.getChildAt( i) ;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin ;
            final int bottom = top + mItemSize ;
            for (int j = 0; j <indexNum ; j=j+2) {
                c.drawRect(left+index*j,top,left+index*(j+1),bottom,p);
            }

        }

    }

    public void seta(){

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,mItemSize);

    }

}
