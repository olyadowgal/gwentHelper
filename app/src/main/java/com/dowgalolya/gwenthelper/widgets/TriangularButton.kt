package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent


/**
 * Custom Shape Button which ignores touches on transparent background.
 */
class TriangularButton(context: Context?, attrs: AttributeSet?, defStyle: Int) :
    androidx.appcompat.widget.AppCompatButton(context!!, attrs, defStyle) {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()

        // ignores touches on transparent background
        return if (isPixelTransparent(x, y)) true else super.onTouchEvent(event)
    }

    /**
     * @return true if pixel from (x,y) is transparent
     */
    private fun isPixelTransparent(x: Int, y: Int): Boolean {
        val bmp: Bitmap = Bitmap.createBitmap(getDrawingCache())
        var color: Int = Color.TRANSPARENT
        try {
            color = bmp.getPixel(x, y)
        } catch (e: IllegalArgumentException) {
            // x or y exceed the bitmap's bounds.
            // Reverts the View's internal state from a previously set "pressed" state.
            isPressed = false
        }

        // Ignores touches on transparent background.
        return if (color == Color.TRANSPARENT) true else false
    }

    init {
        isDrawingCacheEnabled = true
    }
}