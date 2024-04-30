package com.neupanesushant.kastha.data.remote

import com.neupanesushant.kastha.domain.model.Alignment
import com.neupanesushant.kastha.domain.model.CartProduct
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.model.Review
import com.neupanesushant.kastha.domain.model.ReviewResponse
import com.neupanesushant.kastha.domain.model.User
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import com.neupanesushant.kastha.domain.model.dto.UserUpdateDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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
    ): Response<List<CartProduct>>

    @DELETE("cart")
    suspend fun removeProductFromCart(@Query("cart_product_ids") cartProductIds: List<Int>): Response<List<CartProduct>>

    @GET("cart/{user_id}")
    suspend fun allCartProducts(@Path("user_id") userId: Int): Response<List<CartProduct>>

    @POST("cart/increment/{cart_product_id}")
    suspend fun increment(@Path("cart_product_id") cartProductId: Int): Response<CartProduct>

    @POST("cart/decrement/{cart_product_id}")
    suspend fun decrement(@Path("cart_product_id") cartProductId: Int): Response<CartProduct>

    // Favorite

    @FormUrlEncoded
    @POST("favorite")
    suspend fun addProductToFavorite(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int
    ): Response<List<Product>>

    @DELETE("favorite")
    suspend fun removeProductFromFavorite(
        @Query("product_ids") productIds: List<Int>,
        @Query("user_id") userId: Int
    ): Response<List<Product>>

    @GET("favorite/{user_id}")
    suspend fun allFavoriteProducts(@Path("user_id") userId: Int): Response<List<Product>>

    // Category
    @GET("category/all")
    suspend fun getCategories(): Response<List<Category>>

    @GET("category/{category_id}")
    suspend fun getCategoryOf(@Path("category_id") categoryId: Int): Category

    // Product

    @POST("product")
    suspend fun addProduct(@Body product: Product): Response<Product>

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

    @GET("product/recommended")
    suspend fun getProductByRecommended(@Query("category_ids") categoryIds: List<Int>): Response<List<Product>>

    // Object Model
    // Image
    // Review
    @POST("review/{product_id}/{user_id}")
    suspend fun addReview(
        @Path("product_id") productId: Int,
        @Path("user_id") userId: Int,
        @Body review: Review
    ): Response<ReviewResponse>

    @GET("review/{product_id}")
    suspend fun getReviewsOf(@Path("product_id") productId: Int): Response<List<ReviewResponse>>

    @GET("user/{user_id}")
    suspend fun getUserDetail(@Path("user_id") userId: Int): Response<User>

    @PATCH("user")
    suspend fun updateUserDetail(@Body user: UserUpdateDTO): Response<User>

    @Multipart
    @POST("image/multiple")
    suspend fun uploadImages(
        @Part files: List<MultipartBody.Part>
    ): Response<BaseResponse<List<String>>>

    @Multipart
    @POST("model")
    suspend fun uploadModel(
        @Part file: MultipartBody.Part
    ): Response<BaseResponse<String>>


    // Chat
    @GET("user/latest-chat")
    suspend fun getChatUsers(
        @Query("user_ids") userIds: List<Int>
    ): Response<List<User>>
}