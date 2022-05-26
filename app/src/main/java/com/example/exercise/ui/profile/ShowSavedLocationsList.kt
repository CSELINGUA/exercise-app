package com.example.exercise.ui.profile

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.exercise.R
import com.example.exercise.databinding.ActivityShowSavedLocationsListBinding


class ShowSavedLocationsList : AppCompatActivity() {
    private var _binding: ActivityShowSavedLocationsListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_saved_locations_list)

//        var savedLocations = ArrayList<String>()
//        savedLocations = listOf("string","2Locations") as ArrayList<String>
//        binding.lvShowWayPoints.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,savedLocations)

    }
}