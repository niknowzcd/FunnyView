package com.mysample.application.view.numberRainView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

/**
 * Created by dly on 2018/4/26.
 */
class NumberRainItemViewKt(context: Context) : View(context) {

    var normalColor = Color.GREEN
    var highLightColor = Color.RED
    var nowHeight: Float = 0.toFloat()
    var textSize: Float = 15 * resources.displayMetrics.density
    var startDelay: Long = 0
    var mPaint: Paint = Paint()
    var highLightIndex: Int = 0

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint.textSize = textSize
        mPaint.color = normalColor
        canvas?.let {
            if (isShowAllNumbers()) {
                drawAllNumbers(it)
            } else {
                drawPartNumbers(it)
            }
        }
    }

    private fun drawAllNumbers(canvas: Canvas) {
        val count = (height / textSize).toInt()
        drawSingleNumber(canvas, count)
    }

    private fun drawPartNumbers(canvas: Canvas) {
        val count = (nowHeight / textSize).toInt()
        nowHeight += textSize
        drawSingleNumber(canvas, count)
    }

    private fun drawSingleNumber(canvas: Canvas, count: Int) = if (count == 0) {
        postInvalidateDelayed(startDelay)
    } else {
        var numberOffset = 0f
        for (i in 0 until count) {
            val randomNumber = (Math.random() * 2).toInt().toString()
            mPaint.color = if (highLightIndex == i) highLightColor else normalColor
            canvas.drawText(randomNumber, 0f, numberOffset, mPaint)
            numberOffset += textSize
        }

        if (isShowAllNumbers()) {
            highLightIndex++
            highLightIndex %= count
        } else {
            highLightIndex++
        }
        postInvalidateDelayed(100)
    }

    private fun isShowAllNumbers(): Boolean = nowHeight >= height

}