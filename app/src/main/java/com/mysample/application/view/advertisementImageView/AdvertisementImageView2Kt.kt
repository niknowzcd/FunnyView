package com.mysample.application.view.advertisementImageView

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

/**
 * Created by a01 on 2018/4/19.
 */
class AdvertisementImageView2Kt : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    var drawableHelper: DrawableHelperKt = DrawableHelperKt(context, this)
    val location = IntArray(2)
    val rvLocation = IntArray(2)

    var realWidth: Int = 0
    var realHeight: Int = 0

    var resId: Int = 0
    var rescaleHeight: Int = 0
    var scale: Float = 0.toFloat()
    private var drawableDisY: Float = 0.toFloat()      //图片Y轴的偏移量
    private var mMimDisPlayTop: Int = 0


    init {
        drawableHelper.listener = object : DrawableHelperKt.ProcessListener {
            override fun ProcessFinish(width: Int, height: Int) {
                rescaleHeight = height
                scale = 1.0f * height / rvHeight
                mMimDisPlayTop = -height + realHeight
                getLocationInWindow(location)
                drawableDisY = (rvLocation[1] - location[1]) * scale
                boundTop()
                post { invalidate() }
            }

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        realWidth = measureHandle(suggestedMinimumWidth, widthMeasureSpec)
        realHeight = measureHandle(suggestedMinimumHeight, heightMeasureSpec)
        setMeasuredDimension(realWidth, realHeight)

        drawableHelper.createDrawable()
    }

    private fun measureHandle(defaultSize: Int, measureSpec: Int): Int {
        val result: Int
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        result = if (specMode == View.MeasureSpec.EXACTLY || specMode == View.MeasureSpec.AT_MOST) {
            specSize
        } else {
            defaultSize
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val drawable = drawableHelper.targetDrawable
        canvas.save()
        canvas.translate(0f, drawableDisY)
        drawable?.setBounds(0, 0, realWidth, rescaleHeight)
        drawable?.draw(canvas)
        canvas.restore()
    }

    fun setImageResourceId(resourceId: Int) {
        resId = resourceId
    }


    var recyclerView: RecyclerView? = null
    var rvScrollListener: RecyclerView.OnScrollListener? = null
    private var rvHeight: Int = 0

    fun bindRecyclerView(recyclerView: RecyclerView) {
        if (recyclerView == this@AdvertisementImageView2Kt.recyclerView) return
        unbindRecyclerView()
        this@AdvertisementImageView2Kt.recyclerView = recyclerView
        rvHeight = recyclerView.layoutManager.height
        recyclerView.getLocationInWindow(rvLocation)
        rvScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (getTopDistance() > 0 && getTopDistance() + height < rvHeight) {
                    drawableDisY += dy * scale
                    boundTop()
                    invalidate()
                }
            }
        }
        recyclerView.addOnScrollListener(rvScrollListener)
    }

    fun unbindRecyclerView() {
        recyclerView?.removeOnScrollListener(rvScrollListener)
        recyclerView = null
    }

    private fun boundTop() {
        if (drawableDisY > 0) {
            drawableDisY = 0f
        }
        if (drawableDisY < mMimDisPlayTop) {
            drawableDisY = mMimDisPlayTop.toFloat()
        }
    }

    private fun getTopDistance(): Int {
        getLocationInWindow(location)
        return location[1] - rvLocation[1]
    }
}