package com.bmiapptask.bmitask.presentation.main_activity.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmiapptask.dmytrodyshuk.R
import com.bmiapptask.bmitask.presentation.main_activity.model.Parameter

class ParametersAdapter(var parametersData: ArrayList<Parameter>) : RecyclerView.Adapter<ParametersAdapter.ViewHolder>() {

    init {
        parametersData.add(parametersData.size, Parameter(""))
        parametersData.add(0, Parameter(""))
    }

    override fun getItemCount() = parametersData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_parameter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = parametersData[position]

        holder.paramItem?.text = item.title
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val paramItem = itemView.findViewById(R.id.tvParamTitle) as? TextView
    }

}