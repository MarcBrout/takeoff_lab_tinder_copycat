package com.example.takeofflabstinder.profiles

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.playground.utils.autoCleared
import com.example.takeofflabstinder.R
import com.example.takeofflabstinder.databinding.FragmentProfilesBinding
import com.yuyakaido.android.cardstackview.*
import com.yuyakaido.android.cardstackview.Direction.*

class ProfilesFragment : Fragment(R.layout.fragment_profiles) {
    private var binding: FragmentProfilesBinding by autoCleared()
    private val profilesViewModel: ProfilesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfilesBinding.bind(view)
        binding.profilesStackView.apply {
            layoutManager = CardStackLayoutManager(context, object : CardStackListener {
                override fun onCardDragging(direction: Direction?, ratio: Float) {
                }

                override fun onCardSwiped(direction: Direction?) {
                    when (direction) {
                        Left -> profilesViewModel.dislike()
                        Right -> profilesViewModel.like()
                    }
                }

                override fun onCardRewound() {
                }

                override fun onCardCanceled() {
                }

                override fun onCardAppeared(view: View?, position: Int) {
                }

                override fun onCardDisappeared(view: View?, position: Int) {
                }
            }
            ).apply {
                setStackFrom(StackFrom.Bottom)
                setVisibleCount(3)
                setCanScrollVertical(false)

            }

            adapter = ProfilesAdapter(listOf())
        }


        binding.likeButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            (binding.profilesStackView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
            binding.profilesStackView.swipe()
        }

        binding.dislikeButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            (binding.profilesStackView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
            binding.profilesStackView.swipe()
        }

        profilesViewModel.profiles.observe(viewLifecycleOwner) {
            it?.let { profiles ->
                (binding.profilesStackView.adapter as ProfilesAdapter).profiles = profiles
            }
        }

        profilesViewModel.matchCount.observe(viewLifecycleOwner) {
            it?.let {
                binding.likedProfileCount.text = it.toString()
            }
        }

        profilesViewModel.getProfiles()
    }
}