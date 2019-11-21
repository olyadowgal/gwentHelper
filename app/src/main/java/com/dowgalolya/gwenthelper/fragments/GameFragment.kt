package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.dialogs.AddCardDialog
import com.dowgalolya.gwenthelper.dialogs.EditCardDialog
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.enums.CardsRowType
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.Companion.CARD
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.Companion.CARD_ROW
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.CustomViewAction
import com.dowgalolya.gwenthelper.widgets.CardsRowView
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.view_cards_row.view.*

class GameFragment : BaseFragment() {

    override val viewModel: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    private val rowViews: Map<CardsRowType, CardsRowView> by lazy {
        mapOf(
            CardsRowType.CLOSE_COMBAT to cv_close_combat,
            CardsRowType.LONG_RANGE to cv_long_range,
            CardsRowType.SIEGE to cv_siege
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.game_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rowViews.forEach { (type, view) ->
            view.setCardRowAdapter(viewModel.rowAdapters.getValue(type))
        }

        cv_weather.listener = viewModel

        cv_close_combat.setOnButtonClickListener(View.OnClickListener {
            viewModel.onPlusClicked(CardsRowType.CLOSE_COMBAT)
        })

        cv_close_combat.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.CLOSE_COMBAT, isChecked)
        }

        cv_long_range.setOnButtonClickListener(View.OnClickListener {
            viewModel.onPlusClicked(CardsRowType.LONG_RANGE)
        })

        cv_long_range.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.LONG_RANGE, isChecked)
        }

        cv_siege.setOnButtonClickListener(View.OnClickListener {
            viewModel.onPlusClicked(CardsRowType.SIEGE)
        })

        cv_siege.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.SIEGE, isChecked)
        }

        viewModel.selectedPlayerData.observe(this, Observer { playerData: PlayerData ->
            rowViews.forEach { (type, view) ->
                view.setCardCounterValue(playerData.cardsRows.getValue(type).totalPoints)
            }
        })
    }

    override fun handleViewAction(action: ViewAction) {
        when (action) {
            is ViewAction.Custom -> when (action.action) {
                CustomViewAction.SHOW_ADD_CARD_DIALOG -> {
                    AddCardDialog(
                        context!!,
                        viewModel,
                        action.args[CARD_ROW] as CardsRowType
                    ).show()
                }
                CustomViewAction.SHOW_EDIT_CARD_DIALOG -> {
                    EditCardDialog(
                        context!!,
                        viewModel,
                        action.args[CARD_ROW] as CardsRow,
                        action.args[CARD] as Card
                    ).show()

                }
                CustomViewAction.SHOW_CONFIG_CARD_DIALOG -> {
                    val cardsRow = action.args[CARD_ROW] as CardsRow
                    val card = action.args[CARD] as Card
                    AlertDialog.Builder(context!!)
                        .setTitle("What to do with card?")
                        .setNegativeButton("Edit") { _, _ ->
                            viewModel.onEditClicked(
                                cardsRow,
                                card
                            )
                        }
                        .setPositiveButton("Delete") { _, _ ->
                            viewModel.onDeleteClicked(
                                cardsRow,
                                card
                            )
                        }
                        .setNeutralButton("Cancel", null)
                        .show()
                }
                else -> super.handleViewAction(action)
            }
            else -> super.handleViewAction(action)
        }
    }
}
