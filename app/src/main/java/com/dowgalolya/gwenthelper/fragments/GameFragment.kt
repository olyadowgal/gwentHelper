package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.adapters.itemdecoration.CardRowItemDecoration
import com.dowgalolya.gwenthelper.databinding.GameFragmentBinding
import com.dowgalolya.gwenthelper.di.SingletonHolder
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
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.Companion.REVIEW_INFO
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.Companion.REVIEW_MANAGER
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel.CustomViewAction
import com.dowgalolya.gwenthelper.widgets.CardsStatsView
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.view_cards_stats.view.*
import kotlinx.android.synthetic.main.view_user.view.*

class GameFragment : BaseFragment(), View.OnClickListener, View.OnLongClickListener {

    override val viewModel: GameViewModel by viewModels { SingletonHolder.viewModelFactory }
    private var _binding: GameFragmentBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val rowStats: Map<CardsRowType, CardsStatsView> by lazy {
        mapOf(
            CardsRowType.CLOSE_COMBAT to _binding!!.widgetStatsCloseCombat,
            CardsRowType.LONG_RANGE to _binding!!.widgetStatsLongRange,
            CardsRowType.SIEGE to _binding!!.widgetStatsSiege
        )
    }

    private val rowCards: Map<CardsRowType, RecyclerView> by lazy {
        mapOf(
            CardsRowType.CLOSE_COMBAT to _binding!!.rvCloseCombat,
            CardsRowType.LONG_RANGE to _binding!!.rvLongRange,
            CardsRowType.SIEGE to _binding!!.rvSiege
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

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

        _binding!!.widgetWeather.listener = viewModel
        viewModel.init(
            GameFragmentArgs.fromBundle(requireArguments()).user1,
            GameFragmentArgs.fromBundle(requireArguments()).user2
        )

        Glide.with(this)
            .load(GameFragmentArgs.fromBundle(requireArguments()).user1Photo)
            .placeholder(R.drawable.ic_male_avatar)
            .transform(CircleCrop())
            .into(_binding!!.widgetUser1.img_user_avatar)

        Glide.with(this)
            .load(GameFragmentArgs.fromBundle(requireArguments()).user2Photo)
            .placeholder(R.drawable.ic_female_avatar)
            .transform(CircleCrop())
            .into(widget_user2.img_user_avatar)

        _binding!!.widgetUser1.setOnClickListener(this)
        _binding!!.widgetUser2.setOnClickListener(this)
        _binding!!.btnReset.setOnLongClickListener(this)
        _binding!!.btnReset.setOnClickListener(this)
        _binding!!.btnExitGame.setOnClickListener(this)
        _binding!!.btnExitGame.setOnLongClickListener(this)

        _binding!!.widgetStatsCloseCombat.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.CLOSE_COMBAT, isChecked)
        }

        _binding!!.widgetStatsCloseCombat.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.LONG_RANGE, isChecked)
        }

        widget_stats_siege.cb_horn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHornChecked(CardsRowType.SIEGE, isChecked)
        }

        viewModel.selectedPlayer.observe(viewLifecycleOwner) {
            with(requireContext()) {
                when (it!!) {
                    Player.FIRST -> {
                        widget_user1.img_avatar_ring.setColorFilter(getColor(R.color.colorPrimary))
                        widget_user2.img_avatar_ring.setColorFilter(getColor(R.color.white))

                    }
                    Player.SECOND -> {
                        widget_user2.img_avatar_ring.setColorFilter(getColor(R.color.colorPrimary))
                        widget_user1.img_avatar_ring.setColorFilter(getColor(R.color.white))
                    }
                }
            }
        }

        viewModel.selectedPlayerData.observe(viewLifecycleOwner) { playerData: PlayerData ->
            rowStats.forEach { (type, view) ->
                view.setCardCounterValue(playerData.cardsRows.getValue(type).totalPoints)
                view.setHornValue(playerData.cardsRows.getValue(type).horn)
            }
            widget_weather.setWeather(
                playerData.cardsRows.getValue(CardsRowType.CLOSE_COMBAT).badWeather,
                playerData.cardsRows.getValue(CardsRowType.LONG_RANGE).badWeather,
                playerData.cardsRows.getValue(CardsRowType.SIEGE).badWeather
            )
        }

        viewModel.gameData.observe(viewLifecycleOwner) {
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
        }

        viewModel.gameOver.observe(viewLifecycleOwner) {
            val message = when (it!!) {
                Winner.FIRST -> getString(R.string.end_of_game_message) + " " + widget_user1.txt_user_name.text.toString()
                Winner.SECOND -> getString(R.string.end_of_game_message) + " " + widget_user2.txt_user_name.text.toString()
                Winner.TIE -> getString(R.string.end_of_game_tie_message)
            }
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.end_of_game_title))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.end_of_game_positive_btn)) { _, _ ->
                    viewModel.onGameEnds()
                }
                .setNegativeButton(getString(R.string.end_of_game_negative_btn)) { _, _ ->
                    viewModel.onGameEndsWithoutSaving()
                }
                .show()
        }
    }

    override fun handleViewAction(action: ViewAction) {
        when (action) {
            is ViewAction.Custom -> when (action.action) {
                CustomViewAction.SHOW_ADD_CARD_DIALOG -> {
                    AddCardDialog(
                        requireContext(),
                        viewModel,
                        action.args[CARD_ROW] as CardsRowType
                    ).show()
                }
                CustomViewAction.SHOW_EDIT_CARD_DIALOG -> {
                    EditCardDialog(
                        requireContext(),
                        viewModel,
                        action.args[CARD_ROW] as CardsRow,
                        action.args[CARD] as Card
                    ).show()

                }
                CustomViewAction.SHOW_CONFIG_CARD_DIALOG -> {
                    val cardsRow = action.args[CARD_ROW] as CardsRow
                    val card = action.args[CARD] as Card
                    AlertDialog.Builder(requireContext())
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
                CustomViewAction.FINISH_GAME -> {
                    findNavController().popBackStack()
                }
                CustomViewAction.LAUNCH_REVIEW_AND_FINISH_GAME -> {
                    val manager = action.args[REVIEW_MANAGER] as ReviewManager
                    val reviewInfo = action.args[REVIEW_INFO] as ReviewInfo
                    activity?.let { manager.launchReviewFlow(it, reviewInfo) }
                        ?.addOnCompleteListener { _ ->
                            findNavController().popBackStack()
                        }
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
            R.id.btn_exit_game -> {
                Toast.makeText(
                    context,
                    getString(R.string.end_game_msg),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onLongClick(view: View): Boolean {
        when (view.id) {
            R.id.btn_reset -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.end_round_title))
                    .setMessage(getString(R.string.end_round_message))
                    .setNegativeButton(getString(R.string.end_round_negative), null)
                    .setPositiveButton(getString(R.string.end_round_positive)) { _, _ ->
                        viewModel.onRoundEnds()
                    }
                    .show()
            }
            R.id.btn_exit_game -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.exit_game_title))
                    .setMessage(getString(R.string.exit_game_message))
                    .setNegativeButton(getString(R.string.end_round_negative), null)
                    .setPositiveButton(getString(R.string.end_round_positive)) { _, _ ->
                        findNavController().popBackStack()
                    }
                    .show()
            }
        }

        return true
    }
}
