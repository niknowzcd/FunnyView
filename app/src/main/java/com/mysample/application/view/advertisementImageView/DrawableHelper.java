package com.mysample.application.view.advertisementImageView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * Created by dly on 2018/3/12.
 */

public class DrawableHelper {

    private Context mContext;
    private AdvertisementImageView2 mView;
    private Drawable targetDrawable;
    private float scale;
    private ProcessListener listener;

    public DrawableHelper(Context mContext, AdvertisementImageView2 mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void setProcessListener(ProcessListener listener){
        this.listener = listener;
    }

    public void createDrawable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int resId = mView.getResourceId();
                Resources resources = mContext.getResources();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(resources, resId, options);

                // options.outWidth is dp, need do dp -> px
                int outWidthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, options.outWidth, resources.getDisplayMetrics());
                int outHeightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, options.outHeight, resources.getDisplayMetrics());
                scale = 1.0f * mView.getRealWidth() / outWidthPx;
                int processedWidth = (int) (scale * outWidthPx);
                int processedHeight = (int) (scale * outHeightPx);

                options.inSampleSize = calculateInSampleSize(outWidthPx, outHeightPx, processedWidth, processedHeight);
                options.inJustDecodeBounds = false;
                Bitmap sourceBitmap = BitmapFactory.decodeResource(resources, resId, options);
                Matrix mMatrix = new Matrix();
                mMatrix.postScale(scale, scale);
                Bitmap targetBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), mMatrix, true);
                targetDrawable = new BitmapDrawable(resources, targetBitmap);
                sourceBitmap.recycle();
                listener.ProcessFinish(processedWidth, processedHeight);
            }
        }).start();
    }

    public Drawable getTargetDrawable() {
        return targetDrawable;
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

    public interface ProcessListener {
        void ProcessFinish(int width, int height);
    }
}
