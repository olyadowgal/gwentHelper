package com.dowgalolya.gwenthelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : BaseFragment(), View.OnClickListener {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_play.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        viewModel.onButtonClicked(
            f_user1_name.text?.toString(),
            f_user2_name.text?.toString()
        )
    }
}
