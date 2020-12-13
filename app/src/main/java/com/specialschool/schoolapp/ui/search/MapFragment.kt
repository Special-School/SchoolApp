package com.specialschool.schoolapp.ui.search

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.specialschool.schoolapp.R
import kotlin.reflect.typeOf

class MapFragment : Fragment() {

    private var str1 : String ?=null
    private var str2 : String ?=null
    private var str3 : String ?=null
    private var lat : Double ?=null
    private var lng : Double ?= null

    private val callback = OnMapReadyCallback { googleMap ->

        //상수로 선언된 seoul -> 학교 위치를 위도, 경도로 받아와 작용됨 (마커를 만들기 위해 적용 해당 학교의 위치에 마커를 생성하고 지도를 마커위치로 옮겨주기 위해 사용)
        val seoul = LatLng(lat?.toDouble()!!, lng?.toDouble()!!)


        //str1은 현재 학교 id 데이터 적용되면 oncreateview에서 key 값 str1로 변경할것
        googleMap.addMarker(MarkerOptions().position(seoul).title(str1))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15F))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        arguments?.let {
            str1 = it.getString("str1")
            str2 = it.getString("str10")
            str3 = it.getString("str11")
            lat = it.getDouble("double1")
            lng = it.getDouble("double2")
            //필요 데이터는 str1, lat,lng 임 위도경도를 double 값으로 못받아 올경우 str2,str3를 double로 형변환해 사용함
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
