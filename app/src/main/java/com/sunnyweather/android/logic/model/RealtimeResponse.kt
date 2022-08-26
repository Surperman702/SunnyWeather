package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * 2.显示天气信息
 * 2.1.定义相应数据模型
 * 将所有的数据模型类都定义在了RealtimeResponse的内部，这样可以防止出现和其他接口的数据模型类有同名冲突的情况。
 * 2.1.1获取实时天气信息接口所返回的JSON数据格式，简化后的内容如下所示：
 * {
    "status": "ok",
    "result": {
        "realtime": {
            "temperature": 23.16,
            "skycon": "WIND",
            "air_quality": {
                "aqi": { "chn": 17.0 }
            }
        }
    }
}
 */

data class RealtimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: RealTime)

    data class RealTime(
        val skycon: String, val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}