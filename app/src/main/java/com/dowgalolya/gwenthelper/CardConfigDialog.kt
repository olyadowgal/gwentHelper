package com.dowgalolya.gwenthelper

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class CardConfigDialog(context: Context?, val listener: OnCardCreateListener) : AlertDialog(context),
    DialogInterface.OnClickListener, View.OnClickListener {

    companion object {
        const val TAG = "CARDDIALOG"
    }

    interface OnCardCreateListener {

        fun onCardSet(cardValue: Int)
    }

    private val textCardPoints: TextView

    init {
        val view = View.inflate(context, R.layout.card_dialog_fragment, null)
        setView(view)

        setButton(DialogInterface.BUTTON_POSITIVE, "ADD CARD", this)
        setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", this)

        textCardPoints = view.findViewById(R.id.text_card_points)
        view.findViewById<Button>(R.id.btn_minus_value).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_plus_value).setOnClickListener(this)
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                Log.d(TAG,textCardPoints.text.toString())
                listener.onCardSet(textCardPoints.text.toString().toInt())
                dismiss()
            }
            DialogInterface.BUTTON_NEGATIVE -> cancel()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_plus_value -> {
                Log.d(TAG,textCardPoints.text.toString())
                textCardPoints.text = (textCardPoints.text.toString().toInt() + 1).toString()
            }
            R.id.btn_minus_value -> {

                textCardPoints.text = (textCardPoints.text.toString().toInt() - 1).toString()

            }
        }
    }

}