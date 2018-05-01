package com.mysample.application.view.numberRainView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.mysample.application.R

/**
 * Created by dly on 2018/4/26.
 */
class NumberRainViewKt(context: Context, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet) {

    constructor(context: Context) : this(context, null)

    private var normalColor = Color.GREEN
    private var highLightColor = Color.RED
    private var textSize: Float = 15 * resources.displayMetrics.density

    init {
        attributeSet?.let {
            val typedArray = this.context.obtainStyledAttributes(attributeSet, R.styleable.NumberRainView)
            normalColor = typedArray.getColor(R.styleable.NumberRainView_normalColor, Color.GREEN)
            highLightColor = typedArray.getColor(R.styleable.NumberRainView_highLightColor, Color.BLUE)
            textSize = typedArray.getDimension(R.styleable.NumberRainView_textSize, textSize)
            typedArray.recycle()
        }

        orientation = LinearLayout.HORIZONTAL
        setBackgroundColor(Color.BLACK)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        addRainItems()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun addRainItems() {
        val count = (measuredWidth / textSize).toInt()

        for (i in 0 until count) {
            val itemView = NumberRainItemViewKt(context)
            itemView.normalColor = normalColor
            itemView.highLightColor = highLightColor
            itemView.textSize = textSize
            itemView.startDelay = (Math.random() * 1000).toLong()
            val params = LinearLayout.LayoutParams(textSize.toInt() + 10, measuredHeight)
            addView(itemView, params)
        }
    }
}