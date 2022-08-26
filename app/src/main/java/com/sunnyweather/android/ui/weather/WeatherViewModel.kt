package com.sunnyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Location

/**
 * 2.5.定义ViewModel层
 */

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    /**
     * 定义了locationLng、locationLat和placeName这3个变量，它们都是和界面相关的数据，
     * 放到ViewModel中可以保证它们在手机屏幕发生旋转的时候不会丢失
     */

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    /**
     * 2.5.2然后使用Transformations的switchMap()方法来观察这个对象，并在switchMap()方法的转换函数中调用仓库层中定义的refreshWeather()方法。
     * 这样，仓库层返回的LiveData对象就可以转换成一个可供Activity观察的LiveData对象了
     */
    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    /**
     * 2.5.1定义了一个refreshWeather()方法来刷新天气信息，并将传入的经纬度参数封装成一个Location对象后赋值给locationLiveData对象
     */
    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

}