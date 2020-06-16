package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.adapters.itemdecoration.CardRowItemDecoration
import com.dowgalolya.gwenthelper.dialogs.AddCardDialog
import com.dowgalolya.gwenthelper.dialogs.EditCardDialog
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow
import com.dowgalolya.gwenthelper.entities.PlayerData
import com.dowgalolya.gwenthelper.enums.CardsRowType
import com.dowgalolya.gwenthelper.enums.Player
import com.dowgalolya.gwenthelper.enums.Winner
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.Companion.CARD
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.Companion.CARD_ROW
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.CustomViewAction
import com.dowgalolya.gwenthelper.viewmodels.GameViewModelFactory
import com.dowgalolya.gwenthelper.widgets.CardsStatsView
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.view_cards_stats.view.*
import kotlinx.android.synthetic.main.view_user.view.*

class GameFragment : BaseFragment(), View.OnClickListener, View.OnLongClickListener {

    override val viewModel: GameViewModel by lazy {
        activity?.application?.let { GameViewModelFactory(it).create(GameViewModel::class.java) }!!
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
        viewModel.init(
            GameFragmentArgs.fromBundle(arguments!!).user1,
            GameFragmentArgs.fromBundle(arguments!!).user2
        )

        Glide.with(this)
            .load(GameFragmentArgs.fromBundle(arguments!!).user1Photo)
            .placeholder(R.drawable.ic_male_avatar)
            .transform(CircleCrop())
            .into(widget_user1.img_user_avatar)

        Glide.with(this)
            .load(GameFragmentArgs.fromBundle(arguments!!).user2Photo)
            .placeholder(R.drawable.ic_female_avatar)
            .transform(CircleCrop())
            .into(widget_user2.img_user_avatar)

        widget_user1.setOnClickListener(this)
        widget_user2.setOnClickListener(this)
        btn_reset.setOnLongClickListener(this)
        btn_reset.setOnClickListener(this)
        btn_exit_game.setOnClickListener(this)

        widget_stats_close_combat.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.CLOSE_COMBAT, isChecked)
        }

        widget_stats_long_range.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.LONG_RANGE, isChecked)
        }

        widget_stats_siege.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.SIEGE, isChecked)
        }

        viewModel.selectedPlayer.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    Player.FIRST -> {
                        context?.getColor(R.color.colorPrimary)?.let {
                            widget_user1.img_avatar_ring.setColorFilter(it)
                        }
                        context?.getColor(R.color.white)?.let {
                            widget_user2.img_avatar_ring.setColorFilter(it)
                        }

                    }
                    Player.SECOND -> {
                        context?.getColor(R.color.colorPrimary)?.let {
                            widget_user2.img_avatar_ring.setColorFilter(it)
                        }
                        context?.getColor(R.color.white)?.let {
                            widget_user1.img_avatar_ring.setColorFilter(it)
                        }
                    }
                }

            }
        )

        viewModel.selectedPlayerData.observe(
            viewLifecycleOwner,
            Observer { playerData: PlayerData ->
                rowStats.forEach { (type, view) ->
                    view.setCardCounterValue(playerData.cardsRows.getValue(type).totalPoints)
                    view.setHornValue(playerData.cardsRows.getValue(type).horn)
                }
                widget_weather.setWeather(
                    playerData.cardsRows.getValue(CardsRowType.CLOSE_COMBAT).badWeather,
                    playerData.cardsRows.getValue(CardsRowType.LONG_RANGE).badWeather,
                    playerData.cardsRows.getValue(CardsRowType.SIEGE).badWeather
                )
            })

        viewModel.gameData.observe(viewLifecycleOwner, Observer {
            widget_user1.txt_user_name.text = it.firstPlayerData.name
            widget_user2.txt_user_name.text = it.secondPlayerData.name
            widget_user1.txt_user_points.text = it.firstPlayerData.totalPoints.toString()
            widget_user2.txt_user_points.text = it.secondPlayerData.totalPoints.toString()

            widget_user1.setLives(it.firstPlayerData.lives)
            widget_user2.setLives(it.secondPlayerData.lives)

            when (it.winner) {
                Winner.FIRST -> {
                    widget_user1.winnerPointsColor()
                    widget_user2.loserPointsColor()
                }
                Winner.SECOND -> {
                    widget_user1.loserPointsColor()
                    widget_user2.winnerPointsColor()
                }
                Winner.TIE -> {
                    widget_user1.loserPointsColor()
                    widget_user2.loserPointsColor()
                }
            }

        })

        viewModel.gameOver.observe(viewLifecycleOwner, Observer {
            val message = when (it) {
                Winner.FIRST -> getString(R.string.end_of_game_message) + " " + widget_user1.txt_user_name.text.toString()
                Winner.SECOND -> getString(R.string.end_of_game_message) + " " + widget_user2.txt_user_name.text.toString()
                Winner.TIE -> getString(R.string.end_of_game_tie_message)
            }
            AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.end_of_game_title))
                .setMessage(message)
                .setCancelable(false)
//                .setNegativeButton(R.string.end_of_game_negative_btn) { _, _ ->
//                    findNavController().popBackStack()
//                }
                .setPositiveButton(getString(R.string.end_of_game_positive_btn)) { _, _ ->
                    // TODO: Save game stats here
                    findNavController().popBackStack()
                }
                .show()
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
                        .setTitle(getString(R.string.config_card_dialog_title))
                        .setNegativeButton(getString(R.string.config_card_dialog_negative)) { _, _ ->
                            viewModel.onEditClicked(cardsRow, card)
                        }
                        .setPositiveButton(getString(R.string.config_card_dialog_positive)) { _, _ ->
                            viewModel.onDeleteClicked(cardsRow, card)
                        }
                        .setNeutralButton(getString(R.string.config_card_dialog_neutral), null)
                        .show()
                }
                else -> super.handleViewAction(action)
            }
            else -> super.handleViewAction(action)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.widget_user1 -> viewModel.onUserClicked(Player.FIRST)
            R.id.widget_user2 -> viewModel.onUserClicked(Player.SECOND)
            R.id.btn_reset -> {
                Toast.makeText(
                    context,
                    getString(R.string.click_reset_msg),
                    Toast.LENGTH_LONG
                ).show()
            }
            R.id.btn_exit_game -> findNavController().popBackStack()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.end_round_title))
            .setMessage(getString(R.string.end_round_message))
            .setNegativeButton(getString(R.string.end_round_negative), null)
            .setPositiveButton(getString(R.string.end_round_positive)) { _, _ ->
                viewModel.onPassClicked()
            }
            .show()
        return true
    }
}
