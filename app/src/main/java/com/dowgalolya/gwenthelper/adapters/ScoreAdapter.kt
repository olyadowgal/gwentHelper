package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.enums.Winner
import kotlinx.android.synthetic.main.item_score.view.*

class ScoreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _scores: List<GameScore> = emptyList()

    object ViewType {
        const val PLACEHOLDER = R.layout.item_placehoder
        const val FEED = R.layout.item_score
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            ViewType.PLACEHOLDER -> PlaceholderViewHolder(view)
            ViewType.FEED -> FeedItemViewHolder(view)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedItemViewHolder -> {
                holder.onBind(_scores[position])
            }
        }
    }

    override fun getItemViewType(
        position: Int
    ): Int = if (_scores.isEmpty()) ViewType.PLACEHOLDER else ViewType.FEED

    override fun getItemCount(): Int = if (_scores.isNullOrEmpty()) 1 else _scores.size

    fun addAll(score: List<GameScore>) {
        _scores = score
        notifyDataSetChanged()
    }

    fun clearAll() {
        _scores = emptyList()
        notifyDataSetChanged()
    }

    inner class FeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: GameScore) = with(itemView) {
            txt_game_date.text = item.date.toString()
            txt_user_1.text = item.firstPlayer
            txt_user_2.text = item.secondPlayer

            txt_round_1_player_1.text = item.firstRoundFirstPlayerPoints?.toString() ?: "-"
            txt_round_2_player_1.text = item.secondRoundFirstPlayerPoints?.toString() ?: "-"
            txt_round_3_player_1.text = item.thirdRoundFirstPlayerPoints?.toString() ?: "-"

            txt_round_1_player_2.text = item.firstRoundSecondPlayerPoints?.toString() ?: "-"
            txt_round_2_player_2.text = item.secondRoundSecondPlayerPoints?.toString() ?: "-"
            txt_round_3_player_2.text = item.thirdRoundSecondPlayerPoints?.toString() ?: "-"

            img_winner_user_1.isActivated = item.winner == Winner.FIRST.name
            img_winner_user_2.isActivated = item.winner == Winner.SECOND.name


        }
    }

    inner class PlaceholderViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

}