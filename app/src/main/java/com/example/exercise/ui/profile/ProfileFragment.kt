package com.example.exercise.ui.profile


import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.exercise.databinding.FragmentProfileBinding
import com.example.exercise.ui.coach.ProfileViewModel
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val LOCATION_CODE : Int? = 1
    private lateinit var locationCallback : LocationCallback

    //locationrequest is a config file for all services related to fuselocaitonproviderclient
    lateinit var locationRequest: LocationRequest
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    private lateinit var currentLocation: Location
    private lateinit var savedLocations : ArrayList<Location>


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_CODE -> {
                if(grantResults[0] ==PackageManager.PERMISSION_GRANTED)
                    updateGPS()
                else {
                    Toast.makeText(requireContext(),"This app requires permission", Toast.LENGTH_SHORT).show()

                }
            }
            else -> {
                print("Number should be 0 or 1.")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textProfile
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        savedLocations = ArrayList()

        locationRequest = LocationRequest()
        locationRequest.interval = 1000*30
        locationRequest.fastestInterval = 1000*3
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        binding.accuracy.setOnClickListener {
            if (accuracy.isChecked) {
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                binding.swTvSensor.text = "Using GPS sensors"
            } else {
                locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                binding.swTvSensor.text = "Using Towers + WIFI"
            }
        }

        binding.update.setOnClickListener{
            if(update.isChecked){
                startLocationUpdates()
            }
            else{
                stopLcoationUpdates()
            }
        }

        binding.btnNewWayPoint.setOnClickListener{


            //var myApplication = activity as MainActivity2
            //savedLocations = myApplication.getMyLocations
            savedLocations.add(currentLocation)

        }
        binding.btnShowWayPoint.setOnClickListener{
            var intent = Intent(requireActivity(), ShowSavedLocationsList::class.java)
            startActivity(intent)

        }

        binding.btnShowMap.setOnClickListener{
            var intent = Intent(requireActivity(), MapsActivity::class.java)
            startActivity(intent)

        }

        updateGPS()

        locationCallback  = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {

                super.onLocationResult(p0)
                updateUIValues(p0.lastLocation)




//                p0 ?: return
//                for (location in p0.locations){
//                    // Update UI with location data
//                    // ...
//                }
            }
        }




        return root
    }

    private fun startLocationUpdates() {
        binding.swTvUpdate.text = "Location is being tracked"
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        updateGPS()

    }


    private fun stopLcoationUpdates() {
        binding.swTvUpdate.text = "Location is NOT being tracked "

        binding.tvLat.text = "Not tracking location"
        binding.tvLon.text = "Not tracking location"
        binding.tvAccuracy.text = "Not tracking location"
        binding.tvAltitude.text = "Not tracking location"
        binding.tvSpeed.text = "Not tracking location"

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)

    }
    private fun updateGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                updateUIValues(location)
                currentLocation = location

            }
        }
        else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_CODE!!
                )
            }
        }
    }

    private fun updateUIValues(location : Location) {
        binding.tvLat.text = location.latitude.toString()
        binding.tvLon.text = location.longitude.toString()
        binding.tvAccuracy.text = location.accuracy.toString()


        if(location.hasAltitude()){
            binding.tvAltitude.text = location.altitude.toString()
        }
        else{
            binding.tvAltitude.text = "Not available"
        }

        if(location.hasSpeed()){
            binding.tvSpeed.text = location.speed.toString()
        }
        else{
            binding.tvSpeed.text = "Not available"
        }

        var geocoder : Geocoder = Geocoder(requireContext())


        try{
            var addresses = geocoder.getFromLocation(location.latitude,location.longitude,1)
            binding.tvAddress.text = addresses.get(0).getAddressLine(0)
        }
        catch (e: Exception ){
            binding.tvAddress.text = "Unable to get street address"

        }

        //var myApplication = context as MainActivity2
        //savedLocations = context.getMyLocations
        binding.tvCountOfCrumbs.text = savedLocations.size.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}