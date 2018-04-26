package com.mysample.application.view.numberRainView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mysample.application.R;

/**
 * Created by dly on 2018/4/26.
 */

public class NumberRainView extends LinearLayout {

    private int normalColor = Color.GREEN;
    private int highLightColor = Color.WHITE;
    private Context context;
    private float textSize;

    public NumberRainView(Context context) {
        this(context, null);
    }

    public NumberRainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        textSize = 15 * context.getResources().getDisplayMetrics().density;
        if (attrs != null) {
            TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.NumberRainView);
            normalColor = typedArray.getColor(R.styleable.NumberRainView_normalColor, Color.GREEN);
            highLightColor = typedArray.getColor(R.styleable.NumberRainView_highLightColor, Color.WHITE);
            textSize = typedArray.getDimension(R.styleable.NumberRainView_textSize, textSize);
            typedArray.recycle();
        }

        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        addRainItems();
    }

    private void addRainItems() {
        int count = (int) (getMeasuredWidth() / textSize);
        for (int i = 0; i < count; i++) {
            NumberRainItemView itemView = new NumberRainItemView(context);
            itemView.setNormalColor(normalColor);
            itemView.setHighLightColor(highLightColor);
            itemView.setTextSize(textSize);
            itemView.setStartDelay((long) (Math.random() * 1000));
            LayoutParams params = new LayoutParams((int) textSize + 10, getMeasuredHeight());
            addView(itemView, params);
        }
    }

}
