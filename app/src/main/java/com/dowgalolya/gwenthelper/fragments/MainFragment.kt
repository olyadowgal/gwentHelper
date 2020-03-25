package com.dowgalolya.gwenthelper.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.viewmodels.MainViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment() : BaseFragment(), View.OnClickListener {


    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    lateinit var chosenUser: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_play.setOnClickListener(this)
        img_user1_avatar.setOnClickListener(this)
        img_user2_avatar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_play -> {
                viewModel.onButtonClicked(
                    f_user1_name.text?.toString(),
                    f_user2_name.text?.toString()
                )
            }
            R.id.img_user1_avatar -> {
                chosenUser = img_user1_avatar
                getPicture()
            }
            R.id.img_user2_avatar -> {
                chosenUser = img_user2_avatar
                getPicture()
            }
        }
    }

    fun getPicture() {
        getContext()?.let {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setFixAspectRatio(true)
                .setBorderLineColor(Color.GREEN)
                .setGuidelinesColor(Color.GRAY)
                .setAutoZoomEnabled(false)
                .start(it, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                chosenUser.setImageURI(result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }


}
