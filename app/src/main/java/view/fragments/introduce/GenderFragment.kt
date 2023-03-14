package view.fragments.introduce

import about.me.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import model.Gender
import model.Profile

class GenderFragment : Fragment() {

    private var gender: Gender? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_gender, container, false)

    fun validate(callback: (Boolean)->Unit) {
        if (gender != null && Profile.gender != gender)
            Profile.gender = gender
        callback(gender != null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("position") }?.apply {
            val editText = view.findViewById<TextView>(R.id.indicatorPage)
            editText.text = getString("position")
        }

        view.findViewById<RadioGroup>(R.id.genderRadioGroup).setOnCheckedChangeListener { _, checkedId ->
            gender = if (checkedId == R.id.maleRadioButton) Gender.MALE else Gender.FEMALE
        }
    }
}