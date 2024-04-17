package com.neupanesushant.kastha.data.remote

import com.neupanesushant.kastha.domain.model.Alignment
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.model.Review
import com.neupanesushant.kastha.domain.model.ReviewResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    // Alignment
    @GET("alignment/all")
    suspend fun getAlignments(): List<Alignment>

    @GET("alignment/{alignment_id}")
    suspend fun getAlignmentOf(@Path("alignment_id") alignmentId: Int): Alignment


    // Cart
    @FormUrlEncoded
    @POST("cart")
    suspend fun addProductToCart(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int
    ): CartProduct

    @DELETE("cart/{product_id}")
    suspend fun removeProductFromCart(@Path("product_ids") cartProductIds: List<Int>)

    @GET("cart/{user_id}")
    suspend fun allCartProducts(@Path("user_id") userId: Int): List<CartProduct>

    @POST("cart/increment/{cart_product_id}")
    suspend fun increment(@Path("cart_product_id") cartProductId: Int): CartProduct

    @POST("cart/increment/{cart_product_id}")
    suspend fun decrement(@Path("cart_product_id") cartProductId: Int): CartProduct

    // Favorite

    @FormUrlEncoded
    @POST("favorite")
    suspend fun addProductToFavorite(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int
    ): List<Product>

    @DELETE("favorite")
    suspend fun removeProductFromFavorite(
        @Query("product_ids") productIds: List<Int>,
        @Query("user_id") userId: Int
    ): List<Product>

    @GET("favorite/{user_id}")
    suspend fun allFavoriteProducts(@Path("user_id") userId: Int): List<Product>

    // Category
    @GET("category/all")
    suspend fun getCategories(): List<Category>

    @GET("category/{category_id}")
    suspend fun getCategoryOf(@Path("category_id") categoryId: Int): Category

    // Product

    @POST("product")
    suspend fun addProduct(@Body product: Product): Product

    @PUT("product")
    suspend fun updateProduct(@Body product: Product): Product

    @GET("product")
    suspend fun getProducts(): List<Product>

    @GET("product/id/{product_id}")
    suspend fun getProductById(@Path("product_id") productId: Int): Product

    @GET("product/category/{category_id}")
    suspend fun getProductsByCategory(@Path("category_id") categoryId: Int): List<Product>

    @FormUrlEncoded
    @GET("product/search")
    suspend fun getProductBySearch(@Field("value") value: String): List<Product>

    // Object Model
    // Image
    // Review
    @FormUrlEncoded
    @POST("review")
    suspend fun addReview(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int,
        @Body review: Review
    ): Review

    @GET("review/{product_id}")
    suspend fun getReviewsOf(@Path("product_id") productId: Int): List<ReviewResponse>

}