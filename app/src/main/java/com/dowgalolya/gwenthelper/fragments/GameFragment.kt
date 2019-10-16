package com.dowgalolya.gwenthelper.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dowgalolya.gwenthelper.CardConfigDialog
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel
import com.dowgalolya.gwenthelper.R
import kotlinx.android.synthetic.main.game_fragment.*


class GameFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = GameFragment()
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card_view.layoutManager = LinearLayoutManager(this.context)
        card_view.adapter = viewModel.cardRowAdapter
        btn_add_card.setOnClickListener(this)

        viewModel.currentRowPoints.observe(this, Observer {
            txt_row_counter.text = it.toString()
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_add_card -> {
                CardConfigDialog(context,viewModel).show()
            }
        }
    }

}
