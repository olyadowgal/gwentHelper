package com.dowgalolya.gwenthelper.dialogs

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.enums.CardsRowType
import kotlinx.android.synthetic.main.card_dialog_fragment.*

class AddCardDialog(
    context: Context,
    private val listener: OnCardAddListener,
    private val cardsRowType: CardsRowType
) : BaseCardDialog(context), DialogInterface.OnClickListener {

    companion object {
        const val TAG = "CARDDIALOG"
    }

    interface OnCardAddListener {

        fun onCardAdd(cardsRowType: CardsRowType, card: Card)
    }

    init {
        setButton(DialogInterface.BUTTON_POSITIVE, getContext().getString(R.string.add_card), this)
        setButton(DialogInterface.BUTTON_NEGATIVE, getContext().getString(R.string.cancel), this)
    }


    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                Log.d(TAG, textCardPoints.text.toString())

                listener.onCardAdd(
                    cardsRowType,
                    generateCard(textCardPoints.text.toString().toInt())
                )
                dismiss()
            }
            DialogInterface.BUTTON_NEGATIVE -> cancel()
        }
    }

}