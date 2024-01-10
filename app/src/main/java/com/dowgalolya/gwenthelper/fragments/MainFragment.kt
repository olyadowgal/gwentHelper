package com.dowgalolya.gwenthelper.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.dowgalolya.gwenthelper.R
import com.dowgalolya.gwenthelper.databinding.MainFragmentBinding
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.dowgalolya.gwenthelper.viewmodels.MainViewModel
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView


class MainFragment : BaseFragment(), View.OnClickListener {

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override val viewModel: MainViewModel by viewModels {
        SingletonHolder.viewModelFactory
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            //val uriContent = result.uriContent
            //val uriFilePath = context?.let { result.getUriFilePath(it) }
            when (viewModel.selectedUser.value) {
                R.id.img_user1_avatar -> {
                    result.uriContent?.let { viewModel.firstUserPhotoUpdate(it) }
                }

                R.id.img_user2_avatar -> {
                    result.uriContent?.let { viewModel.secondUserPhotoUpdate(it) }
                }
            }

        } else {
            // An error occurred.
            val exception = result.error
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        _binding?.btnPlay?.setOnClickListener(this)
        _binding?.imgScores?.setOnClickListener(this)
        _binding?.imgUser1Avatar?.setOnClickListener(this)
        _binding?.imgUser2Avatar?.setOnClickListener(this)

        viewModel.selectedUser.observe(viewLifecycleOwner) {
            context?.let {
                //here we will open the camera
                cropImage.launch(
                    CropImageContractOptions(
                        null,
                        CropImageOptions(
                            guidelines = CropImageView.Guidelines.ON,
                            cropShape = CropImageView.CropShape.RECTANGLE,
                            fixAspectRatio = true,
                            borderLineColor = resources.getColor(R.color.colorPrimary, null),
                            guidelinesColor = resources.getColor(R.color.oxford_blue, null),
                            autoZoomEnabled = true

                        )
                    )
                )
//                CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setCropShape(CropImageView.CropShape.RECTANGLE)
//                    .setFixAspectRatio(true)
//                    .setBorderLineColor(resources.getColor(R.color.colorPrimary, null))
//                    .setGuidelinesColor(resources.getColor(R.color.oxford_blue, null))
//                    .setAutoZoomEnabled(true)
//                    .start(it, this)
            }
        }

        viewModel.firstUserPhotoUri.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it)
                .transform(CircleCrop())
                .into(_binding?.imgUser1Avatar!!)

        }

        viewModel.secondUserPhotoUri.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it)
                .transform(CircleCrop())
                .into(_binding?.imgUser2Avatar!!)
        }

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_play -> {
                viewModel.onPlayClicked(
                    _binding?.fUser1Name?.text?.toString(),
                    _binding?.fUser2Name?.text?.toString()
                )
            }

            R.id.img_scores -> {
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


//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//
//            if (resultCode == RESULT_OK) {
//
//                when (viewModel.selectedUser.value) {
//                    R.id.img_user1_avatar -> {
//                        viewModel.firstUserPhotoUpdate(result.uri)
//                    }
//                    R.id.img_user2_avatar -> {
//                        viewModel.secondUserPhotoUpdate(result.uri)
//                    }
//                }
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                result.error
//            }
//        }
//    }


}
