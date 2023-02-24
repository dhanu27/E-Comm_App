package com.myyour.eCommApp.ui
import android.view.View
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {
    protected abstract var fragmentView: View
    abstract fun swipeLeftCallBack()
    abstract fun swipeRightCallBack()

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