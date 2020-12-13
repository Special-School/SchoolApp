package com.specialschool.schoolapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.specialschool.schoolapp.R

class SearchTestActivity : AppCompatActivity() {
    private var listOfdata = mutableListOf<String>()
    private var location_data = mutableListOf<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_test)

        //search_fragment에서 정보 받아옴 fragment 구성이 안되서 activity로 화면 구성후 intent로 정보 주고 받음

        //키패드로 넘어가면서 전화번호 넘겨줌

        val testframe: FrameLayout = findViewById(R.id.scroll_frame)

        val searchdetailfragment = SearchDetailFragment()
        //리사이클러뷰에 있는 정보를 search_detail로 넘겨줌
        /*


        val mapfragment = MapFragment()

        val bundle = Bundle()
        bundle.putString("city", intent.getStringExtra("city"))
        bundle.putString("school_id", intent.getStringExtra("school_id"))
        bundle.putString("establish", intent.getStringExtra("establish"))
        bundle.putString("schoolname", intent.getStringExtra("school_name"))
        bundle.putString("type", intent.getStringExtra("type"))
        bundle.putString("opening", intent.getStringExtra("opening"))
        bundle.putString("tel1", intent.getStringExtra("tel1"))
        bundle.putString("tel2", intent.getStringExtra("tel2"))
        bundle.putString("addr_num", intent.getStringExtra("addr_num"))
        bundle.putString("addr_detail", intent.getStringExtra("addr_detail"))
        bundle.putString("url", intent.getStringExtra("url"))
        //bundle.putString("latitude",intent.getStringExtra("latitude"))
        //bundle.putString("longitude",intent.getStringExtra("longitude"))
        bundle.putString("latitude","37.58682576827939")
        bundle.putString("longitude","126.96673496601298")
        searchdetailfragment.arguments = bundle
         */

        listOfdata.add(intent.getStringExtra("school_id").toString())
        listOfdata.add(intent.getStringExtra("city").toString())
        listOfdata.add(intent.getStringExtra("establish").toString())
        listOfdata.add(intent.getStringExtra("school_name").toString())
        listOfdata.add(intent.getStringExtra("type").toString())
        listOfdata.add(intent.getStringExtra("opening").toString())
        listOfdata.add(intent.getStringExtra("addr_num").toString())
        listOfdata.add(intent.getStringExtra("addr_detail").toString())
        listOfdata.add(intent.getStringExtra("tel1").toString())
        listOfdata.add(intent.getStringExtra("tel2").toString())
        listOfdata.add(intent.getStringExtra("url").toString())
        //listOfdata.add(intent.getStringExtra("latitude").toString())
        //listOfdata.add(intent.getStringExtra("longitude").toString())
        listOfdata.add("37.58682576827939")
        listOfdata.add("126.96673496601298")
        location_data.add(37.58682576827939)
        location_data.add(126.96673496601298)





        set2(searchdetailfragment,listOfdata,location_data)


    }

    fun returnACtivity() {
        onBackPressed()
    }

    fun set1(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.scroll_frame,fragment)
        transaction.commit()
    }
    fun set2(fragment: Fragment,datalist: MutableList<String>,local_list:MutableList<Double>){
        val bundle = Bundle()
        bundle.putString("str0",datalist[0])
        bundle.putString("str1",datalist[1])
        bundle.putString("str2",datalist[2])
        bundle.putString("str3",datalist[3])
        bundle.putString("str4",datalist[4])
        bundle.putString("str5",datalist[5])
        bundle.putString("str6",datalist[6])
        bundle.putString("str7",datalist[7])
        bundle.putString("str8",datalist[8])
        bundle.putString("str9",datalist[9])
        bundle.putString("str10",datalist[10])
        bundle.putString("str11",datalist[11])
        bundle.putString("str12",datalist[12])
        bundle.putDouble("double1",local_list[0])
        bundle.putDouble("double2",local_list[1])
        fragment.arguments = bundle
        set1(fragment)
    }
}
