package com.neupanesushant.kastha.ui.fragment.main

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentProductDetailBinding
import com.neupanesushant.kastha.databinding.ItemHomeCarouselBinding
import com.neupanesushant.kastha.databinding.ItemReviewBinding
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.model.ReviewResponse
import com.neupanesushant.kastha.ui.adapter.ProductHorizontalCardAdapter
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import com.neupanesushant.kastha.viewmodel.CartViewModel
import com.neupanesushant.kastha.viewmodel.FavouriteViewModel
import com.neupanesushant.kastha.viewmodel.ProductViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    companion object {
        const val PRODUCT_ARGUMENT = "PRODUCT_ARGUMENT"
    }

    override val layoutId: Int
        get() = R.layout.fragment_product_detail

    private lateinit var product: Product
    private val productViewModel: ProductViewModel by sharedViewModel()
    private val cartViewModel: CartViewModel by sharedViewModel()
    private val favouriteViewModel: FavouriteViewModel by sharedViewModel()

    private val reviews: List<ReviewResponse> by inject(named("test_reviews"))

    override fun initialize() {
        try {
            product = arguments?.getParcelable<Product>(PRODUCT_ARGUMENT)!!
        } catch (_: Exception) {
            requireActivity().finish()
        }
    }

    override fun setupViews() {
        setupCarouselView()
        binding.apply {
            tvProductTitle.text = product.name
            tvProductPrice.text = String.format("Rs.%.2f", product.price)
            tvCategoryTitle.text = product.category.name
            tvDescriptionContent.text = product.description

            layoutStockContainers.cvArFeatured.isVisible = product.model != null
            rvReviews.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun setupEventListener() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.btnAugmentedView.setOnClickListener {
            if (product.model == null) return@setOnClickListener
            requestCameraPermission()
        }
        binding.btnAddToCart.setOnClickListener {
            cartViewModel.addProductToCart(product.id)
        }
        binding.btnFavourites.setOnClickListener {
            favouriteViewModel.addToFavourite(product.id)
        }
    }

    override fun setupObserver() {
        productViewModel.allProduct.observe(viewLifecycleOwner) {
            setupSearchView()
        }
        setupReviews(reviews)
    }

    private fun setupCarouselView() {
        binding.rvProductImages.apply {
            val snapHelper = CarouselSnapHelper()
            snapHelper.attachToRecyclerView(this)
            val carouselLayoutManager = CarouselLayoutManager()
            carouselLayoutManager.carouselAlignment = CarouselLayoutManager.ALIGNMENT_START
            layoutManager = carouselLayoutManager
        }
        setProductImagesData(product.images.map { it.url })
    }

    private fun setProductImagesData(imageUrls: List<String>) {
        val adapter = RVAdapter<String, ItemHomeCarouselBinding>(
            R.layout.item_home_carousel,
            imageUrls
        ) { mBinding, data, _ ->
            GlideManager.load(requireContext(), data, mBinding.carouselImageView)
        }
        binding.rvProductImages.adapter = adapter
    }

    private fun setupReviews(reviews: List<ReviewResponse>) {
        binding.rvReviews.adapter = getReviewAdapter(reviews)
    }

    private fun getReviewAdapter(reviews: List<ReviewResponse>) =
        RVAdapter<ReviewResponse, ItemReviewBinding>(
            R.layout.item_review,
            reviews
        ) { mBinding, data, datas ->
            mBinding.tvReviewUsername.text = data.userName ?: "Unknown"
            mBinding.tvReviewDate.text = data.date.toString()
            mBinding.tvReviewDescription.text = data.description
            for (i in 1..data.rating) {
                mBinding.llStartContainer.addView(createStarImageView(requireContext()))
            }
        }

    private fun createStarImageView(context: Context): ImageView {
        val imageView = ImageView(context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            context.resources.getDimensionPixelSize(R.dimen.dp_12),
            context.resources.getDimensionPixelSize(R.dimen.dp_12)
        )
        imageView.setImageResource(R.drawable.ic_star_filled)
        imageView.contentDescription = context.getString(R.string.image_view)
        imageView.visibility = View.VISIBLE
        imageView.setPadding(0, 0, context.resources.getDimensionPixelSize(R.dimen.dp_2), 0)
        val typedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        val color = typedValue.data

        imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        return imageView
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            onCameraPermissionGranted()
        } else {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onCameraPermissionGranted()
            }
        }

    private fun onCameraPermissionGranted() {
        product.model?.let {
            RouteHelper.routeAugmentedView(requireActivity(), it)
        }
    }

    private fun setupSearchView() {
        binding.layoutSearchView.apply {
            searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    val searchResults = productViewModel.getSearchResults(textView.text.toString())
                    setSearchViewContentVisibility(searchResults.isNotEmpty())
                    setupSearchedProducts(searchResults)
                }
                false
            }
        }
    }

    private fun setSearchViewContentVisibility(isProductsAvailable: Boolean) {
        binding.layoutSearchView.apply {
            rvSearchView.isVisible = isProductsAvailable
            llInvalidContainer.isVisible = !isProductsAvailable
        }
    }

    private fun setupSearchedProducts(products: List<Product>) {
        binding.layoutSearchView.apply {
            rvSearchView.layoutManager = LinearLayoutManager(requireContext())
            rvSearchView.adapter = ProductHorizontalCardAdapter(requireActivity(), products)
        }
    }
}