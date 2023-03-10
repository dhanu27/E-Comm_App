package com.myyour.eCommApp.ui

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


/**
 * On swipe touch listener
 *
 * It listen the swipe events and finalizes which one is right ,top, left, bottom,
 * and provide callbacks for them So class which use them can override them
 *
 * @param ctx
 */
open class OnSwipeTouchListener(ctx: Context?) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    companion object {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 70
    }

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }


    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }

    }

    /**
     * On swipe right
     *
     *//* onSwipeRight ,onSwipeLeft, onSwipeTop , onSwipeBottom are touch events callbacks which implementation can override them*/
    open fun onSwipeRight() {}

    /**
     * On swipe left
     *
     */
    open fun onSwipeLeft() {}

    /**
     * On swipe top
     *
     */
    open fun onSwipeTop() {}

    /**
     * On swipe bottom
     *
     */
    open fun onSwipeBottom() {}
}