package view.baseInfo

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

class NameFragment : Fragment() {

    private lateinit var firstNameTextInputLayout: TextInputLayout
    private lateinit var lastNameTextInputLayout: TextInputLayout

    private lateinit var firstNameTextInputEditText: TextInputEditText
    private lateinit var lastNameTextInputEditText: TextInputEditText
    private lateinit var patronymicTextInputEditText: TextInputEditText

    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var patronymic: String

    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_name, container, false)

        firstNameTextInputLayout = binding.findViewById(R.id.firstNameLayout)
        lastNameTextInputLayout = binding.findViewById(R.id.lastNameLayout)

        firstNameTextInputEditText = binding.findViewById(R.id.firstName)
        lastNameTextInputEditText = binding.findViewById(R.id.lastName)
        patronymicTextInputEditText = binding.findViewById(R.id.patronymic)

        firstNameTextInputEditText.addTextChangedListener(TextFieldValidation(firstNameTextInputEditText))
        lastNameTextInputEditText.addTextChangedListener(TextFieldValidation(lastNameTextInputEditText))

        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("position") }?.apply {
            val editText = view.findViewById<TextView>(R.id.testTextView)
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

    fun isValid(): Boolean {
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

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment NameFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            NameFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}