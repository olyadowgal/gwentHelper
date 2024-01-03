package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.databinding.ItemScoreBinding
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.enums.Winner
import java.text.SimpleDateFormat
import java.util.*

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
        val formatter = SimpleDateFormat("hh:mm dd MMM yyyy", Locale.getDefault())
        private val binding: ItemScoreBinding = ItemScoreBinding.bind(itemView)

        fun onBind(item: GameScore) = with(itemView) {
            binding.txtGameDate.text = formatter.format(item.date)
            binding.txtUser1.text = item.firstPlayer
            binding.txtUser2.text = item.secondPlayer

            binding.txtRound1Player1.text = item.firstRoundFirstPlayerPoints?.toString() ?: "-"
            binding.txtRound2Player1.text = item.secondRoundFirstPlayerPoints?.toString() ?: "-"
            binding.txtRound3Player1.text = item.thirdRoundFirstPlayerPoints?.toString() ?: "-"

            binding.txtRound1Player2.text = item.firstRoundSecondPlayerPoints?.toString() ?: "-"
            binding.txtRound2Player2.text = item.secondRoundSecondPlayerPoints?.toString() ?: "-"
            binding.txtRound3Player2.text = item.thirdRoundSecondPlayerPoints?.toString() ?: "-"

            binding.imgWinnerUser1.isActivated = item.winner == Winner.FIRST.name
            binding.imgWinnerUser2.isActivated = item.winner == Winner.SECOND.name


        }
    }

    inner class PlaceholderViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

}