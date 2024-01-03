package com.dowgalolya.gwenthelper.widgets

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.Toast
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.databinding.ViewWeatherBinding
import com.dowgalolya.gwenthelper.enums.CardsRowType

class WeatherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CompoundButton.OnCheckedChangeListener {

    var listener : OnWeatherChangeListener? = null
    private var binding: ViewWeatherBinding? = null

    init {
        binding = ViewWeatherBinding.inflate(LayoutInflater.from(context),this)
        addView(binding!!.root)
        binding!!.cbFrost.setOnCheckedChangeListener(this)
        binding!!.cbFog.setOnCheckedChangeListener(this)
        binding!!.cbRain.setOnCheckedChangeListener(this)

    }

    fun setWeather(isFrost: Boolean, isFog : Boolean, isRain : Boolean) {
        binding!!.cbFrost.isChecked = isFrost
        binding!!.cbFog.isChecked = isFog
        binding!!.cbRain.isChecked = isRain
    }

    interface OnWeatherChangeListener {

        fun onWeatherChange(cardsRowType: CardsRowType, weather : Boolean)
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        makeToast(buttonView,isChecked)
        when (buttonView) {
            binding!!.cbFrost -> {
                listener?.onWeatherChange(CardsRowType.CLOSE_COMBAT, isChecked)
            }
            binding!!.cbFog -> {
                listener?.onWeatherChange(CardsRowType.LONG_RANGE, isChecked)
            }
            binding!!.cbRain -> {
                listener?.onWeatherChange(CardsRowType.SIEGE, isChecked)
            }
        }
    }

    private fun makeToast(buttonView: CompoundButton, isChecked: Boolean) {
        var result = emptySequence<Char>().toString()
        when (buttonView) {
            binding!!.cbFrost -> {
                result = if (isChecked) context.getString(R.string.frost_true) else context.getString(R.string.frost_false)
            }
            binding!!.cbFog -> {
                result = if (isChecked) context.getString(R.string.fog_true) else context.getString(R.string.fog_false)
            }
            binding!!.cbRain -> {
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