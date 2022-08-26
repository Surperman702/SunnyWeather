package com.sunnyweather.android.logic.model

/**
 * 2.1.3定义一个Weather类，用于将Realtime和Daily对象封装起来
 */

data class Weather(val realtime: RealtimeResponse.RealTime, val daily: DailyResponse.Daily)