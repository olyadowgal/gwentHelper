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
import com.dowgalolya.gwenthelper.enums.Player
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.Companion.CARD
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.Companion.CARD_ROW
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.CustomViewAction
import com.dowgalolya.gwenthelper.widgets.CardsRowView
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.view_cards_row.view.*
import kotlinx.android.synthetic.main.view_user.view.*

class GameFragment : BaseFragment(), View.OnClickListener {

    override val viewModel: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    private val rowViews: Map<CardsRowType, CardsRowView> by lazy {
        mapOf(
            CardsRowType.CLOSE_COMBAT to widget_close_combat,
            CardsRowType.LONG_RANGE to widget_long_range,
            CardsRowType.SIEGE to widget_siege
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

        widget_weather.listener = viewModel

        widget_user1.txt_user_name.text = GameFragmentArgs.fromBundle(arguments!!).user1
        widget_user2.txt_user_name.text = GameFragmentArgs.fromBundle(arguments!!).user2

        widget_close_combat.setOnButtonClickListener(this)
        widget_long_range.setOnButtonClickListener(this)
        widget_siege.setOnButtonClickListener(this)
        widget_user1.setOnClickListener(this)
        widget_user2.setOnClickListener(this)

        widget_close_combat.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.CLOSE_COMBAT, isChecked)
        }

        widget_long_range.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.LONG_RANGE, isChecked)
        }

        widget_siege.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.SIEGE, isChecked)
        }

        viewModel.selectedPlayerData.observe(this, Observer { playerData: PlayerData ->
            rowViews.forEach { (type, view) ->
                view.setCardCounterValue(playerData.cardsRows.getValue(type).totalPoints)
                view.setHornValue(playerData.cardsRows.getValue(type).horn)
            }
            when (viewModel.selectedPlayer.value) {
                Player.FIRST -> widget_user1.txt_user_points.text = playerData.totalPoints.toString()
                Player.SECOND -> widget_user2.txt_user_points.text = playerData.totalPoints.toString()
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

    override fun onClick(view: View) {
       when (view) {
           widget_close_combat.btn_add_card -> viewModel.onPlusClicked(CardsRowType.CLOSE_COMBAT)
           widget_long_range.btn_add_card ->  viewModel.onPlusClicked(CardsRowType.LONG_RANGE)
           widget_siege.btn_add_card -> viewModel.onPlusClicked(CardsRowType.SIEGE)
           widget_user1 -> viewModel.onUserClicked(Player.FIRST)
           widget_user2 -> viewModel.onUserClicked(Player.SECOND)
       }
    }
}
