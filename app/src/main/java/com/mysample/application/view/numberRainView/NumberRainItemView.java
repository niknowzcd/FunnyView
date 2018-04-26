package com.mysample.application.view.numberRainView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by dly on 2018/4/26.
 */

public class NumberRainItemView extends View {

    private int normalColor = Color.GREEN;
    private int highLightColor = Color.RED;
    private float nowHeight;
    private float textSize;
    private long startDelay;
    private Paint mPaint;
    private int highLightIndex;

    public NumberRainItemView(Context context) {
        super(context);
        textSize = 15 * getResources().getDisplayMetrics().density;
        mPaint = new Paint();
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public void setHighLightColor(int highLightColor) {
        this.highLightColor = highLightColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextSize(textSize);
        mPaint.setColor(normalColor);
        if (isShowAllNumbers()) {
            drawAllNumbers(canvas);
        } else {
            drawPartNumbers(canvas);
        }
    }

    private void drawAllNumbers(Canvas canvas) {
        int count = (int) (getHeight() / textSize);
        drawSingleNumber(canvas, count);
    }

    private void drawPartNumbers(Canvas canvas) {
        int count = (int) (nowHeight / textSize);
        nowHeight += textSize;
        drawSingleNumber(canvas, count);
    }

    private void drawSingleNumber(Canvas canvas, int count) {
        if (count == 0) {
            postInvalidateDelayed(startDelay);
        } else {
            float numberOffset = 0f;
            for (int i = 0; i < count; i++) {
                String randomNumber = String.valueOf((int) (Math.random() * 2));
                mPaint.setColor(highLightIndex == i ? highLightColor : normalColor);
                canvas.drawText(randomNumber, 0, numberOffset, mPaint);
                numberOffset += textSize;
            }

            if (isShowAllNumbers()) {
                highLightIndex++;
                highLightIndex = highLightIndex % count;
            } else {
                highLightIndex++;
            }
            postInvalidateDelayed(100);
        }
    }

    private boolean isShowAllNumbers() {
        return nowHeight >= getHeight();
    }
}
