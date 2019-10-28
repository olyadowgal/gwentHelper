package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dowgalolya.gwenthelper.CardConfigDialog
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel
import com.dowgalolya.gwenthelper.widgets.CardsRowView
import kotlinx.android.synthetic.main.game_fragment.*


class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    private val rowViews: Array<CardsRowView> by lazy {
        arrayOf(
            cv_user1_close_combat,
            cv_user1_long_range,
            cv_user1_siege
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
        cv_user1_close_combat.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(context, viewModel, R.id.cv_user1_close_combat).show()
        })
        cv_user1_long_range.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(context, viewModel, R.id.cv_user1_long_range).show()
        })
        cv_user1_siege.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(context, viewModel, R.id.cv_user1_siege).show()
        })

        viewModel.selectedPlayerData.observe(this, Observer { playerData: PlayerData ->
            rowViews.forEachIndexed { index, cardsRowView ->
                cardsRowView.setCardCounterValue(playerData.cardRows[index].totalPoints())
            }
        })
    }

}
