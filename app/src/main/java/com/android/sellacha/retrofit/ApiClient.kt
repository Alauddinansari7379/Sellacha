package com.example.myrecyview.apiclient

import com.example.ehcf.retrofit.ApiInterface
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
  //  private const val BASE_URL = "https://ehcf.thedemostore.in/api/customer/"
  private const val BASE_URL = "http://thedemostore.in/api/"

    //const val Auth_key = "$2a$08$4BsgX5lRtC5/fZar6OBSf.zRDr.HpYenJ5yR8.gov4VSM/7dIIPle"
    private var retrofit: Retrofit? = null
    private val client: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val apiClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build()
            val gson = GsonBuilder().setLenient().create()
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(apiClient)
                    .build()
            }
            return retrofit
        }
    val apiService: ApiInterface
        get() = client!!.create(ApiInterface::class.java)

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS).build()

}
