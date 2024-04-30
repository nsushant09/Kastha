package com.neupanesushant.kastha.ui.fragment.admin

import android.annotation.SuppressLint
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
import com.neupanesushant.kastha.core.StateResolver
import com.neupanesushant.kastha.databinding.FragmentAddProductBinding
import com.neupanesushant.kastha.databinding.ItemImageBinding
import com.neupanesushant.kastha.extra.helper.Validator
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import com.neupanesushant.kastha.ui.dialog.DialogUtils
import com.neupanesushant.kastha.viewmodel.CategoryViewModel
import com.neupanesushant.kastha.viewmodel.ProductCRUDViewModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_add_product

    private val productCRUDViewModel: ProductCRUDViewModel by viewModel()
    private val categoryViewModel: CategoryViewModel by viewModel()
    override fun setupViews() {
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
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

        productCRUDViewModel.addProductState.observe(viewLifecycleOwner) {
            StateResolver(
                it,
                onLoading = { showLoading() },
                onError = {
                    hideLoading()
                    DialogUtils.generalDialog(requireContext(), it, "Error!!!")
                },
                onSuccess = { hideLoading() })()
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
        val selectedImages: ArrayList<Bitmap> = arrayListOf()

        data.clipData?.let { clipData ->
            for (index in 0 until clipData.itemCount) {
                val uri = clipData.getItemAt(index).uri

                val bitmap: Bitmap =
                    BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(uri))

                selectedImagesUri.add(uri)
                selectedImages.add(bitmap)
                productCRUDViewModel.setProductImages(selectedImages)
            }
        }

        binding.rvSelectedImages.adapter = getImageAdapter(selectedImagesUri)
    }

    private fun chooseModel() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        modelSelectorRequestLauncher.launch(Intent.createChooser(intent, "Select model"))
    }

    @SuppressLint("SetTextI18n")
    private val modelSelectorRequestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult
        data.data?.let { uri ->
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            inputStream?.use {
                val byteArray = it.readBytes()
                val requestBody =
                    RequestBody.create(MediaType.get("model/gltf-binary"), byteArray)
                val part = MultipartBody.Part.createFormData(
                    "file",
                    UUID.nameUUIDFromBytes(byteArray).toString(),
                    requestBody
                )
                productCRUDViewModel.setProductModel(part)
                binding.btnSelectModel.text = "Object Model Added"
            }
        }
    }

    private fun getImageAdapter(files: List<Uri>) = RVAdapter<Uri, ItemImageBinding>(
        R.layout.item_image, files
    ) { mBinding, data, _ ->
        Glide.with(mBinding.root.context).load(data).into(mBinding.ivProductImage)
    }

    private fun setupCategoryAutoCompleteView(options: Array<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
        binding.etCategory.setAdapter(adapter)
    }

    private fun addProduct() {
        binding.apply {
            val name = binding.etName.text.toString()
            val category = binding.etCategory.text.toString()
            val price = binding.etPrice.text.toString()
            val stock = binding.etStock.text.toString()
            val description = binding.etDescription.text.toString()

            if (!areValidDetails(name, category, price, stock, description)) {
                return
            }

            val selectedCategory = categoryViewModel.categories.value?.find { it.name == category }
            if (selectedCategory == null) {
                binding.tilCategory.error = "Could not select this category"
                return
            }

            productCRUDViewModel.addProduct(
                name,
                description,
                price.toFloat(),
                stock.toInt(),
                selectedCategory
            )
        }
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
            btnSelectImages.error = null
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

        val descriptionValidation = Validator.generalString(description)
        if (!descriptionValidation.first) {
            binding.tilDescription.error = descriptionValidation.second
            allValid = false
        }

        if (productCRUDViewModel.productImages.value.isNullOrEmpty()) {
            binding.btnSelectImages.error = "Select at least one image"
            allValid = false
        }

        return allValid
    }
}