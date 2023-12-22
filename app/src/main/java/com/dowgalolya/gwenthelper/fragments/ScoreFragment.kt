package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.databinding.ScoreFragmentBinding
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.dowgalolya.gwenthelper.viewmodels.ScoreViewModel

class ScoreFragment : BaseFragment(), View.OnLongClickListener, View.OnClickListener {

    override val viewModel: ScoreViewModel by viewModels { SingletonHolder.viewModelFactory }
    private var _binding: ScoreFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScoreFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding!!.listStats.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        _binding!!.listStats.adapter = viewModel.statsAdapter
        _binding!!.btnBack.setOnClickListener(this)
        _binding!!.btnResetDb.setOnClickListener(this)
        _binding!!.btnResetDb.setOnLongClickListener(this)
        viewModel.isScoresButtonActive.observe(viewLifecycleOwner) {
            _binding!!.btnResetDb.isVisible = it
        }
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
            R.id.btn_back -> findNavController().popBackStack()
            R.id.btn_reset_db -> {
                Toast.makeText(
                    context,
                    getString(R.string.click_reset_db),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}