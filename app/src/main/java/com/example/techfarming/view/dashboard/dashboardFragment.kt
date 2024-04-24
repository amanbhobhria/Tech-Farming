package com.example.techfarming.view.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techfarming.R
import com.example.techfarming.model.data.WeatherRootList
import com.example.techfarming.utilities.CellClickListener
import com.example.techfarming.view.articles.ArticleListFragment
import com.example.techfarming.view.articles.FruitsFragment
import com.example.techfarming.view.articles.WebViewFragment
import com.example.techfarming.view.ecommerce.EcommerceItemFragment
import com.example.techfarming.view.weather.WeatherFragment
import com.example.techfarming.view.yojna.YojnaListFragment
import com.example.techfarming.viewmodel.WeatherViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [dashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class dashboardFragment : Fragment(), CellClickListener , SensorEventListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var weatherFragment: WeatherFragment
    lateinit var webViewFragment: WebViewFragment
    lateinit var fruitsFragment: FruitsFragment
    lateinit var yojnaListFragment: YojnaListFragment
    lateinit var articleListFragment: ArticleListFragment
    private lateinit var viewModel: WeatherViewModel
//    private lateinit var viewModel2: EcommViewModel
    var data: WeatherRootList? = null


    private lateinit var weathTempTextWeathFrag: TextView
    private lateinit var humidityTextWeathFrag: TextView
    private lateinit var windTextWeathFrag: TextView
    private lateinit var weatherCityTitle: TextView
    private lateinit var tempTXt: TextView
    private lateinit var weathIconImageWeathFrag: ImageView
    private lateinit var schemeImg: ImageView
    private lateinit var dashboardEcommRecycler: RecyclerView
    private lateinit var weatherCard: CardView
    private lateinit var cat2: CardView
    private lateinit var cat3: CardView
    private lateinit var cat4: CardView
    private lateinit var cat5: CardView
    private val msg=StringBuilder()
    private lateinit var mgr: SensorManager
    private  var temp: Sensor?=null




    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        viewModel = ViewModelProvider(requireActivity())
            .get<WeatherViewModel>(WeatherViewModel::class.java)

//        viewModel2 = ViewModelProvider(requireActivity())
//            .get<EcommViewModel>(EcommViewModel::class.java)


//        viewModel2.loadAllEcommItems()
    }


    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        viewModel.getCoordinates().observe(viewLifecycleOwner, Observer {
            Log.d("DashFrag", it.toString())
            viewModel.updateNewData()
            val city = it.get(2) as String
            viewModel.newDataTrial.observe(viewLifecycleOwner, Observer {

                Log.d("Observed Here", "Yes")
                weathTempTextWeathFrag.text =
                    (it.list[0].main.temp - 273).toInt().toString() + "\u2103"
                humidityTextWeathFrag.text =
                    "Humidity: " + it!!.list[0].main.humidity.toString() + " %"
                windTextWeathFrag.text = "Wind: " + it!!.list[0].wind.speed.toString() + " km/hr"
                weatherCityTitle.text = city.toString()
                var iconcode = it!!.list[0].weather[0].icon
                var iconurl = "https://openweathermap.org/img/w/" + iconcode + ".png";
                Glide.with(requireActivity().applicationContext).load(iconurl)
                    .into(weathIconImageWeathFrag)
            })
        })


//        viewModel2.ecommLiveData.observe(viewLifecycleOwner, Observer {
//            var itemsToShow = (0..it.size - 1).shuffled().take(4) as List<Int>
//            val adapterEcomm =
//                DashboardEcomItemAdapter(requireActivity().applicationContext, it, itemsToShow, this)
//            dashboardEcommRecycler.adapter = adapterEcomm
//            dashboardEcommRecycler.layoutManager =
//                GridLayoutManager(requireActivity().applicationContext, 2)
//        })

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)


        // Find views by ID

        weathTempTextWeathFrag = view.findViewById(R.id.weathTempTextWeathFrag)
        humidityTextWeathFrag = view.findViewById(R.id.humidityTextWeathFrag)
        windTextWeathFrag = view.findViewById(R.id.windTextWeathFrag)
        weatherCityTitle = view.findViewById(R.id.weatherCityTitle)
        tempTXt = view.findViewById(R.id.tempTXt)
        weathIconImageWeathFrag = view.findViewById(R.id.weathIconImageWeathFrag)
        dashboardEcommRecycler = view.findViewById(R.id.dashboardEcommRecycler)
        weatherCard = view.findViewById(R.id.weatherCard)
        cat2 = view.findViewById(R.id.cat2)
        cat3 = view.findViewById(R.id.cat3)
        cat4 = view.findViewById(R.id.cat4)
        cat5 = view.findViewById(R.id.cat5)
        schemeImg = view.findViewById(R.id.schemeImg)

        mgr=requireActivity().getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        temp = mgr.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)


        // Find the button using the inflated view
        val enableBtn: Button = view.findViewById(R.id.enBtn)
        val enBltBtn: Button = view.findViewById(R.id.enBltBtn)

        // Set an onClickListener on the button
        enableBtn.setOnClickListener {
            enableWifi()
        }

        val mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter()

        enBltBtn.setOnClickListener {
            // Check if Bluetooth is enabled
            if (mBluetoothAdapter.isEnabled) {
                // Check for BLUETOOTH_CONNECT permission
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request the missing permission
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                        1001
                    )
                    return@setOnClickListener
                }
                // Disable Bluetooth
                mBluetoothAdapter.disable()
            } else {
                // Check for BLUETOOTH_CONNECT permission
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request the missing permission
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
1001                    )
                    return@setOnClickListener
                }
                // Enable Bluetooth
                mBluetoothAdapter.enable()
            }
        }


        schemeImg.setOnClickListener{

            val a = AnimationUtils.loadAnimation(requireContext(),R.anim.zoom_in)


            schemeImg.startAnimation(a)
        }




            return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Agri India"

        weatherCard.setOnClickListener {
            weatherFragment = WeatherFragment()

            val transaction = requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, weatherFragment, "name2")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit()
            data?.let { it1 -> viewModel.messageToB(it1) }
        }

        viewModel = ViewModelProvider(requireActivity())
            .get<WeatherViewModel>(WeatherViewModel::class.java)

        viewModel.getMessageA()
            .observe(viewLifecycleOwner, object : Observer<WeatherRootList?> {
                override fun onChanged(t: WeatherRootList?) {
                    Log.d("DashFrag Data Changed A", "B")
                }
            })



        cat2.setOnClickListener {



            webViewFragment= WebViewFragment()

            // Create a Bundle and add the data you want to share
            val bundle = Bundle()

            bundle.putInt("intKey", 2)

            // Set the arguments (Bundle) in FragmentB
            webViewFragment.arguments = bundle

            // Start FragmentB
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, webViewFragment)
                .addToBackStack(null)
                .commit()
        }





        cat3.setOnClickListener {



            webViewFragment= WebViewFragment()

            // Create a Bundle and add the data you want to share
            val bundle = Bundle()

            bundle.putInt("intKey", 3)

            // Set the arguments (Bundle) in FragmentB
            webViewFragment.arguments = bundle

            // Start FragmentB
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, webViewFragment)
                .addToBackStack(null)
                .commit()
        }









        cat4.setOnClickListener {
            webViewFragment= WebViewFragment()

            // Create a Bundle and add the data you want to share
            val bundle = Bundle()

            bundle.putInt("intKey", 4)

            // Set the arguments (Bundle) in FragmentB
            webViewFragment.arguments = bundle

            // Start FragmentB
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, webViewFragment)
                .addToBackStack(null)
                .commit()
        }

        cat5.setOnClickListener {

            webViewFragment= WebViewFragment()

            // Create a Bundle and add the data you want to share
            val bundle = Bundle()

            bundle.putInt("intKey", 5)

            // Set the arguments (Bundle) in FragmentB
            webViewFragment.arguments = bundle

            // Start FragmentB
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, webViewFragment)
                .addToBackStack(null)
                .commit()
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment dashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            dashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onCellClickListener(name: String) {
        val ecommerceItemFragment = EcommerceItemFragment()

        val transaction = requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, ecommerceItemFragment, name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .setReorderingAllowed(true)
            .addToBackStack("name")
            .commit()
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        p0?.let {
            val fahrenheit=p0.values[0]*9/5+32
            msg.insert(0,"Got a Sensor Event : "+
                    "${p0.values[0]} Celsiusm ($fahrenheit F)\n")
            tempTXt.text= msg
            tempTXt.invalidate()

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }



    override fun onResume() {
        super.onResume()
        // Register the listener for temperature sensor
        mgr.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        // Unregister the listener when fragment is paused
        mgr.unregisterListener(this)
    }

    private fun enableWifi() {
        // Show a Toast message





        // Get the WifiManager
        val wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as? WifiManager

        // Check if WifiManager is null
        if (wifiManager == null) {
            Toast.makeText(requireContext(), "Failed to get WifiManager", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if WiFi is not enabled and enable it if necessary
        if (!wifiManager.isWifiEnabled) {
            // For Android Q and above, start a WiFi scan
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                wifiManager.startScan()
            }

            // Enable WiFi
            wifiManager.isWifiEnabled = true
            Toast.makeText(requireContext(), "WiFi enabled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "WiFi is already enabled", Toast.LENGTH_SHORT).show()
        }


    }


}