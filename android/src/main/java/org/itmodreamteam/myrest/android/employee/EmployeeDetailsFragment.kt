package org.itmodreamteam.myrest.android.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentEmployeeDetailsBinding
import org.itmodreamteam.myrest.android.ui.afterTextChanged
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@AndroidEntryPoint
class EmployeeDetailsFragment : Fragment() {
    private val args: EmployeeDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var employeeDetailsViewModelFactory: EmployeeDetailsViewModel.AssistedFactory
    private val model: EmployeeDetailsViewModel by viewModels { ->
        EmployeeDetailsViewModel.provideFactory(
            employeeDetailsViewModelFactory,
            args.employeeId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEmployeeDetailsBinding.inflate(inflater, container, false)
        binding.model = model
        val adapter = EmployeeReservationListAdapter(model, object: ClickListener {
            override fun onClick(reservation: ReservationInfo) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Потвердить бронирование?")
                    .setMessage("${reservation.activeFrom} - ${reservation.activeUntil}")
                    .setNeutralButton("Еще подумаю") { dialog, which ->

                    }
                    .setNegativeButton("Отклонить") { dialog, which ->
                        model.reject(reservation)
                    }
                    .setPositiveButton("Подтвердить") { dialog, which ->
                        model.approve(reservation)
                    }
                    .show()
            }
        })
        binding.reservations.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        model.reservations.observe(viewLifecycleOwner) { reservations ->
            adapter.submitList(reservations)
        }

        binding.dateEdit.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выберите дату")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneId.of("UTC")
                ).toLocalDate()
                model.setSearchDate(date)
                binding.dateEdit.setText("${date.month.value} ${date.dayOfMonth}")
            }
            picker.show(childFragmentManager, picker.toString())
        }

        binding.searchEdit.afterTextChanged {
            model.search(it)
        }

        return binding.root
    }
}