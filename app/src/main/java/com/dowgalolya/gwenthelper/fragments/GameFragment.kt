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
        rv_cards_user1_close_combat.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv_cards_user1_close_combat.adapter = viewModel.user1CloseCombatRowAdapter
        btn_add_user1_close_combat.setOnClickListener(this)

        rv_cards_user1_long_range.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv_cards_user1_long_range.adapter = viewModel.user1LongRangeAdapter
        btn_add_user1_long_range.setOnClickListener(this)

        rv_cards_user1_siege.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        rv_cards_user1_siege.adapter = viewModel.user1SiegeAdapter
        btn_add_user1_siege.setOnClickListener(this)

        viewModel.user1CloseCombatRowPoints.observe(this, Observer {
            txt_counter_user1_close_combat.text = it.toString()
        })
        viewModel.user1LongRangePoints.observe(this, Observer {
            txt_counter_user1_long_range.text = it.toString()
        })
        viewModel.user1SiegePoints.observe(this, Observer {
            txt_counter_user1_siege.text = it.toString()
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_add_user1_close_combat -> {
                CardConfigDialog(context,viewModel,R.id.btn_add_user1_close_combat).show()
            }
            R.id.btn_add_user1_long_range -> {
                CardConfigDialog(context,viewModel,view.id).show()
            }
            R.id.btn_add_user1_siege -> {
                CardConfigDialog(context,viewModel,R.id.btn_add_user1_siege).show()
            }
        }
    }

}
