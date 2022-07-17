package com.leeseungyun1020.position

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.leeseungyun1020.position.databinding.FragmentMapsBinding

class MapsFragment : Fragment() {
    private val model: MapsViewModel by activityViewModels()
    private lateinit var map: GoogleMap
    private var previousMarker: Marker? = null
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        map = googleMap
        map.setMinZoomPreference(15.0f)
        map.setMaxZoomPreference(20.0f)
        val pos = LatLng(37.503617, 127.044844)
        previousMarker = map.addMarker(
            MarkerOptions().position(pos).title("Sample location")
        )
        val circleOptions = CircleOptions().apply {
            center(LatLng(37.503617, 127.044844))
            radius(100.0)
            strokeWidth(5f)
            strokeColor(Color.rgb(27, 115, 232))
            fillColor(Color.argb(26, 27, 115, 232))
            clickable(true)
        }
        map.addCircle(circleOptions)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        var isCameraSet = false
        model.location.observe(viewLifecycleOwner) { location ->
            val now = LatLng(location?.latitude ?: 37.503617, location?.longitude ?: 127.044844)
            previousMarker?.remove()
            previousMarker = map.addMarker(
                MarkerOptions()
                    .position(now)
                    .title("${location?.latitude} ${location?.longitude}")
            )
            if (!isCameraSet) {
                Toast.makeText(context, "Location $location", Toast.LENGTH_LONG).show()
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(now, 17.0f))
                isCameraSet = true
            }
            val compare = Location("center")
            compare.latitude = 37.503617
            compare.longitude = 127.044844
            if (location.distanceTo(compare) <= 100) {
                binding.noticeText.text = "IN"
            } else {
                binding.noticeText.text = "OUT"
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}