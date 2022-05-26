package com.example.exercise.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise.R
import com.example.exercise.databinding.UserCardViewBinding
import com.example.exercise.model.User
import com.example.exercise.ui.home.HomeFragment
import com.example.exercise.util.GlideLoader
import kotlinx.android.synthetic.main.user_card_view.view.*

class CommunityAdaptor(
    private val context: Context,
    private val users: ArrayList<User>,
    private val homeFragment: HomeFragment
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var _binding: UserCardViewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.user_card_view,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = users[position]
        if(holder is MyViewHolder){
            //GlideLoader(context).loadUserImage(user.image,holder.itemView.imageView)
            holder.itemView.name.text = user.name
            holder.itemView.location.text = user.location
            holder.itemView.headline.text = user.headline
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}