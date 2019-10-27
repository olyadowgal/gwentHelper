package com.dowgalolya.gwenthelper.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dowgalolya.gwenthelper.CardConfigDialog
import com.dowgalolya.gwenthelper.viewmodels.GameViewModel
import com.dowgalolya.gwenthelper.R
import kotlinx.android.synthetic.main.game_fragment.*


class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cv_user1_close_combat.setCardRowAdapter(viewModel.user1CloseCombatRowAdapter)
        cv_user1_close_combat.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(context,viewModel,R.id.cv_user1_close_combat).show()
        })

        cv_user1_long_range.setCardRowAdapter(viewModel.user1LongRangeAdapter)
        cv_user1_long_range.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(context,viewModel,R.id.cv_user1_long_range).show()
        })

        cv_user1_siege.setCardRowAdapter(viewModel.user1SiegeAdapter)
        cv_user1_siege.setOnButtonClickListener(View.OnClickListener {
            CardConfigDialog(context,viewModel,R.id.cv_user1_siege).show()
        })


        viewModel.user1CloseCombatRowPoints.observe(this, Observer {
            cv_user1_close_combat.setCardCounterValue(it)
        })
        viewModel.user1LongRangePoints.observe(this, Observer {
            cv_user1_long_range.setCardCounterValue(it)
        })
        viewModel.user1SiegePoints.observe(this, Observer {
            cv_user1_siege.setCardCounterValue(it)
        })
    }

}
