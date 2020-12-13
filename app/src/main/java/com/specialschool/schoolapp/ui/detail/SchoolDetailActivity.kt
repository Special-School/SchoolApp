package com.specialschool.schoolapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import com.specialschool.schoolapp.R
import com.specialschool.schoolapp.ui.map.MapFragment

class SchoolDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_detail)

        //search_fragment에서 정보 받아옴 fragment 구성이 안되서 activity로 화면 구성후 intent로 정보 주고 받음

        //키패드로 넘어가면서 전화번호 넘겨줌

        val testframe: FrameLayout = findViewById(R.id.scroll_frame)

        //fragmnet 를 fuction 으로 프레임 레이아웃으로
        setFragment()
        //리사이클러뷰에 있는 정보를 search_detail로 넘겨줌

        val schoolDetailFragment = SchoolDetailFragment()
        val mapfragment = MapFragment()

        val bundle = Bundle()
        bundle.putString("city", intent.getStringExtra("city"))
        bundle.putString("establish", intent.getStringExtra("establish"))
        bundle.putString("schoolname", intent.getStringExtra("school_name"))
        bundle.putString("type", intent.getStringExtra("type"))
        bundle.putString("opening", intent.getStringExtra("opening"))
        bundle.putString("tel1", intent.getStringExtra("tel1"))
        bundle.putString("tel2", intent.getStringExtra("tel2"))
        bundle.putString("addr_num", intent.getStringExtra("addr_num"))
        bundle.putString("addr_detail", intent.getStringExtra("addr_detail"))
        bundle.putString("url", intent.getStringExtra("url"))
        schoolDetailFragment.arguments = bundle
        mapfragment.arguments = bundle


        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.scroll_frame, schoolDetailFragment)
        transaction.commit()
    }

    fun setFragment() {
        val fragment = SchoolDetailFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.scroll_frame, fragment)
        transaction.commit()
    }

    fun returnACtivity() {
        onBackPressed()
    }

    fun setMap() {
        val map = MapFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.scroll_frame, map)
        transaction.addToBackStack("map")
        transaction.commit()
    }
}
