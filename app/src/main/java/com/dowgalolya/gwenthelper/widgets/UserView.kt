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


    fun winnerPointsColor() {
        txt_user_points.setTextColor(resources.getColor(R.color.colorSecondary, null))
    }

    fun loserPointsColor() {
        txt_user_points.setTextColor(resources.getColor(R.color.oxford_blue, null))
    }

    fun setLives(lives : Int) {
        when (lives) {
            2 ->{
                iv_user_coin1.setImageResource(R.drawable.ic_jewel)
                iv_user_coin2.setImageResource(R.drawable.ic_jewel)
            }
            1 -> iv_user_coin2.setColorFilter(context.getColor(R.color.mid_gray_40_percent))
            0 -> iv_user_coin1.setColorFilter(context.getColor(R.color.mid_gray_40_percent))
        }
    }

}