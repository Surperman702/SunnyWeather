package com.sunnyweather.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

/**
 * 1.7.编写布局文件
 * 1.8.为RecyclerView准备适配器。让这个适配器继承自RecyclerView.Adapter，并将泛型指定为PlaceAdapter.ViewHolder
 */

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    /**
     * 4.4.存储选中的城市。这里需要进行两处修改:
     * 4.4.1先把PlaceAdapter主构造函数中传入的Fragment对象改成PlaceFragment对象，这样我们就可以调用PlaceFragment所对应的PlaceViewModel了
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)

        /**
         * 2.9.从搜索城市界面跳转到天气界面
         *
         * 给place_item.xml的最外层布局注册了一个点击事件监听器，
         * 然后在点击事件中获取当前点击项的经纬度坐标和地区名称，并把它们传入Intent中，
         * 最后调用Fragment的startActivity()方法启动WeatherActivity
         */
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]

            /**
             * 6.3.需要根据PlaceFragment所处的Activity来进行不同的逻辑处理
             */
            val activity = fragment.activity
            // 如果是在WeatherActivity中，那么就关闭滑动菜单，
            // 给WeatherViewModel赋值新的经纬度坐标和地区名称，然后刷新城市的天气信息
            if (activity is WeatherActivity) {
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                fragment.startActivity(intent)
                fragment.activity?.finish()
            }

            /**
             * 4.4.2接着在onCreateViewHolder()方法中，当点击了任何子项布局时，在跳转到WeatherActivity之前，
             * 先调用PlaceViewModel的savePlace()方法来存储选中的城市
             */
            fragment.viewModel.savePlace(place)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount() = placeList.size

}