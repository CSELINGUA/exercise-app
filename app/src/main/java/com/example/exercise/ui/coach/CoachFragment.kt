package com.example.exercise.ui.coach

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.exercise.databinding.FragmentCoachBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback


class CoachFragment : Fragment() {

    private var _binding: FragmentCoachBinding? = null

    //This property is only valid between onCreateView and
    //OnDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val coachViewModel = ViewModelProvider(this).get(CoachViewModel::class.java)
        _binding = FragmentCoachBinding.inflate(inflater,container,false)
        val root: View = binding.root




        return root

    }
}