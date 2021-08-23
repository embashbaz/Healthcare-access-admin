package com.example.yourmedadmin.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


    private val BASE_URL = "https://clinicaltables.nlm.nih.gov/api/rxterms/v3/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    interface RxtermApiService{

        @GET("search")
        fun getTerms(@Query("terms")term: String): Call<String>

    }

    object RxtermApi{
        val retrofitService : RxtermApiService by lazy {
            retrofit.create(RxtermApiService::class.java) }
    }

