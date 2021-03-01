package com.collegeapp.advertmarketapp.utils.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.utils.StatusCode

class StatusView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var status: Int = StatusCode.CLIENT
        set(value) {
            field = value

            when (value) {
                StatusCode.CLIENT -> status0()
                StatusCode.CREATION -> status1()
                StatusCode.PRODUCTION -> status2()
                StatusCode.MEDIA -> status3()
                StatusCode.REPORT -> status4()
            }
        }

    private val round1: View
    private val round2: View
    private val round3: View
    private val round4: View
    private val round5: View

    private val line1: View
    private val line2: View
    private val line3: View
    private val line4: View

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_status, this, true)

        round1 = root.findViewById(R.id.round_1)
        round2 = root.findViewById(R.id.round_2)
        round3 = root.findViewById(R.id.round_3)
        round4 = root.findViewById(R.id.round_4)
        round5 = root.findViewById(R.id.round_5)

        line1 = root.findViewById(R.id.line_1)
        line2 = root.findViewById(R.id.line_2)
        line3 = root.findViewById(R.id.line_3)
        line4 = root.findViewById(R.id.line_4)
    }

    private fun status0() {
        round1.background = ContextCompat.getDrawable(context, R.drawable.ic_status_round_active)
    }

    private fun status1() {
        status0()
        line1.setBackgroundColor(ContextCompat.getColor(context, R.color.title_color))
        round2.background = ContextCompat.getDrawable(context, R.drawable.ic_status_round_active)
    }

    private fun status2() {
        status1()
        line2.setBackgroundColor(ContextCompat.getColor(context, R.color.title_color))
        round3.background = ContextCompat.getDrawable(context, R.drawable.ic_status_round_active)
    }

    private fun status3() {
        status2()
        line3.setBackgroundColor(ContextCompat.getColor(context, R.color.title_color))
        round4.background = ContextCompat.getDrawable(context, R.drawable.ic_status_round_active)
    }

    private fun status4() {
        status3()
        line4.setBackgroundColor(ContextCompat.getColor(context, R.color.title_color))
        round5.background = ContextCompat.getDrawable(context, R.drawable.ic_status_round_active)
    }

}