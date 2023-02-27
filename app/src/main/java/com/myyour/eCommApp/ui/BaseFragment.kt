package com.myyour.eCommApp.ui
import android.view.View
import androidx.fragment.app.Fragment


/**
 * Base fragment
 *
 * @constructor Create empty Base fragment
 */
abstract class BaseFragment : Fragment() {
    protected abstract var fragmentView: View

    /**
     * Swipe left call back
     *
     */
    abstract fun swipeLeftCallBack()

    /**
     * Swipe right call back
     *
     */
    abstract fun swipeRightCallBack()

    /**
     * Swipe observation :- Observe the swipe the gesture on fragments
     *
     */
    fun swipeObservation() {
        fragmentView.setOnTouchListener(object : OnSwipeTouchListener(fragmentView.context) {
            override fun onSwipeLeft() {
                swipeLeftCallBack()
            }

            override fun onSwipeRight() {
                swipeRightCallBack()
            }
        })
    }
}