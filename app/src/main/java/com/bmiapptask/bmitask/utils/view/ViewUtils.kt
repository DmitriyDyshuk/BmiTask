package com.bmiapptask.bmitask.utils.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmiapptask.bmitask.presentation.main_activity.model.Parameter
import com.bmiapptask.bmitask.presentation.main_activity.presenter.ParametersAdapter

object ViewUtils {

    fun initParametersAdapter(recyclerView: RecyclerView, parametersData: ArrayList<Parameter>, context: Context) {
        recyclerView.adapter = ParametersAdapter(parametersData)
        val myLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = myLayoutManager
        SnapHelper(context).attachToRecyclerView(recyclerView, myLayoutManager)
    }
}