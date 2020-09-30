package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.dowgalolya.gwenthelper.viewmodels.ScoreViewModel
import kotlinx.android.synthetic.main.score_fragment.*

class ScoreFragment : BaseFragment() {

    override val viewModel: ScoreViewModel by viewModels { SingletonHolder.viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.score_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_stats.layoutManager = LinearLayoutManager(context)
        list_stats.adapter = viewModel.statsAdapter
        viewModel.loadDatabaseScores()
    }
}