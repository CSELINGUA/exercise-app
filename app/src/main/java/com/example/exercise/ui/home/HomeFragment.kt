package com.example.exercise.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercise.R
import com.example.exercise.adaptor.CommunityAdaptor
import com.example.exercise.databinding.FragmentHomeBinding
import com.example.exercise.model.User

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        var user1: User = User("jonathan","Sal Jonathan","jona","jona","jona",true)
        loadAdaptor(arrayListOf<User>(user1,user1,user1,user1,user1,user1,user1,user1,user1,user1,user1))

        binding.toggle.addOnButtonCheckedListener{toggle, checkedId,isChecked ->
            if(isChecked){
                when(checkedId){
                    R.id.btn_all -> showToast("ALL")
                    R.id.btn_outdoor -> showToast("OUTDOOR")
                    R.id.btn_indoor -> showToast("INDOOR")
                }
            }
            else{
                if(toggle.checkedButtonId == View.NO_ID){
                    showToast("No button selected ")
                }
            }
        }
        return root
    }
    private fun showToast(str: String){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }


    fun loadAdaptor(users: ArrayList<User>){
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.setHasFixedSize(true)
        val userAdaptor = CommunityAdaptor(requireActivity(),users,this)
        binding.rv.adapter = userAdaptor
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}