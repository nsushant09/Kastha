package com.neupanesushant.kastha.data.remote

import com.neupanesushant.kastha.domain.model.Alignment
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.model.Review
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {

    // Alignment
    @get:GET("alignment/all")
    val alignments: List<Alignment>

    @GET("alignment/{alignment_id}")
    suspend fun getAlignmentOf(@Path("alignment_id") alignmentId: Int): Alignment


    // Cart
    @FormUrlEncoded
    @POST("cart")
    suspend fun addProductToCart(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int
    ): List<CartProduct>

    @DELETE("cart/{product_id}")
    suspend fun removeProductFromCart(@Path("product_id") cartProductId: Int): List<CartProduct>

    @GET("cart/{user_id}")
    suspend fun allCartProducts(@Path("user_id") userId: Int): List<CartProduct>

    // Favorite

    @FormUrlEncoded
    @POST("favorite")
    suspend fun addProductToFavorite(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int
    ): List<Product>

    @FormUrlEncoded
    @DELETE("favorite")
    suspend fun removeProductFromFavorite(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int
    ): List<Product>

    @GET("favorite/{user_id}")
    suspend fun allFavoriteProducts(@Path("user_id") userId: Int): List<Product>

    // Category
    @get:GET("category/all")
    val categories: List<Category>

    @GET("category/{category_id}")
    suspend fun getCategoryOf(@Path("category_id") alignmentId: Int): Category

    // Product
    @get:GET("product")
    val products: List<Product>

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
    suspend fun getReviewsOf(@Path("product_id") productId: Int): List<Review>

}