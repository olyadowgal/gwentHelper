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
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.dowgalolya.gwenthelper.viewmodels.ScoreViewModel
import kotlinx.android.synthetic.main.score_fragment.*

class ScoreFragment : BaseFragment(), View.OnLongClickListener, View.OnClickListener {

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
        img_back.setOnClickListener(this)
        img_reset_db.setOnClickListener(this)
        img_reset_db.setOnLongClickListener(this)
        viewModel.loadDatabaseScores()
    }

    override fun onLongClick(view: View): Boolean {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.clear_db_title))
            .setMessage(getString(R.string.clear_db_message))
            .setNegativeButton(getString(R.string.clear_db_negative), null)
            .setPositiveButton(getString(R.string.clear_db_positive)) { _, _ ->
                viewModel.clearScores()
            }
            .show()
        return true
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.img_back -> findNavController().popBackStack()
            R.id.img_reset_db -> {
                Toast.makeText(
                    context,
                    getString(R.string.click_reset_db),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}