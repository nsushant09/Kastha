package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
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
import com.google.android.material.color.MaterialColors
import com.neupanesushant.kastha.R
import com.neupanesushant.kastha.appcore.ArCore.ArInitializer
import com.neupanesushant.kastha.appcore.RouteHelper
import com.neupanesushant.kastha.core.BaseFragment
import com.neupanesushant.kastha.databinding.FragmentProductDetailBinding
import com.neupanesushant.kastha.databinding.ItemProductDetailCarouselBinding
import com.neupanesushant.kastha.databinding.ItemReviewBinding
import com.neupanesushant.kastha.domain.managers.GlideManager
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.model.ReviewResponse
import com.neupanesushant.kastha.extra.RecommendedDataManager
import com.neupanesushant.kastha.extra.extensions.Snackbar
import com.neupanesushant.kastha.extra.extensions.showKeyboard
import com.neupanesushant.kastha.ui.adapter.ProductHorizontalCardAdapter
import com.neupanesushant.kastha.ui.adapter.RVAdapter
import com.neupanesushant.kastha.ui.dialog.DialogUtils
import com.neupanesushant.kastha.viewmodel.CartViewModel
import com.neupanesushant.kastha.viewmodel.FavouriteViewModel
import com.neupanesushant.kastha.viewmodel.ProductViewModel
import com.neupanesushant.kastha.viewmodel.ReviewViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
    private val reviewViewModel: ReviewViewModel by sharedViewModel()

    private var reviewRating: Int = 0

    private val arInitializer: ArInitializer by lazy {
        ArInitializer(requireActivity())
    }


    override fun initialize() {
        try {
            product = arguments?.getParcelable<Product>(PRODUCT_ARGUMENT)!!
        } catch (_: Exception) {
            requireActivity().finish()
        }
        reviewViewModel.getProductReview(product.id)
        RecommendedDataManager.saveCategory(product.id)
    }

    override fun setupViews() {
        setupCarouselView()
        binding.apply {
            tvProductTitle.text = product.name
            tvProductPrice.text = String.format("Rs.%.2f", product.price)
            tvCategoryTitle.text = product.category.name
            tvDescriptionContent.text = product.description

            layoutStockContainers.cvArFeatured.isVisible = product.model != null

            if (product.stockQuantity == 0) {
                layoutStockContainers.cvOutOfStock.isVisible = true
            } else if (product.stockQuantity < 10) {
                layoutStockContainers.cvLowStock.isVisible = true
            }

            rvReviews.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setupEventListener() {
        setupStartEventListener()
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.btnAugmentedView.setOnClickListener {
            if (product.model == null) return@setOnClickListener
            if (arInitializer.isArAvailable()) {
                arInitializer.isArInstalled {
                    requestCameraPermission()
                }
            } else {
                DialogUtils.generalDialog(
                    requireContext(),
                    "Augmented reality features are not available on your device",
                    "Not Supported"
                )
            }
        }
        binding.btnAddToCart.setOnClickListener {
            cartViewModel.addProductToCart(product.id, onSuccess = {
                binding.root.Snackbar(
                    "Added to cart",
                    anchorView = binding.llBottomButtonsContainer
                )
            }, onFailure = {
                binding.root.Snackbar(
                    "$it. Could not add to cart",
                    anchorView = binding.llBottomButtonsContainer
                )
            })
        }
        binding.btnFavourites.setOnClickListener {
            favouriteViewModel.addToFavourite(product, onSuccess = {
                binding.root.Snackbar(
                    "Added to favourites",
                    anchorView = binding.llBottomButtonsContainer
                )
            }, onFailure = {
                binding.root.Snackbar(
                    "$it. Could not add to favourites",
                    anchorView = binding.llBottomButtonsContainer
                )
            })
        }

        binding.btnAddReview.setOnClickListener {

            if (binding.llProvideReviewContainer.isVisible) {
                binding.llProvideReviewContainer.isVisible = false
            } else {
                binding.llProvideReviewContainer.isVisible = true
                binding.btnAddReview.text = "Hide Review"
                binding.etDescription.requestFocus()
                requireContext().showKeyboard(binding.etDescription)
            }
        }

        binding.btnSubmitReview.setOnClickListener {
            submitReview()
        }
    }

    override fun setupObserver() {
        productViewModel.allProduct.observe(viewLifecycleOwner) {
            setupSearchView()
        }

        reviewViewModel.productReviews.observe(viewLifecycleOwner) { reviews ->
            binding.llReviewEmptyView.isVisible = reviews.isEmpty()
            setupReviews(reviews)
        }

        favouriteViewModel.favouriteProducts.observe(viewLifecycleOwner) { _ -> }

        cartViewModel.allProducts.observe(viewLifecycleOwner) { _ -> }
    }

    private fun setupCarouselView() {
        binding.rvProductImages.apply {
            val snapHelper = CarouselSnapHelper()
            if (binding.rvProductImages.adapter == null) {
                snapHelper.attachToRecyclerView(this)
            }
            val carouselLayoutManager = CarouselLayoutManager()
            carouselLayoutManager.carouselAlignment = CarouselLayoutManager.ALIGNMENT_START
            layoutManager = carouselLayoutManager
        }
        setProductImagesData(product.images.map { it.url })
    }

    private fun setProductImagesData(imageUrls: List<String>) {
        val adapter = RVAdapter<String, ItemProductDetailCarouselBinding>(
            R.layout.item_product_detail_carousel,
            imageUrls
        ) { mBinding, data, _ ->
            GlideManager.load(requireContext(), data, mBinding.carouselImageView, size = 600)
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
            RouteHelper.routeAugmentedView(requireActivity(), it, product.category.alignment)
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

    private fun onCartStatusChange(isInCart: Boolean) {
        hideLoading()
        binding.btnAddToCart.text = if (isInCart) "Remove from cart" else "Add to cart"
    }

    private fun onFavouritesStatusChange(isInFavourites: Boolean) {
        hideLoading()
        binding.btnFavourites.icon =
            ContextCompat.getDrawable(
                requireContext(),
                if (isInFavourites) R.drawable.ic_heart_filled else R.drawable.ic_heart
            )
    }


    @SuppressLint("SetTextI18n")
    private fun submitReview() {
        binding.tilDescription.error = null
        if (binding.etDescription.text.toString().isEmpty()) {
            binding.tilDescription.error = "Please write a review for the product"
        }
        reviewViewModel.addReview(product.id, reviewRating, binding.etDescription.text.toString()) {
            binding.llProvideReviewContainer.isVisible = false
            binding.btnAddReview.text = "Add Review"
        }
    }

    private fun setupStartEventListener() {
        binding.layoutStars.apply {
            star1.setOnClickListener(starOnClickListener)
            star2.setOnClickListener(starOnClickListener)
            star3.setOnClickListener(starOnClickListener)
            star4.setOnClickListener(starOnClickListener)
            star5.setOnClickListener(starOnClickListener)
        }
    }

    @SuppressLint("ResourceType")
    val starOnClickListener = View.OnClickListener { view ->
        reviewRating = when (view.id) {
            binding.layoutStars.star1.id -> 1
            binding.layoutStars.star2.id -> 2
            binding.layoutStars.star3.id -> 3
            binding.layoutStars.star4.id -> 4
            binding.layoutStars.star5.id -> 5
            else -> 0
        }
        val stars = listOf(
            binding.layoutStars.star1,
            binding.layoutStars.star2,
            binding.layoutStars.star3,
            binding.layoutStars.star4,
            binding.layoutStars.star5
        )

        stars.forEach {
            it.imageTintList = ColorStateList.valueOf(
                MaterialColors.getColor(
                    requireContext(),
                    com.google.android.material.R.attr.colorOnSurface,
                    null
                )
            )
        }

        for (i in 0 until reviewRating) {
            stars[i].imageTintList = ColorStateList.valueOf(
                MaterialColors.getColor(
                    requireContext(),
                    com.google.android.material.R.attr.colorPrimaryVariant,
                    ContextCompat.getColor(requireContext(), R.color.green_30)
                )
            )
        }
    }
}