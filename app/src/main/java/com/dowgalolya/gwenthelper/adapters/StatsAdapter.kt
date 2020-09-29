package com.dowgalolya.gwenthelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.db.GameScore
import com.dowgalolya.gwenthelper.enums.Winner
import kotlinx.android.synthetic.main.item_stats.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class StatsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _stats: MutableList<GameScore> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FeedItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stats, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedItemViewHolder -> {holder.onBind(_stats[position])}
        }
    }

    override fun getItemCount(): Int {
       return _stats.size
    }

    fun add(score: GameScore) {
        _stats.add(score)
        notifyItemInserted(_stats.size)
    }
    fun clearAll() {
        _stats.clear()
    }

    inner class FeedItemViewHolder(
        val containerView: View
    ) : RecyclerView.ViewHolder(containerView) {

        fun onBind(item: GameScore) {
            containerView.txt_game_date.text = item.date
            containerView.txt_user_1.text = item.firstPlayer
            containerView.txt_user_2.text = item.secondPlayer
            when (item.winner) {
                Winner.FIRST.name -> {
                    containerView.img_winner_user_2.isVisible = false
                }
                Winner.SECOND.name -> {
                    containerView.img_winner_user_1.isVisible = false
                }
                Winner.TIE.name -> {
                    containerView.img_winner_user_1.imageTintList
                    containerView.img_winner_user_2.setColorFilter(R.color.white)
                }
            }
        }

    }

}