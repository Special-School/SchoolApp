package com.specialschool.schoolapp.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.specialschool.schoolapp.R
import com.specialschool.schoolapp.model.Coordinate

class MapFragment : Fragment() {

    private lateinit var coordinate: Coordinate

    private val callback = OnMapReadyCallback { googleMap ->
        val seoul = LatLng(coordinate.latitude, coordinate.longitude)

        googleMap.addMarker(MarkerOptions().position(seoul).title("marker"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15F))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        arguments?.let {
            coordinate = it.getParcelable("school_coordinate")!!
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
