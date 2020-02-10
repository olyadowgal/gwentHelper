package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.adapters.itemdecoration.CardRowItemDecoration
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
import com.dowgalolya.gwenthelper.widgets.CardsStatsView
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.view_cards_stats.view.*
import kotlinx.android.synthetic.main.view_user.view.*

class GameFragment : BaseFragment(), View.OnClickListener, View.OnLongClickListener {

    override val viewModel: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    private val rowStats: Map<CardsRowType, CardsStatsView> by lazy {
        mapOf(
            CardsRowType.CLOSE_COMBAT to widget_stats_close_combat,
            CardsRowType.LONG_RANGE to widget_stats_long_range,
            CardsRowType.SIEGE to widget_stats_siege
        )
    }

    private val rowCards: Map<CardsRowType, RecyclerView> by lazy {
        mapOf(
            CardsRowType.CLOSE_COMBAT to rv_close_combat,
            CardsRowType.LONG_RANGE to rv_long_range,
            CardsRowType.SIEGE to rv_siege
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.game_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rowCards.forEach { (type, view) ->

            view.apply {
                            layoutManager = LinearLayoutManager(
                this.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            this.adapter = viewModel.rowAdapters.getValue(type)
            addItemDecoration(CardRowItemDecoration(context))
        }

        }

        widget_weather.listener = viewModel

        widget_user1.txt_user_name.text = GameFragmentArgs.fromBundle(arguments!!).user1
        widget_user2.txt_user_name.text = GameFragmentArgs.fromBundle(arguments!!).user2

        widget_user1.setOnClickListener(this)
        widget_user2.setOnClickListener(this)
        btn_pass.setOnLongClickListener(this)

        widget_stats_close_combat.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.CLOSE_COMBAT, isChecked)
        }

        widget_stats_long_range.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.LONG_RANGE, isChecked)
        }

        widget_stats_siege.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.SIEGE, isChecked)
        }

        viewModel.selectedPlayerData.observe(viewLifecycleOwner, Observer { playerData: PlayerData ->
            rowStats.forEach { (type, view) ->
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
           widget_user1 -> viewModel.onUserClicked(Player.FIRST)
           widget_user2 -> viewModel.onUserClicked(Player.SECOND)
       }
    }

    override fun onLongClick(v: View?): Boolean {
        viewModel.onPassClicked()
        val toast = Toast.makeText(context, getString(R.string.pass_msg), Toast.LENGTH_SHORT)
        toast.show()
        return true
    }
}
