package com.neupanesushant.kastha.ui.fragment.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentAddProductBinding
import com.neupanesushant.kastha.databinding.ItemImageBinding
import com.neupanesushant.kastha.extra.Utils
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import java.io.File

class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_add_product

    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnSelectImages.setOnClickListener { chooseImage() }
        binding.btnSelectModel.setOnClickListener { chooseModel() }
    }

    override fun setupObserver() {
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        imageSelectorRequestLauncher.launch(Intent.createChooser(intent, "Select images"))
    }

    private val imageSelectorRequestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult
        val selectedImages: ArrayList<Uri> = arrayListOf()

        data.clipData?.let { clipData ->
            for (index in 0 until clipData.itemCount) {
                selectedImages.add(clipData.getItemAt(index).uri)
//                Utils.uriToFile(
//                    requireContext(),
//                    clipData.getItemAt(index).uri
//                )?.let {
//                    selectedImages.add(
//                        it
//                    )
//                }
            }
        }

        binding.rvSelectedImages.adapter = getImageAdapter(selectedImages)
    }

    private fun chooseModel() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "model/gltf-binary"
        modelSelectorRequestLauncher.launch(Intent.createChooser(intent, "Select model"))
    }

    private val modelSelectorRequestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        val data = result.data?.clipData?.getItemAt(0) ?: return@registerForActivityResult
    }

    private fun getImageAdapter(files: List<Uri>) =
        RVAdapter<Uri, ItemImageBinding>(
            R.layout.item_image,
            files
        ) { mBinding, data, _ ->
            Glide.with(mBinding.root.context)
                .load(data)
                .into(mBinding.ivProductImage)
        }
}