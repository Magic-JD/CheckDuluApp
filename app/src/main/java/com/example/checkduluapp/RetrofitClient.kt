package com.example.checkduluapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
    @GET("info/mobile")
    fun getInformationMobile(
        @Query("barcode") barcode: String,
        @Query("amount") amount: Double?
    ): Call<InformationResponse>
}

data class InformationResponse(
    val productName: String,
    val sugarPerGivenAmount: Double
)

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}