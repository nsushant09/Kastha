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
    @get:GET("/alignment/all")
    val alignments: List<Alignment>

    @GET("/alignment/{alignment_id}")
    fun getAlignmentOf(@Path("alignment_id") alignmentId: Int): Alignment


    // Cart
    @FormUrlEncoded
    @POST("/cart")
    fun addProductToCart(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int
    ): List<CartProduct>

    @DELETE("/cart/{product_id}")
    fun removeProductFromCart(@Path("product_id") cartProductId: Int): List<CartProduct>

    @GET("/cart/{user_id}")
    fun allCartProducts(@Path("user_id") userId: Int): List<CartProduct>

    // Favorite

    @FormUrlEncoded
    @POST("/favorite")
    fun addProductToFavorite(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int
    ): List<Product>

    @FormUrlEncoded
    @DELETE("/favorite")
    fun removeProductFromFavorite(
        @Field("product_id") cartProductId: Int,
        @Field("user_id") userId: Int
    ): List<Product>

    @GET("/favorite/{user_id}")
    fun allFavoriteProducts(@Path("user_id") userId: Int): List<Product>

    // Category
    @get:GET("/category/all")
    val categories: List<Category>

    @GET("/category/{category_id}")
    fun getCategoryOf(@Path("category_id") alignmentId: Int): Alignment

    // Product
    @get:GET("/product")
    val products: List<Product>

    @GET("/product/id/{product_id}")
    fun getProductById(@Path("product_id") productId: Int): Product

    @GET("/product/category/{category_id}")
    fun getProductsByCategory(@Path("category_id") categoryId: Int): List<Product>

    @FormUrlEncoded
    @GET("/product/search")
    fun getProductBySearch(@Field("value") value: String): List<Product>


    // Object Model
    // Image
    // Review
    @FormUrlEncoded
    @POST("/review")
    fun addReview(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int,
        @Body review: Review
    ): Review

    @GET("/review/{product_id}")
    fun getReviewsOf(@Path("product_id") productId: Int): List<Review>

}