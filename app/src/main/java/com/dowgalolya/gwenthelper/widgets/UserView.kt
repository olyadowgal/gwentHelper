package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.databinding.ViewUserBinding

class UserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var binding: ViewUserBinding? = null

    init {
        binding = ViewUserBinding.inflate(LayoutInflater.from(context),this)
        addView(binding!!.root)
        clipToPadding = false
        clipChildren = false
    }


    fun winnerPointsColor() {
        binding!!.txtUserPoints.setTextColor(resources.getColor(R.color.colorSecondary, null))
    }

    fun loserPointsColor() {
        binding!!.txtUserPoints.setTextColor(resources.getColor(R.color.oxford_blue, null))
    }

    fun setLives(lives : Int) {
        when (lives) {
            2 ->{
                binding!!.imgUserCoin1.isActivated = true
                binding!!.imgUserCoin2.isActivated = true
            }
            1 -> binding!!.imgUserCoin2.isActivated = false
            0 -> binding!!.imgUserCoin1.isActivated = false
        }
    }
}