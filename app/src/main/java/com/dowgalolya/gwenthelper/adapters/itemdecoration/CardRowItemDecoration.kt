package com.dowgalolya.gwenthelper.adapters.itemdecoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dowgalolya.gwenthelper.R

class CardRowItemDecoration(context : Context) : RecyclerView.ItemDecoration() {

    private val regularMargin = context.resources.getDimensionPixelSize(R.dimen.card_item_margin_regular)
    private val startEndMargin = context.resources.getDimensionPixelSize(R.dimen.card_item_margin_start_end)
    private val topBottomMargin = context.resources.getDimensionPixelSize(R.dimen.card_item_margin_top_bottom)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        super.getItemOffsets(outRect, view, parent, state)
        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.right = regularMargin
                outRect.left = startEndMargin
                outRect.top = topBottomMargin
                outRect.bottom = topBottomMargin
            }
            parent.adapter!!.itemCount - 1 -> {
                outRect.right = startEndMargin
                outRect.left = regularMargin
                outRect.top = topBottomMargin
                outRect.bottom = topBottomMargin
            }
            else -> {
                outRect.right = regularMargin
                outRect.left = regularMargin
                outRect.top = topBottomMargin
                outRect.bottom = topBottomMargin
            }
        }
    }
}