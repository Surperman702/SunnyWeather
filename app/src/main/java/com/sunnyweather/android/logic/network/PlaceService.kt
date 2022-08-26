package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 1.2.开始编写网络层相关的代码了。首先定义一个用于访问彩云天气城市搜索API的Retrofit接口
 */

interface PlaceService {

    /**
     * 定义一个用于访问彩云天气城市搜索API的Retrofit接口
     * 这样当调用searchPlaces()方法的时候，Retrofit就会自动发起一条GET请求，去访问@GET注解中配置的地址。
     * 其中，搜索城市数据的API中只有query这个参数是需要动态指定的，我们使用@Query注解的方式来进行实现，另外两个参数是不会变的，因此固定写在@GET注解中即可
     */
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}