package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dowgalolya.gwenthelper.dialogs.CardConfigDialog
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel
import com.dowgalolya.gwenthelper.widgets.CardsRowView
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.view_cards_row.view.*


class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    private val rowViews: Array<CardsRowView> by lazy {
        arrayOf(
            cv_close_combat,
            cv_long_range,
            cv_siege
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rowViews.forEachIndexed { index, cardsRowView ->
            cardsRowView.setCardRowAdapter(viewModel.rowAdapters[index])
        }

        cv_weather.listener = viewModel

        cv_close_combat.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(
                context,
                viewModel,
                R.id.cv_close_combat
            ).show()
        })

        cv_close_combat.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(R.id.cv_close_combat, isChecked)
        }

        cv_long_range.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(
                context,
                viewModel,
                R.id.cv_long_range
            ).show()
        })

        cv_long_range.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(R.id.cv_long_range, isChecked)
        }

        cv_siege.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(context, viewModel, R.id.cv_siege).show()
        })

        cv_siege.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(R.id.cv_siege, isChecked)
        }

        viewModel.selectedPlayerData.observe(this, Observer { playerData: PlayerData ->
            rowViews.forEachIndexed { index, cardsRowView ->
                cardsRowView.setCardCounterValue(playerData.cardRows[index].totalPoints())
            }
        })
    }

}
