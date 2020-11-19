package com.specialschool.schoolapp.ui.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.specialschool.schoolapp.R


class Search_Detail_Fragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_search__detail_, container, false)

        val info1:TextView = view.findViewById(R.id.detail_info1)
        val info2:TextView = view.findViewById(R.id.detail_info2)
        val info3:TextView = view.findViewById(R.id.detail_info3)
        val info4:TextView = view.findViewById(R.id.detail_info4)
        val info5:TextView = view.findViewById(R.id.detail_info5)
        val info6:TextView = view.findViewById(R.id.detail_info6)
        val info7:TextView = view.findViewById(R.id.detail_info7)
        val info8:TextView = view.findViewById(R.id.detail_info8)
        val info9:TextView = view.findViewById(R.id.detail_info9)
        val info10:TextView = view.findViewById(R.id.detail_info10)

        val mapbtn:Button = view.findViewById(R.id.map_button)
        val callbtn1:Button = view.findViewById(R.id.call_btn1)
        val callbtn2:Button = view.findViewById(R.id.call_btn2)
        val homepagebtn:Button = view.findViewById(R.id.homepage_btn)

        info1.text = arguments?.getString("city")
        info2.text = arguments?.getString("establish")
        info3.text = arguments?.getString("schoolname")
        info4.text = arguments?.getString("type")
        info5.text = arguments?.getString("opening")
        info6.text = arguments?.getString("addr_num")
        info7.text = arguments?.getString("addr_detail")
        info8.text = arguments?.getString("tel1")
        info9.text = arguments?.getString("tel2")
        info10.text = arguments?.getString("url")

        callbtn1.setOnClickListener {
            val dial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${info8.text}"))
            startActivity(dial)
        }
        callbtn2.setOnClickListener {
            val dial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${info9.text}"))
            startActivity(dial)
        }
        homepagebtn.setOnClickListener {
            val homepage = Intent(Intent.ACTION_VIEW, Uri.parse("http://"+info10.text as String?))
            startActivity(homepage)
        }










        return view
    }
}