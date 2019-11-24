package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.dowgalolya.gwenthelper.R
import kotlinx.android.synthetic.main.view_user.view.*

class UserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_user, this)
    }

}