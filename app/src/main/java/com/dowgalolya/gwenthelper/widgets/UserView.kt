package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.dowgalolya.gwenthelper.R
import kotlinx.android.synthetic.main.view_user.view.*

class UserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_user, this)
        clipToPadding = false
        clipChildren = false
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
                img_user_coin1.isActivated = true
                img_user_coin2.isActivated = true
            }
            1 -> img_user_coin2.isActivated = false
            0 -> img_user_coin1.isActivated = false
        }
    }
}