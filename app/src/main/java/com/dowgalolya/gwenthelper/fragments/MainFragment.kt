package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), View.OnClickListener {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_play.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        view.findNavController().navigate(R.id.action_mainFragment_to_gameFragment)
    }

}
