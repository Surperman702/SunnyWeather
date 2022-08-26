package com.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

/**
 * 1.6.定义ViewModel层
 */

class PlaceViewModel : ViewModel() {

    /**
     * 现在每当searchPlaces()函数被调用时，switchMap()方法所对应的转换函数就会执行。
     * 然后在转换函数中，我们只需要调用仓库层中定义的searchPlaces()方法就可以发起网络请求，
     * 同时将仓库层返回的LiveData对象转换成一个可供Activity观察的LiveData对象。
     */

    private val searchLiveData = MutableLiveData<String>()

    /**
     * 定义了一个placeList集合，用于对界面上显示的城市数据进行缓存，
     * 因为原则上与界面相关的数据都应该放到ViewModel中，这样可以保证它们在手机屏幕发生旋转的时候不会丢失
     */
    val placeList = ArrayList<Place>()

    /**
     * 1.6.2转换函数
     * 并使用Transformations的switchMap()方法来观察这个对象，否则仓库层返回的LiveData对象将无法进行观察
     */
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    /**
     * 1.6.1首先PlaceViewModel中也定义了一个searchPlaces()方法，
     * 但是这里并没有直接调用仓库层中的searchPlaces()方法，而是将传入的搜索参数赋值给了一个searchLiveData对象
     */
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    /**
     * 4.3.PlaceDao这几个接口的业务逻辑是和PlaceViewModel相关的，因此我们还得在PlaceViewModel中再进行一层封装才行
     *
     * 由于仓库层中这几个接口的内部没有开启线程，因此也不必借助LiveData对象来观察数据变化，直接调用仓库层中相应的接口并返回即可
     */
    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}