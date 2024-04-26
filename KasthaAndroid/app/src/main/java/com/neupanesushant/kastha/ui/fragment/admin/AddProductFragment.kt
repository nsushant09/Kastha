package com.neupanesushant.kastha.ui.fragment.admin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentAddProductBinding
import com.neupanesushant.kastha.databinding.ItemImageBinding
import com.neupanesushant.kastha.extra.Utils.toMultipart
import com.neupanesushant.kastha.extra.helper.Validator
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import com.neupanesushant.kastha.viewmodel.CategoryViewModel
import com.neupanesushant.kastha.viewmodel.ProductCRUDViewModel
import okhttp3.MultipartBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_add_product

    private val productCRUDViewModel: ProductCRUDViewModel by viewModel()
    private val categoryViewModel: CategoryViewModel by viewModel()
    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnSelectImages.setOnClickListener { chooseImage() }
        binding.btnSelectModel.setOnClickListener { chooseModel() }
        binding.btnAddProduct.setOnClickListener {
            addProduct()
        }
    }

    override fun setupObserver() {
        categoryViewModel.categories.observe(viewLifecycleOwner) {
            setupCategoryAutoCompleteView(it.map { it.name }.toTypedArray())
        }
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

        val selectedImagesUri: ArrayList<Uri> = arrayListOf()
        val selectedImages: ArrayList<MultipartBody.Part> = arrayListOf()

        data.clipData?.let { clipData ->
            for (index in 0 until clipData.itemCount) {
                val uri = clipData.getItemAt(index).uri

                val bitmap: Bitmap =
                    BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(uri))

                val multipart = bitmap.toMultipart()

                selectedImagesUri.add(uri)
                selectedImages.add(multipart)
                productCRUDViewModel.setProductImages(selectedImages)
            }
        }

        binding.rvSelectedImages.adapter = getImageAdapter(selectedImagesUri)
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

    private fun setupCategoryAutoCompleteView(options: Array<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
        binding.etCategory.setAdapter(adapter)
    }

    private fun addProduct() {
        productCRUDViewModel.addImages()
    }

    private fun areValidDetails(
        name: String,
        category: String,
        price: String,
        stock: String,
        description: String,
    ): Boolean {
        // Clear any previous errors
        binding.apply {
            tilName.error = null
            tilCategory.error = null
            tilPrice.error = null
            tilStock.error = null
            tilDescription.error = null
        }

        var allValid = true

        val nameValidation = Validator.generalString(name)
        if (!nameValidation.first) {
            binding.tilName.error = nameValidation.second
            allValid = false
        }

        val categoryValidation = Validator.generalString(category)
        if (!categoryValidation.first) {
            binding.tilCategory.error = categoryValidation.second
            allValid = false
        }

        val priceValidation = Validator.numeric(price)
        if (!priceValidation.first) {
            binding.tilPrice.error = priceValidation.second
            allValid = false
        }

        val stockValidation = Validator.numeric(stock)
        if (!stockValidation.first) {
            binding.tilStock.error = stockValidation.second
            allValid = false
        }

        return allValid
    }
}