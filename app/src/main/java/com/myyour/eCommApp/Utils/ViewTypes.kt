package com.myyour.eCommApp.Utils

import androidx.annotation.IntDef

class ViewTypes{
    companion object {
        @IntDef(LINEARVIEW, GRIDVIEW)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ViewType

        const val LINEARVIEW = 0
        const val GRIDVIEW = 1
    }

    @ViewType
    private var viewType: Int = 0

    public fun setViewType(@ViewType type: Int) {
        this.viewType = type
    }
    public fun getViewType():Int {
        return viewType
    }
}