package com.mysample.application.view.advertisementImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 改进版
 * Created by dly on 2018/3/12.
 */

public class AdvertisementImageView2 extends View {

    private int[] location = new int[2];
    private int[] rvLocation = new int[2];

    private int realWidth;
    private int realHeight;

    private int resId;
    private float drawableDisY;      //图片Y轴的偏移量
    private int mMimDisPlayTop;
    private float scale;             //缩放后的图片高度与recycleView的比例
    private DrawableHelper helper;
    private int rescaleHeight;

    public AdvertisementImageView2(Context context) {
        super(context);
        init(context);
    }

    public AdvertisementImageView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        helper = new DrawableHelper(context, this);
        helper.setProcessListener(new DrawableHelper.ProcessListener() {
            @Override
            public void ProcessFinish(int width, int height) {
                rescaleHeight = height;
                scale = 1.0f * height / rvHeight;
                mMimDisPlayTop = -height + realHeight;
                getLocationInWindow(location);
                drawableDisY = (rvLocation[1] - location[1]) * scale;
                boundTop();
                post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        realWidth = measureHandle(getSuggestedMinimumWidth(), widthMeasureSpec);
        realHeight = measureHandle(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(realWidth, realHeight);

        helper.createDrawable();
    }

    public int getRealWidth() {
        return realWidth;
    }

    private int measureHandle(int defaultSize, int measureSpec) {
        int result;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        if (specMode == View.MeasureSpec.EXACTLY || specMode == View.MeasureSpec.AT_MOST) {
            result = specSize;
        } else {
            result = defaultSize;
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = helper.getTargetDrawable();
        if (drawable != null) {
            canvas.save();
            canvas.translate(0, drawableDisY);
            drawable.setBounds(0, 0, realWidth, rescaleHeight);
            drawable.draw(canvas);
            canvas.restore();
        }
    }

    public void setImageResource(@DrawableRes int resId) {
        this.resId = resId;
    }

    public int getResourceId() {
        return resId;
    }

    private RecyclerView recyclerView;
    private RecyclerView.OnScrollListener rvScrollListener;
    private int rvHeight;

    public void bindRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null || recyclerView.equals(this.recyclerView)) {   //避免重复绑定，造成资源浪费
            return;
        }
        unbindRecyclerView();
        this.recyclerView = recyclerView;
        rvHeight = recyclerView.getLayoutManager().getHeight();
        recyclerView.getLocationInWindow(rvLocation);
        recyclerView.addOnScrollListener(rvScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getTopDistance() > 0 && getTopDistance() + getHeight() < rvHeight) {
                    drawableDisY += dy * scale;
                    boundTop();
                    invalidate();
                }
            }
        });
    }

    private void unbindRecyclerView() {
        if (rvScrollListener != null) {
            recyclerView.removeOnScrollListener(rvScrollListener);
        }
        recyclerView = null;
    }

    private void boundTop() {
        if (drawableDisY > 0) {
            drawableDisY = 0;
        }
        if (drawableDisY < mMimDisPlayTop) {
            drawableDisY = mMimDisPlayTop;
        }
    }

    private int getTopDistance() {
        getLocationInWindow(location);
        return location[1] - rvLocation[1];
    }
}
