package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.enums.Winner
import kotlinx.android.synthetic.main.item_score.view.*
import java.lang.IllegalArgumentException
import kotlin.collections.ArrayList

class ScoreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _stats: MutableList<GameScore> = ArrayList()

    init {
        setHasStableIds(true)
    }

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
                holder.onBind(_stats[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_stats.isEmpty()) ViewType.PLACEHOLDER
        else ViewType.FEED
    }

    override fun getItemCount(): Int {
        return if (_stats.isNullOrEmpty()) 1 else _stats.size
    }

    fun add(score: GameScore) {
        _stats.add(score)
        notifyItemInserted(_stats.size)
    }

    fun clearAll() {
        _stats.clear()
    }

    inner class FeedItemViewHolder(
        private val containerView: View
    ) : RecyclerView.ViewHolder(containerView) {

        fun onBind(item: GameScore) {
            containerView.txt_game_date.text = item.date
            containerView.txt_user_1.text = item.firstPlayer
            containerView.txt_user_2.text = item.secondPlayer
            containerView.txt_round_1_player_1.text = item.firstRoundFirstPlayerPoints.toString()
            containerView.txt_round_1_player_2.text = item.firstRoundSecondPlayerPoints.toString()
            containerView.txt_round_2_player_1.text = item.secondRoundFirstPlayerPoints.toString()
            containerView.txt_round_2_player_2.text = item.secondRoundSecondPlayerPoints.toString()
            containerView.txt_round_3_player_1.text = item.thirdRoundFirstPlayerPoints.toString()
            containerView.txt_round_3_player_2.text = item.thirdRoundSecondPlayerPoints.toString()
            when (item.winner) {
                Winner.FIRST.name -> {
                    containerView.img_winner_user_2.setColorFilter(R.color.colorSimpleCard)
                }
                Winner.SECOND.name -> {
                    containerView.img_winner_user_1.setColorFilter(R.color.colorSimpleCard)
                }
                Winner.TIE.name -> {
                    containerView.img_winner_user_1.setColorFilter(R.color.colorSimpleCard)
                    containerView.img_winner_user_2.setColorFilter(R.color.colorSimpleCard)
                }
            }

        }
    }

    inner class PlaceholderViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

}