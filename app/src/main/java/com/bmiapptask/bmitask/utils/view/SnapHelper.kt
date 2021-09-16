package com.bmiapptask.bmitask.utils.view

import android.content.Context
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bmiapptask.dmytrodyshuk.R
import kotlinx.android.synthetic.main.item_parameter.view.*

class SnapHelper(private val context: Context): LinearSnapHelper() {

    var itemView: View? = null

    fun attachToRecyclerView(recyclerView: RecyclerView , layoutManager: LinearLayoutManager){
        super.attachToRecyclerView(recyclerView)
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    itemView?.tvParamTitle?.typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
                    itemView?.tvParamTitle?.setTextColor(ContextCompat.getColor(context, R.color.colorGray))
                    this@SnapHelper.findSnapView(layoutManager)?.let { centerView ->
                        itemView = centerView
                        centerView.tvParamTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                        centerView.tvParamTitle.typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
                        centerView.tvParamTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    }
                }
            }
        }

        recyclerView.addOnScrollListener(listener)
        Handler().postDelayed({
            (layoutManager.getChildAt(1)?.findViewById(R.id.tvParamTitle) as? TextView)?.let { view ->
                itemView = view
                view.tvParamTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                view.tvParamTitle.typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
                view.tvParamTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
        }, 200)
    }

}