package com.mysample.application.view.advertisementImageView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue

/**
 * Created by a01 on 2018/4/19.
 */
class DrawableHelperKt(val context: Context, val mView: AdvertisementImageView2Kt) {

    var targetDrawable: Drawable? = null
    lateinit var listener: ProcessListener

    fun createDrawable() = Thread(Runnable {

        val resId = mView.resId
        val resources = context.resources
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)

        // options.outWidth is dp, need do dp -> px
        val outWidthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, options.outWidth.toFloat(), resources.displayMetrics).toInt()
        val outHeightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, options.outHeight.toFloat(), resources.displayMetrics).toInt()
        val scale = 1.0f * mView.realWidth / outWidthPx
        val processedWidth = (scale * outWidthPx).toInt()
        val processedHeight = (scale * outHeightPx).toInt()

        options.inSampleSize = calculateInSampleSize(outWidthPx, outHeightPx, processedWidth, processedHeight)
        options.inJustDecodeBounds = false
        val sourceBitmap = BitmapFactory.decodeResource(resources, resId, options)
        val mMatrix = Matrix()
        mMatrix.postScale(scale, scale)
        val targetBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, mMatrix, true)
        targetDrawable = BitmapDrawable(resources, targetBitmap)
        sourceBitmap.recycle()
        listener.ProcessFinish(processedWidth, processedHeight)
    }).start()

    private fun calculateInSampleSize(sourceWidth: Int, sourceHeight: Int, reqWidth: Int, reqHeight: Int): Int {
        var inSampleSize = 1
        if (sourceWidth > reqWidth || sourceHeight > reqHeight) {
            val halfWidth = sourceWidth / 2
            val halfHeight = sourceHeight / 2
            while (halfWidth / inSampleSize > reqWidth && halfHeight / inSampleSize > reqHeight) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    interface ProcessListener {
        fun ProcessFinish(width: Int, height: Int)
    }
}