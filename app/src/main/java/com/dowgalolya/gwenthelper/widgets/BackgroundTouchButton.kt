package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton


/**
 * Custom Shape Button which ignores touches on transparent background.
 */
class BackgroundTouchButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(
            text?.let {
                StringBuilder().apply {
                    for (i in 0 until it.length - 1) {
                        append(text[i])
                        append('\n')
                    }
                    append(it.last())
                }
            },
            type
        )
    }

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
        return color == Color.TRANSPARENT
    }

    init {
        @Suppress("DEPRECATION")
        isDrawingCacheEnabled = true
    }
}