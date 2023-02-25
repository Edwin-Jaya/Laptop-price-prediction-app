package com.ariqh.laptoppriceprediction.retrofit

import com.ariqh.laptoppriceprediction.model.PredictResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiEndPoint {
    @FormUrlEncoded
    @POST("predict")
    fun storePredict(
        @Field("company") company : String,
        @Field("cpu") cpu : String,
        @Field("ram") ram : Int,
        @Field("gpu") gpu : String,
        @Field("opsys") opsys : String,
        @Field("weight") weight : Double,
        @Field("hdd") hdd : Int,
        @Field("ssd") ssd : Int,
    ) : Call<PredictResponse>
}