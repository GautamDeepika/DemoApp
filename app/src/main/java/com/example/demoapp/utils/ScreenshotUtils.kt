package com.example.demoapp.utils

import android.app.Activity

import android.graphics.Bitmap
import android.view.View

import android.view.View.MeasureSpec

 object ScreenshotUtils {
    private var mInstance: ScreenshotUtils? = null

    private fun ScreenshotUtil() {

    }
    fun getInstance(): ScreenshotUtils? {
        if (mInstance == null) {
            synchronized(ScreenshotUtils::class.java) {
                if (mInstance == null) {
                    mInstance = ScreenshotUtils
                }
            }
        }
        return mInstance
    }

    /**
     * Measures and takes a screenshot of the provided [View].
     *
     * @param view The view of which the screenshot is taken
     * @return A [Bitmap] for the taken screenshot.
     */
    fun takeScreenshotForView(view: View): Bitmap? {
        view.measure(
            MeasureSpec.makeMeasureSpec(view.width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(view.height, MeasureSpec.EXACTLY)
        )
        view.layout(
            view.x.toInt(),
            view.y.toInt(),
            view.x.toInt() + view.measuredWidth,
            view.y.toInt() + view.measuredHeight
        )
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache(true)
        val bitmap: Bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return bitmap
    }

    fun takeScreenshotForScreen(activity: Activity): Bitmap? {
        return takeScreenshotForView(activity.window.decorView.rootView)
    }
}