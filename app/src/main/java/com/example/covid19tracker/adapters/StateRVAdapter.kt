package com.example.covid19tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19tracker.R
import com.example.covid19tracker.models.covidTrackerModel

class StateRVAdapter(private val stateList:List<covidTrackerModel>):
    RecyclerView.Adapter<StateRVAdapter.stateViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): stateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)

        return stateViewHolder(view)
    }

    override fun onBindViewHolder(holder: stateViewHolder, position: Int) {
        val stateData = stateList[position]
        holder.stateCases.text = stateData.cases.toString()
        holder.stateDeaths.text = stateData.deaths.toString()
        holder.stateName.text = stateData.state
        holder.stateRecovered.text = stateData.recovered.toString()

    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    class stateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val stateName: TextView = itemView.findViewById(R.id.TVStates)
        val stateCases: TextView = itemView.findViewById(R.id.TVCases)
        val stateRecovered: TextView = itemView.findViewById(R.id.TVRecovered)
        val stateDeaths: TextView = itemView.findViewById(R.id.TVDeaths)
    }
}