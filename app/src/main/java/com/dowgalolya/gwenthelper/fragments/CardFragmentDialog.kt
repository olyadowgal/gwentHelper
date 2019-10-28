package com.dowgalolya.gwenthelper.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dowgalolya.gwenthelper.R
import kotlinx.android.synthetic.main.card_dialog_fragment.*


class CardFragmentDialog : DialogFragment(), View.OnClickListener {
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.card_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_minus_value.setOnClickListener(this)
        btn_plus_value.setOnClickListener(this)
    } 

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_plus_value -> {
               txt_card_points.text = (txt_card_points.text.toString().toInt() + 1).toString()
            }
            R.id.btn_minus_value -> {
                txt_card_points.text = (txt_card_points.text.toString().toInt() - 1).toString()

            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
    }

}
