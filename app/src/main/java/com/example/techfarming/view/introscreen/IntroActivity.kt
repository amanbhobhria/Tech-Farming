package com.example.techfarming.view.introscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.techfarming.R
import com.example.techfarming.adapter.IntroAdapter
import com.example.techfarming.model.data.IntroData
import com.example.techfarming.view.auth.LoginActivity2


class IntroActivity : AppCompatActivity() {

    private val introSliderAdapter = IntroAdapter(
        listOf(
            IntroData(
                "Welcome to the\n\bFarming App\b",
                "Best Guide and Helper for any Farmer. Provides various features at one place!",
                R.drawable.intro_first
            ),
            IntroData(
                "Read Articles",
                "Read Online articles related to Farming Concepts, Technologies and other useful knowledge.",
                R.drawable.intro_read
            ),
            IntroData(
                "Share Knowledge",
                "Social Media let's you share knowledge with other farmers!\nCreate your own posts using Image/Video/Texts.",
                R.drawable.intro_share
            ),
            IntroData(
                "E-Commerce",
                "Buy / Sell Agriculture related products & Manage your Cart Online",
                R.drawable.intro_ecomm
            ),
            IntroData(
                "Weather Forecast",
                "Get Notified for Daily Weather Conditions. 24x7 Data",
                R.drawable.intro_weather
            ),
            IntroData(
                "APMC Statistics",
                "Get updates APMC Pricing and Commidity details everyday.",
                R.drawable.intro_statistics
            ),
            IntroData(
                "Let's Grow Together",
                "- Farming App",
                R.drawable.intro_help
            )

        )
    )


    private lateinit var sliderballs_container: LinearLayout
    private lateinit var nextBtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)


        val sliderViewPager = findViewById<ViewPager2>(R.id.sliderViewPager)
        nextBtn = findViewById<Button>(R.id.nextBtn)
        val nextBtn = findViewById<Button>(R.id.nextBtn)
        val skipIntro = findViewById<TextView>(R.id.skipIntro)
        sliderballs_container = findViewById(R.id.sliderballs_container)





        sliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        if(sliderViewPager.currentItem + 1 == introSliderAdapter.itemCount){
            Log.d("IntroActivity", sliderViewPager.currentItem.toString())
            Log.d("IntroActivity", introSliderAdapter.itemCount.toString())
            nextBtn.text = "Get Started"
        } else{
            nextBtn.text = "Next"
        }

        nextBtn.setOnClickListener {
            if (sliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                sliderViewPager.currentItem += 1
//                Toast.makeText(this, "Current: ${sliderViewPager.currentItem}", Toast.LENGTH_SHORT).show()
                nextBtn.text = "Next"
                if(sliderViewPager.currentItem + 1 == introSliderAdapter.itemCount){
                    Log.d("IntroActivity", sliderViewPager.currentItem.toString())
                    Log.d("IntroActivity", introSliderAdapter.itemCount.toString())
                    nextBtn.text = "Get Started"
                }
            } else {

                Intent(this, LoginActivity2::class.java).also {
                    startActivity(it)
                }
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("firstTime", false)
                editor.apply()
                finish()
            }
        }
        skipIntro.setOnClickListener {
            Intent(this, LoginActivity2::class.java).also {
                startActivity(it)
            }
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstTime", false)
            editor.apply()
            finish()
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }

            sliderballs_container.addView(indicators[i])


        }

    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = sliderballs_container.childCount
        for (i in 0 until childCount) {
            val imageView = sliderballs_container.get(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }

        if(index == introSliderAdapter.itemCount - 1){
            nextBtn.text = "Get Started"
        } else{
            nextBtn.text = "Next"

        }
    }
}