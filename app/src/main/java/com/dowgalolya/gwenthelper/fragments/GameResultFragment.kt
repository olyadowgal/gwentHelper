package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.dowgalolya.gwenthelper.viewmodels.GameResultViewModel
import kotlinx.android.synthetic.main.game_result_fragment.*


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

        btn_exit.setOnClickListener(this)
        btn_play.setOnClickListener(this)
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