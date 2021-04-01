package com.dowgalolya.gwenthelper.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.dowgalolya.gwenthelper.viewmodels.MainViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment(), View.OnClickListener {

    override val viewModel: MainViewModel by viewModels {
        SingletonHolder.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_play.setOnClickListener(this)
        btn_exit.setOnClickListener(this)
        img_user1_avatar.setOnClickListener(this)
        img_user2_avatar.setOnClickListener(this)

        viewModel.selectedUser.observe(viewLifecycleOwner, Observer {
            context?.let {
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setFixAspectRatio(true)
                    .setBorderLineColor(resources.getColor(R.color.colorPrimary, null))
                    .setGuidelinesColor(resources.getColor(R.color.oxford_blue, null))
                    .setAutoZoomEnabled(true)
                    .start(it, this)
            }
        })

        viewModel.firstUserPhotoUri.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it)
                .transform(CircleCrop())
                .into(img_user1_avatar)

        })

        viewModel.secondUserPhotoUri.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it)
                .transform(CircleCrop())
                .into(img_user2_avatar)
        })

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_play -> {
                viewModel.onPlayClicked(
                    f_user1_name.text?.toString(),
                    f_user2_name.text?.toString()
                )
            }
            R.id.btn_exit -> {
                viewModel.onScoresClicked()
            }
            R.id.img_user1_avatar -> {
                viewModel.onPhotoClicked(R.id.img_user1_avatar)
            }
            R.id.img_user2_avatar -> {
                viewModel.onPhotoClicked(R.id.img_user2_avatar)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {

                when (viewModel.selectedUser.value) {
                    R.id.img_user1_avatar -> {
                        viewModel.firstUserPhotoUpdate(result.uri)
                    }
                    R.id.img_user2_avatar -> {
                        viewModel.secondUserPhotoUpdate(result.uri)
                    }
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }


}
