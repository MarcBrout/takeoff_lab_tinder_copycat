package com.example.takeofflabstinder.profiles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.takeofflabstinder.R
import com.example.takeofflabstinder.data.datasource.local.ProfileEntity
import com.example.takeofflabstinder.databinding.ViewHolderProfileBinding

class ProfilesAdapter(profiles: List<ProfileEntity>) : RecyclerView.Adapter<ProfilesAdapter.ViewHolder>() {
    var profiles: List<ProfileEntity> = profiles
    set(value) {
        field = value
        // Heavy operation, refactor later to targeted notifications
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_profile, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(profiles[position])
    }

    override fun getItemCount() = profiles.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ViewHolderProfileBinding.bind(v)

        fun setData(profile: ProfileEntity) {
            binding.textNameAge.text = "${profile.firstName.replaceFirst(profile.firstName[0], profile.firstName[0].uppercaseChar())}, 21"
            binding.textTownCountry.text = "${profile.city.replaceFirst(profile.city[0], profile.city[0].uppercaseChar())}, ${profile.country.uppercase()} + \uD83C\uDDFA\uD83C\uDDF8"

            Glide.with(binding.photo)
                .load(profile.photos.first())
                .centerCrop()
                .into(binding.photo)
        }
    }
}