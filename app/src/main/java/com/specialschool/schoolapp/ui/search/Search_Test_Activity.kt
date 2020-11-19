package com.specialschool.schoolapp.ui.search

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.specialschool.schoolapp.R
import com.specialschool.schoolapp.ui.settings.SettingsFragment
import java.net.URL

class Search_Test_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search__test_)
        
        //search_fragment에서 정보 받아옴 fragment 구성이 안되서 activity로 화면 구성후 intent로 정보 주고 받음



        //키패드로 넘어가면서 전화번호 넘겨줌


        val testframe:FrameLayout = findViewById(R.id.scroll_frame)
        
        //fragmnet 를 fuction 으로 프레임 레이아웃으로
        setFragment()
        //리사이클러뷰에 있는 정보를 search_detail로 넘겨줌

        val searchdetailfragment: Search_Detail_Fragment = Search_Detail_Fragment()
        var bundle = Bundle()
        bundle.putString("city",intent.getStringExtra("city"))
        bundle.putString("establish",intent.getStringExtra("establish"))
        bundle.putString("schoolname",intent.getStringExtra("school_name"))
        bundle.putString("type",intent.getStringExtra("type"))
        bundle.putString("opening",intent.getStringExtra("opening"))
        bundle.putString("tel1",intent.getStringExtra("tel1"))
        bundle.putString("tel2",intent.getStringExtra("tel2"))
        bundle.putString("addr_num",intent.getStringExtra("addr_num"))
        bundle.putString("addr_detail",intent.getStringExtra("addr_detail"))
        bundle.putString("url",intent.getStringExtra("url"))
        searchdetailfragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.scroll_frame,searchdetailfragment)
        transaction.commit()










    }
    fun setFragment(){
        val fragment = Search_Detail_Fragment()
        val transaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.scroll_frame,fragment)
        transaction.commit()
    }
    fun returnACtivity(){
        onBackPressed()
    }

}