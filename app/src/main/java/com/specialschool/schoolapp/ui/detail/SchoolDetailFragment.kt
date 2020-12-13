package com.specialschool.schoolapp.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.specialschool.schoolapp.R
import com.specialschool.schoolapp.ui.map.MapFragment

class SchoolDetailFragment : Fragment() {

    var search: SchoolDetailActivity? = null
    private var txt = mutableListOf<String>()
    private var dou = mutableListOf<Double>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        search = context as SchoolDetailActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_school_detail, container, false)

        //세부 사항 화면에서 사용할 텍스트뷰 와 버튼을 선언

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

        /*
        t1.setText(arguments?.getString("city"))
        t1.setText(arguments?.getString("school_id"))
        t2.setText(arguments?.getString("establish"))
        t3.setText(arguments?.getString("schoolname"))
        t4.setText(arguments?.getString("type"))
        t5.setText(arguments?.getString("opening"))
        t6.setText(arguments?.getString("addr_num"))
        t7.setText(arguments?.getString("addr_detail"))
        t8.setText(arguments?.getString("tel1"))
        t9.setText(arguments?.getString("tel2"))
        t10.setText(arguments?.getString("url"))
         */

        //argument에서 리스트로 데이터 받아오기

        arguments?.let {
            txt.add(it.getString("str0").toString())
            txt.add(it.getString("str1").toString())
            txt.add(it.getString("str2").toString())
            txt.add(it.getString("str3").toString())
            txt.add(it.getString("str4").toString())
            txt.add(it.getString("str5").toString())
            txt.add(it.getString("str6").toString())
            txt.add(it.getString("str7").toString())
            txt.add(it.getString("str8").toString())
            txt.add(it.getString("str9").toString())
            txt.add(it.getString("str10").toString())
            txt.add(it.getString("str11").toString())
            txt.add(it.getString("str12").toString())
            dou.add(it.getDouble("double1")!!)
            dou.add(it.getDouble("double2")!!)
        }
        
        t1.setText(txt[1])
        t2.setText(txt[2])
        t3.setText(txt[3])
        t4.setText(txt[4])
        t5.setText(txt[5])
        t6.setText(txt[6])
        t7.setText(txt[7])
        t8.setText(txt[8])
        t9.setText(txt[9])
        t10.setText(txt[10])

        btn2.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${txt[8]}")
            startActivity(intent)
        }
        btn3.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${txt[9]}")
            startActivity(intent)
        }
        btn4.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("http://${txt[10]}"))
            startActivity(intent)
        }
        btn1.setOnClickListener {
            val map: MapFragment = MapFragment()
            val testA = activity as SchoolDetailActivity
            testA.set2(map, txt, dou)
        }

        return view
    }
}
