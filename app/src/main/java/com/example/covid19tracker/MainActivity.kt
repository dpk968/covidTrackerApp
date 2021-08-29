package com.example.covid19tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covid19tracker.adapters.StateRVAdapter
import com.example.covid19tracker.models.covidTrackerModel
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    lateinit var worldCases: TextView
    lateinit var worldRecovered: TextView
    lateinit var worldDeath: TextView
    lateinit var countryCases: TextView
    lateinit var countryRecovered: TextView
    lateinit var countryDeaths: TextView
    lateinit var stateRV: RecyclerView
    lateinit var stateRVAdapter: StateRVAdapter
    lateinit var stateList: List<covidTrackerModel>

//    val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.refreshLayout)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Covid19Tracker)
        setContentView(R.layout.activity_main)

        worldCases = findViewById(R.id.TVWorldCases)
        worldRecovered = findViewById(R.id.TVWorldRecovered)
        worldDeath = findViewById(R.id.TVWorldDeaths)
        countryCases = findViewById(R.id.TVIndiaCases)
        countryRecovered = findViewById(R.id.TVIndiaRecovered)
        countryDeaths = findViewById(R.id.TVIndiaDeaths)
        stateRV = findViewById(R.id.rvIndiaCases)
        stateList = ArrayList<covidTrackerModel>()

        getWorldInfo()
        getIndiaStateInfo()

    }



    private fun getIndiaStateInfo() {
        val url = "https:api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{ response ->
            try{
                val dataObj = response.getJSONObject("data")
                val summeryObj = dataObj.getJSONObject("summary")
                val cases = summeryObj.getInt("total")
                val recovered = summeryObj.getInt("discharged")
                val deaths = summeryObj.getInt("deaths")

                countryRecovered.text = recovered.toString()
                countryCases.text = cases.toString()
                countryDeaths.text = deaths.toString()

                val resionalArray = dataObj.getJSONArray("regional")
                for (i in 0 until resionalArray.length()){
                    val regionalObj = resionalArray.getJSONObject(i)
                    val stateName:String = regionalObj.getString("loc")
                    val cases:Int = regionalObj.getInt("totalConfirmed")
                    val deaths:Int = regionalObj.getInt("deaths")
                    val recovered:Int = regionalObj.getInt("deaths")

                    val stateModel = covidTrackerModel(stateName,cases,recovered,deaths)
                    stateList = stateList+stateModel
                }

                stateRVAdapter = StateRVAdapter(stateList)
                stateRV.layoutManager = LinearLayoutManager(this)
                stateRV.adapter = stateRVAdapter

            }catch (e: JSONException){
                e.printStackTrace()
            }
        },{error ->
                Toast.makeText(this,"Failed to get data", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

    private fun getWorldInfo() {
        val url = "https://corona.lmao.ninja/v3/covid-19/all"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{ response ->

            try {
                val worlsCases:Int = response.getInt("cases")
                val worlsRecovery:Int = response.getInt("recovered")
                val worlsDeaths:Int = response.getInt("deaths")

                worldCases.text = worlsCases.toString()
                worldRecovered.text = worlsRecovery.toString()
                worldDeath.text = worlsDeaths.toString()
            }catch (e: JSONException){
                e.printStackTrace()
            }

        },{error ->
            Toast.makeText(this,"Failed to Load please try after some time", Toast.LENGTH_LONG).show()
        })

        queue.add(request)
    }

}