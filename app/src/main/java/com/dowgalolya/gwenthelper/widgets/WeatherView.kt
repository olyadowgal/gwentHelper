package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.Toast
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
        makeToast(buttonView,isChecked)
        when (buttonView) {
            cb_frost -> {
                listener?.onWeatherChange(CardsRowType.CLOSE_COMBAT, isChecked)
            }
            cb_fog -> {
                listener?.onWeatherChange(CardsRowType.LONG_RANGE, isChecked)
            }
            cb_rain -> {
                listener?.onWeatherChange(CardsRowType.SIEGE, isChecked)
            }
        }
    }

    private fun makeToast(buttonView: CompoundButton, isChecked: Boolean) {
        var result = emptySequence<Char>().toString()
        when (buttonView) {
            cb_frost -> {
                result = if (isChecked) context.getString(R.string.frost_true) else context.getString(R.string.frost_false)
            }
            cb_fog -> {
                result = if (isChecked) context.getString(R.string.fog_true) else context.getString(R.string.fog_false)
            }
            cb_rain -> {
                result = if (isChecked) context.getString(R.string.rain_true) else context.getString(R.string.rain_false)
            }
        }
        Toast.makeText(
            context,
            result,
            Toast.LENGTH_LONG
        ).show()
    }
}