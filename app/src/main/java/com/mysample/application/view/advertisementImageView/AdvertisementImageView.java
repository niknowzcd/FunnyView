package com.mysample.application.view.advertisementImageView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 *
 * Created by dly on 2018/3/6.
 */

public class AdvertisementImageView extends View {

    private Context mContext;

    private int[] location = new int[2];
    private int[] recyclerLocation = new int[2];

    private int realWidth;
    private int realHeight;

    private int resId;
    private float drawableDisY; //图片Y轴的偏移量
    private float scale;        //缩放比例
    private int mMimDisPlayTop;

    private int processedHeight;
    private Drawable targetDrawable;

    public AdvertisementImageView(Context context) {
        super(context);
        init(context, null);
    }

    public AdvertisementImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        realWidth = measureHandle(getSuggestedMinimumWidth(), widthMeasureSpec);
        realHeight = measureHandle(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(realWidth, realHeight);

        calculateBitmap();
    }

    private void calculateBitmap() {
        Resources resources = mContext.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);

        // options.outWidth is dp, need do dp -> px
        int outWidthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, options.outWidth, resources.getDisplayMetrics());
        int outHeightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, options.outHeight, resources.getDisplayMetrics());
        scale = 1.0f * realWidth / outWidthPx;
        int processedWidth = (int) (scale * outWidthPx);
        processedHeight = (int) (scale * outHeightPx);

        options.inSampleSize = calculateInSampleSize(outWidthPx, outHeightPx, processedWidth, processedHeight);
        options.inJustDecodeBounds = false;
        Bitmap sourceBitmap = BitmapFactory.decodeResource(resources, resId, options);
        Matrix mMatrix = new Matrix();
        mMatrix.postScale(scale, scale);
        Bitmap targetBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), mMatrix, true);
        targetDrawable = new BitmapDrawable(resources, targetBitmap);
        sourceBitmap.recycle();

        mMimDisPlayTop = -targetBitmap.getHeight() + realHeight;

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getLocationInWindow(location);
                drawableDisY = (recyclerLocation[1] - location[1]) * scale;
                boundTop();
                invalidate();
            }
        });
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
        if (resId == 0) return;

        canvas.save();
        canvas.translate(0, drawableDisY);
        targetDrawable.setBounds(0, 0, realWidth, processedHeight);
        targetDrawable.draw(canvas);
        canvas.restore();
    }

    public void setImageResource(@DrawableRes int resId) {
        this.resId = resId;
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
        recyclerView.getLocationInWindow(recyclerLocation);
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


    private int calculateInSampleSize(int sourceWidth, int sourceHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (sourceWidth > reqWidth || sourceHeight > reqHeight) {
            int halfWidth = sourceWidth / 2;
            int halfHeight = sourceHeight / 2;
            while ((halfWidth / inSampleSize > reqWidth)
                    && (halfHeight / inSampleSize > reqHeight)) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
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
        return location[1] - recyclerLocation[1];
    }
}
