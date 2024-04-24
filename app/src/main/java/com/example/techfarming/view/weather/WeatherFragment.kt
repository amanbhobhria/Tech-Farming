package com.example.techfarming.view.weather

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techfarming.R
import com.example.techfarming.adapter.CurrentWeatherAdapter
import com.example.techfarming.adapter.WeatherAdapter
import com.example.techfarming.model.data.WeatherList
import com.example.techfarming.viewmodel.WeatherListener
import com.example.techfarming.viewmodel.WeatherViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class WeatherFragment : Fragment(), WeatherListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var Adapter: WeatherAdapter
    lateinit var Adapter2: CurrentWeatherAdapter

    private lateinit var viewModel: WeatherViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        // init ViewModel
        viewModel = ViewModelProvider(requireActivity())
            .get<WeatherViewModel>(WeatherViewModel::class.java)

        val bundle = this.arguments
        if (bundle != null) {
            Log.d("WeatherFrag Bundle", bundle.getString("key").toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        // Find views by ID




        return view
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Weather Forecast"

        val city = viewModel.getCoordinates().value
        val weatherCity = view.findViewById<TextView>(R.id.weatherCity)

        weatherCity.text = city!!.get(2).toString()
        val newWeatherData = viewModel.newDataTrial.value
        Log.d("New Data Weather Trial", newWeatherData.toString())

        var firstDate = newWeatherData!!.list[0].dt_txt.slice(8..9)
        var otherDates = firstDate
        var i = 1
        var data2 = mutableListOf<WeatherList>()

        while (otherDates == firstDate) {
            data2!!.add(newWeatherData.list[i - 1])
            otherDates = newWeatherData.list[i].dt_txt.slice(8..9)
            i += 1
        }

        var data3 = mutableListOf<WeatherList>()
        for (a in i - 1..39) {
            if (newWeatherData.list[a].dt_txt.slice(11..12) == "12") {
                Log.d("Something date", newWeatherData.list[a].dt_txt)
                data3.add(newWeatherData.list[a])
            }
        }

        Adapter = WeatherAdapter(requireActivity().applicationContext, data3)
        Adapter2 = CurrentWeatherAdapter(requireActivity().applicationContext, data2)


        val rcylr_weather = view.findViewById<RecyclerView>(R.id.rcylr_weather)
        val currentWeather_rcycl = view.findViewById<RecyclerView>(R.id.currentWeather_rcycl)


        rcylr_weather.adapter = Adapter
        rcylr_weather.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        currentWeather_rcycl.adapter = Adapter2
        currentWeather_rcycl.layoutManager = LinearLayoutManager(requireActivity().applicationContext, LinearLayoutManager.HORIZONTAL, false)

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSuccess(authRepo: LiveData<String>) {
        authRepo.observe(this, Observer {
            Log.d("Frag", authRepo.value.toString())
        })
//        Toast.makeText(this.context, "SS", Toast.LENGTH_LONG).show()
    }
}

private fun RecyclerView.hasFixedSize(b: Boolean) {

}