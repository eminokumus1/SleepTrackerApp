/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eminokumus.sleeptracker.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eminokumus.sleeptracker.R
import com.eminokumus.sleeptracker.database.SleepDatabase
import com.eminokumus.sleeptracker.databinding.FragmentSleepQualityBinding
import com.eminokumus.sleeptracker.databinding.FragmentSleepTrackerBinding
import com.eminokumus.sleeptracker.sleeptracker.SleepTrackerViewModelFactory
import com.eminokumus.sleeptracker.sleepquality.SleepQualityViewModelFactory

/**
 * Fragment that displays a list of clickable icons,
 * each representing a sleep quality rating.
 * Once the user taps an icon, the quality is set in the current sleepNight
 * and the database is updated.
 */
class SleepQualityFragment : Fragment() {
    private lateinit var binding: FragmentSleepQualityBinding

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_quality, container, false
        )

        val application = requireNotNull(this.activity).application

        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

        val sleepQualityViewModel =
            ViewModelProvider(this, viewModelFactory)[SleepQualityViewModel::class.java]

        binding.sleepQualityViewModel = sleepQualityViewModel

        sleepQualityViewModel.navigateToSleepTracker.observe(
            viewLifecycleOwner,
            Observer { isNavigated ->
                if (isNavigated == true) {
                    this.findNavController()
                        .navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                    sleepQualityViewModel.doneNavigating()
                }
            })
        binding.qualityZeroImage.setOnClickListener {
            sleepQualityViewModel.onSetSleepQuality(0)
        }
        binding.qualityOneImage.setOnClickListener {
            sleepQualityViewModel.onSetSleepQuality(1)

        }
        binding.qualityTwoImage.setOnClickListener {
            sleepQualityViewModel.onSetSleepQuality(2)

        }
        binding.qualityThreeImage.setOnClickListener {
            sleepQualityViewModel.onSetSleepQuality(3)

        }
        binding.qualityFourImage.setOnClickListener {
            sleepQualityViewModel.onSetSleepQuality(4)

        }
        binding.qualityFiveImage.setOnClickListener {
            sleepQualityViewModel.onSetSleepQuality(5)
        }
        return binding.root
    }


}
