package com.dowgalolya.gwenthelper.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.dowgalolya.gwenthelper.dialogs.AddCardDialog
import com.dowgalolya.gwenthelper.dialogs.EditCardDialog
import com.dowgalolya.gwenthelper.entities.Card
import com.dowgalolya.gwenthelper.entities.CardsRow
import com.dowgalolya.gwenthelper.enums.CardsRowType
import com.dowgalolya.gwenthelper.enums.Winner
import com.dowgalolya.gwenthelper.livedata.ViewAction
import com.dowgalolya.gwenthelper.viewmodels.GameResultViewModel
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.game_result_fragment.*
import kotlinx.android.synthetic.main.view_user.view.*


class GameResultFragment : BaseFragment(), View.OnClickListener {

    override val viewModel: GameResultViewModel by viewModels {
        SingletonHolder.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.game_result_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        with(GameResultFragmentArgs.fromBundle(requireArguments())) {
            txt_user1_name.text = user1
            txt_user2_name.text = user2
            txt_user1_score.text = getString(R.string.global_score, user1TotalPoints)
            txt_user2_score.text = getString(R.string.global_score, user2TotalPoints)

            when (winner) {
                Winner.FIRST.name -> {
                    img_user1_crown.isVisible = true
                    img_user2_crown.isVisible = false
                    txt_game_result.text = getString(R.string.end_of_game_message, user1)
                }
                Winner.SECOND.name -> {
                    img_user1_crown.isVisible = false
                    img_user2_crown.isVisible = true
                    txt_game_result.text = getString(R.string.end_of_game_message, user2)
                }
                Winner.TIE.name -> {
                    img_user1_crown.isVisible = false
                    img_user2_crown.isVisible = false
                    txt_game_result.text = getString(R.string.end_of_game_tie_message)
                }
            }
        }

        Glide.with(this)
            .load(GameResultFragmentArgs.fromBundle(requireArguments()).user1Photo)
            .placeholder(R.drawable.ic_male_avatar)
            .transform(CircleCrop())
            .into(img_user1_avatar)

        Glide.with(this)
            .load(GameResultFragmentArgs.fromBundle(requireArguments()).user2Photo)
            .placeholder(R.drawable.ic_female_avatar)
            .transform(CircleCrop())
            .into(img_user2_avatar)

        btn_exit.setOnClickListener(this)
        btn_play.setOnClickListener(this)
    }

    override fun handleViewAction(action: ViewAction) {
        when (action) {
            is ViewAction.Custom -> when (action.action) {

                GameResultViewModel.CustomViewAction.LAUNCH_REVIEW -> {
                    val manager = action.args[GameViewModel.REVIEW_MANAGER] as ReviewManager
                    val reviewInfo = action.args[GameViewModel.REVIEW_INFO] as ReviewInfo
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
            R.id.btn_exit -> {

            }
            R.id.btn_play -> {
            }
        }
    }
}