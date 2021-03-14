package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentRestaurantDetailsBinding
import java.time.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantDetailsFragment : Fragment() {
    private val args: RestaurantDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var restaurantDetailsViewModelFactory: RestaurantDetailsViewModel.AssistedFactory

    private val viewModel: RestaurantDetailsViewModel by viewModels {
        RestaurantDetailsViewModel.provideFactory(
            restaurantDetailsViewModelFactory,
            args.restaurantId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        binding.restaurant = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.reservationDate.observe(viewLifecycleOwner) { date ->
            binding.dataPicker.text = date.toString()
        }

        viewModel.reservationTime.observe(viewLifecycleOwner) { time ->
            binding.timePicker.text = time.toString()
        }

        binding.dataPicker.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выберите время")
                .build()
            picker.addOnPositiveButtonClickListener {
                viewModel.setReservationDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.of("UTC")).toLocalDate())
            }
            picker.show(childFragmentManager, picker.toString())
        }

        binding.timePicker.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(LocalDateTime.now().hour)
                .setMinute(10)
                .setTitleText("Выберите время")
                .build()
            picker.addOnPositiveButtonClickListener {
                val time = LocalTime.of(picker.hour, picker.minute)
                viewModel.setReservationTime(time)
            }
            picker.show(childFragmentManager, picker.toString())
        }

        return binding.root
    }
}