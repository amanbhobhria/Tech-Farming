package com.example.techfarming.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techfarming.adapter.WeatherAdapter
import com.example.techfarming.model.WeatherApi
import com.example.techfarming.model.WeatherRepository
import com.example.techfarming.model.data.WeatherRootList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeatherViewModel : ViewModel() {
    lateinit var Adapter: WeatherAdapter
    lateinit var rootData: MutableLiveData<WeatherRootList>
    var weatherListener: WeatherListener? = null

    private var message1 = MutableLiveData<WeatherRootList>()
    private var message2 = MutableLiveData<WeatherRootList>()

    private var coordinates = MutableLiveData<List<String>>()

    val _rootData1 = MutableLiveData<WeatherRootList>()
    val rootData2: LiveData<WeatherRootList> = _rootData1


    var newDataTrial = MutableLiveData<WeatherRootList>()


    fun messageToB(msg: WeatherRootList){
        message2.value = msg

    }

    fun messageToA(msg: WeatherRootList){
        message1.value = msg
    }

    fun getMessageA(): MutableLiveData<WeatherRootList> {
        return message1
    }

    fun getMessageB(): MutableLiveData<WeatherRootList>{
        return message2
    }

    fun callWeatherRepository() {
        val response = WeatherRepository().getWeather()
//        val ss = response.value!!.list[0].dt_txt
//        Log.d("ViewModel", ss)
        weatherListener?.onSuccess(response)
    }

    fun updateCoordinates(coords: List<String>){
        coordinates.value = coords
        Log.d("WeatherView", coordinates.value.toString())
    }

    fun getCoordinates(): MutableLiveData<List<String>> {
        return coordinates
    }

    fun updateNewData(){

        val response: Call<WeatherRootList> =
            WeatherApi.weatherInstances.getWeather("${coordinates.value?.get(0)}", "${coordinates.value?.get(1)}")

        Log.d("New Weather Updated", "Yes")
        Log.d("Weather Lat", "${coordinates.value?.get(0)}")
        Log.d("Weather Long", "${coordinates.value?.get(1)}")
        Log.d("Weather City", "${coordinates.value?.get(2)}")

        if (newDataTrial.value == null) {
            response.enqueue(object : Callback<WeatherRootList> {
                override fun onFailure(call: Call<WeatherRootList>, t: Throwable) {
                    Log.d("WeatherRepository", "Error Occured")
                }

                override fun onResponse(
                    call: Call<WeatherRootList>,
                    response: Response<WeatherRootList>
                ) {
                    if (response.isSuccessful) {
                        newDataTrial.value = response.body()
                    }
                }
            }
            )
        }
    }
}