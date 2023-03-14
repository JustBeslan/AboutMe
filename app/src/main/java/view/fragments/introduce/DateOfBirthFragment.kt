package view.fragments.introduce

import about.me.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import model.Profile
import java.time.LocalDate
import java.util.*

class DateOfBirthFragment : Fragment() {

    private lateinit var datePicker: DatePicker
    private var dateOfBirth = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_date_of_birth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        datePicker = view.findViewById(R.id.datePickerDateOfBirthFragment)

        val today = Calendar.getInstance()
        datePicker.apply {
            init(today.get(Calendar.YEAR),
                today.get(Calendar.MONTH) + 1,
                today.get(Calendar.DAY_OF_MONTH))
            { _, year, month, day -> dateOfBirth = LocalDate.of(year, month + 1, day) }
            maxDate = today.timeInMillis
        }

        arguments?.takeIf { it.containsKey("position") }?.apply {
            val editText = view.findViewById<TextView>(R.id.indicatorPage)
            editText.text = getString("position")
        }
    }

    fun validate() {
        Profile.dateOfBirth = dateOfBirth
    }
}