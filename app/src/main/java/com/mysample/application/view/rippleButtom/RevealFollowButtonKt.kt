package com.mysample.application.view.rippleButton

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Created by dly on 2018/4/4.
 */
class RevealFollowButtonKt(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var mIsFollowed: Boolean = false
    private var mFollowTv: TextView? = null
    private var mUnFollowTv: TextView? = null
    private var mRevealRadius = 0f
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var isFirstInit = true

    private val mPath = Path()

    init {
        mUnFollowTv = TextView(context)
        mUnFollowTv!!.text = "未关注"
        mUnFollowTv!!.gravity = 17
        mUnFollowTv!!.setSingleLine()
        mUnFollowTv!!.setBackgroundColor(Color.RED)
        mUnFollowTv!!.setTextColor(Color.WHITE)
        addView(this.mUnFollowTv)
        mFollowTv = TextView(context)
        mFollowTv!!.text = "关注"
        mFollowTv!!.gravity = 17
        mFollowTv!!.setSingleLine()
        mFollowTv!!.setBackgroundColor(Color.WHITE)
        mFollowTv!!.setTextColor(Color.BLACK)
        addView(this.mFollowTv)

        mFollowTv!!.setPadding(40, 40, 40, 40)
        mUnFollowTv!!.setPadding(40, 40, 40, 40)
        setFollowed(false, false)
    }

    fun setFollowed(isFollowed: Boolean, needAnimal: Boolean) {
        mIsFollowed = isFollowed
        if (mIsFollowed) {
            mFollowTv!!.bringToFront()
        } else {
            mUnFollowTv!!.bringToFront()
        }
        if (needAnimal) {
            val animator = ObjectAnimator.ofFloat(mFollowTv, "", 0.0f, Math.hypot(measuredWidth.toDouble(), measuredHeight.toDouble()).toFloat())
            animator.duration = 2000
            animator.addUpdateListener { animation ->
                mRevealRadius = animation.animatedValue as Float
                invalidate()
            }
            animator.start()
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> return true
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }
            MotionEvent.ACTION_UP -> {
                isFirstInit = false
                mCenterX = event.x
                mCenterY = event.y
                mRevealRadius = 0f
                setFollowed(!mIsFollowed, true)
                return true
            }
        }
        return false
    }

    private fun drawBackground(targetView: View): Boolean {
        if (isFirstInit) {
            return true
        }
        if (mIsFollowed && targetView == mUnFollowTv) {
            return true
        } else if (!mIsFollowed && targetView == mFollowTv) {
            return true
        }
        return false
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        if (drawBackground(child)) {
            return super.drawChild(canvas, child, drawingTime)
        }
        val i = canvas.save()
        mPath.reset()
        mPath.addCircle(mCenterX, mCenterY, mRevealRadius, Path.Direction.CW)
        canvas.clipPath(mPath)
        val bol2 = super.drawChild(canvas, child, drawingTime)
        canvas.restoreToCount(i)
        return bol2
    }

}