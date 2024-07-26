package com.example.weatherly


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherly.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 54774079e803520d7dfa523dd1d001d6
class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchWeatherData()
    }
    private fun fetchWeatherData(){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response= retrofit.getWeatherData("noida","54774079e803520d7dfa523dd1d001d6","metric")
        response.enqueue(object:Callback<weatherApp>{
            override fun onResponse(call: Call<weatherApp>, response: Response<weatherApp>) {
                val responseBody= response.body()
               if (response.isSuccessful&&responseBody!=null){
                   val sunRise=responseBody.sys.sunrise
                   val humid=responseBody.main.toString()
                   val condition=responseBody.weather.firstOrNull()?.main?: "unknown"
                   binding.weather.text=condition
                   binding.humidity.text="$humid unit"
                  binding.sunrise.text="$sunRise"
               }
            }

            override fun onFailure(call: Call<weatherApp>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
