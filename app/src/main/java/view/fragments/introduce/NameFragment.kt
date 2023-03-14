package view.fragments.introduce

import about.me.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import model.Profile

class NameFragment : Fragment() {

    private lateinit var firstNameTextInputLayout: TextInputLayout
    private lateinit var lastNameTextInputLayout: TextInputLayout

    private lateinit var firstNameTextInputEditText: TextInputEditText
    private lateinit var lastNameTextInputEditText: TextInputEditText
    private lateinit var patronymicTextInputEditText: TextInputEditText

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var patronymic: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_name, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        firstNameTextInputLayout = view.findViewById(R.id.firstNameLayout)
        lastNameTextInputLayout = view.findViewById(R.id.lastNameLayout)

        firstNameTextInputEditText = view.findViewById(R.id.firstName)
        lastNameTextInputEditText = view.findViewById(R.id.lastName)
        patronymicTextInputEditText = view.findViewById(R.id.patronymic)

        firstNameTextInputEditText.addTextChangedListener(TextFieldValidation(firstNameTextInputEditText))
        lastNameTextInputEditText.addTextChangedListener(TextFieldValidation(lastNameTextInputEditText))

        arguments?.takeIf { it.containsKey("position") }?.apply {
            val editText = view.findViewById<TextView>(R.id.indicatorPage)
            editText.text = getString("position")
        }
    }

    private fun validateFirstName(): Boolean {
        firstName = firstNameTextInputEditText.text.toString()
        if (firstName.isEmpty())
            firstNameTextInputLayout.error = requireContext().getString(R.string.requiredField)
        else if (!firstNameTextInputLayout.error.isNullOrEmpty())
            firstNameTextInputLayout.error = ""
        return firstName.isNotEmpty()
    }

    private fun validateLastName(): Boolean {
        lastName = lastNameTextInputEditText.text.toString()
        if (lastName.isEmpty())
            lastNameTextInputLayout.error = requireContext().getString(R.string.requiredField)
        else if (!lastNameTextInputLayout.error.isNullOrEmpty())
            lastNameTextInputLayout.error = ""
        return lastName.isNotEmpty()
    }

    fun validate(callback: (Boolean)->Unit) {
        if (isValid()) {
            if (Profile.firstName != firstName)
                Profile.firstName = firstName
            if (Profile.lastName != lastName)
                Profile.lastName = lastName
            if (Profile.patronymic != patronymic)
                Profile.patronymic = patronymic
        }
        callback(isValid())
    }

    private fun isValid(): Boolean {
        patronymic = patronymicTextInputEditText.text.toString()
        return validateFirstName() && validateLastName()
    }

    private inner class TextFieldValidation(private val view: View): TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.firstName -> validateFirstName()
                R.id.lastName -> validateLastName()
            }
        }
    }
}