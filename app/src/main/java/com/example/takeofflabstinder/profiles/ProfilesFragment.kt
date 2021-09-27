package com.example.takeofflabstinder.profiles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.playground.utils.autoCleared
import com.example.takeofflabstinder.R
import com.example.takeofflabstinder.databinding.FragmentProfilesBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager

class ProfilesFragment : Fragment(R.layout.fragment_profiles) {
    private var binding: FragmentProfilesBinding by autoCleared()
    private val profilesViewModel: ProfilesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfilesBinding.bind(view)
        binding.profilesStackView.layoutManager = CardStackLayoutManager(context)
        binding.profilesStackView.adapter = ProfilesAdapter(listOf())

        profilesViewModel.profiles.observe(viewLifecycleOwner) {
            it?.let { profiles ->
                (binding.profilesStackView.adapter as ProfilesAdapter).profiles = profiles
                binding.likedProfileCount.text = profiles.count { profile -> profile.isMatch }.toString()
            }
        }

        profilesViewModel.getProfiles()
    }
}