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

    var search: Search_Test_Activity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)

        search = context as Search_Test_Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_search__detail_, container, false)

        val t1: TextView = view.findViewById(R.id.detail_info1)
        val t2: TextView = view.findViewById(R.id.detail_info2)
        val t3: TextView = view.findViewById(R.id.detail_info3)
        val t4: TextView = view.findViewById(R.id.detail_info4)
        val t5: TextView = view.findViewById(R.id.detail_info5)
        val t6: TextView = view.findViewById(R.id.detail_info6)
        val t7: TextView = view.findViewById(R.id.detail_info7)
        val t8: TextView = view.findViewById(R.id.detail_info8)
        val t9: TextView = view.findViewById(R.id.detail_info9)
        val t10: TextView = view.findViewById(R.id.detail_info10)

        val btn1: Button = view.findViewById(R.id.map_button)
        val btn2: Button = view.findViewById(R.id.call_btn1)
        val btn3: Button = view.findViewById(R.id.call_btn2)
        val btn4: Button = view.findViewById(R.id.homepage_btn)
        t1.setText(arguments?.getString("city"))
        t2.setText(arguments?.getString("establish"))
        t3.setText(arguments?.getString("schoolname"))
        t4.setText(arguments?.getString("type"))
        t5.setText(arguments?.getString("opening"))
        t6.setText(arguments?.getString("addr_num"))
        t7.setText(arguments?.getString("addr_detail"))
        t8.setText(arguments?.getString("tel1"))
        t9.setText(arguments?.getString("tel2"))
        t10.setText(arguments?.getString("url"))

        btn2.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${arguments?.getString("tel1")}")
            startActivity(intent)
        }
        btn3.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${arguments?.getString("tel2")}")
            startActivity(intent)
        }
        btn4.setOnClickListener {
            var intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("http://${arguments?.getString("url")}"))
            startActivity(intent)
        }

        btn1.setOnClickListener {
            search?.setMap()
        }

        return view
    }
}
