package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import android.widget.FrameLayout
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.enums.CardsRowType
import kotlinx.android.synthetic.main.view_weather.view.*

class WeatherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CompoundButton.OnCheckedChangeListener {

    var listener : OnWeatherChangeListener? = null

    init {
        View.inflate(context, R.layout.view_weather, this)
        cb_frost.setOnCheckedChangeListener(this)
        cb_fog.setOnCheckedChangeListener(this)
        cb_rain.setOnCheckedChangeListener(this)

    }

    fun setWeather(isFrost: Boolean, isFog : Boolean, isRain : Boolean) {
        cb_frost.isChecked = isFrost
        cb_fog.isChecked = isFog
        cb_rain.isChecked = isRain
    }

    interface OnWeatherChangeListener {

        fun onWeatherChange(cardsRowType: CardsRowType, weather : Boolean)
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView) {
            cb_frost -> listener?.onWeatherChange(CardsRowType.CLOSE_COMBAT, isChecked)
            cb_fog -> listener?.onWeatherChange(CardsRowType.LONG_RANGE, isChecked)
            cb_rain -> listener?.onWeatherChange(CardsRowType.SIEGE, isChecked)
        }
    }
}