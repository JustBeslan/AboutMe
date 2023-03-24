package viewModel.fragments.introduce

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import model.Profile

@BindingAdapter("errorText")
fun setErrorText(textInputLayout: TextInputLayout, errorText: String) {
    textInputLayout.error = errorText
}

class NameFragmentViewModel: ViewModel() {

    companion object {
        class NameFragmentFactory : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(NameFragmentViewModel::class.java))
                    return NameFragmentViewModel() as T
                throw java.lang.IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    val firstName = MutableLiveData("")
    val lastName = MutableLiveData("")
    val patronymic = MutableLiveData("")

    fun isValidData(): Boolean {
        val isValid = !firstName.value.isNullOrEmpty() && !lastName.value.isNullOrEmpty()
        if (isValid) {
            Profile.firstName = firstName.value.toString()
            Profile.lastName = lastName.value.toString()
            Profile.patronymic = patronymic.value.toString()
        }
        return isValid
    }
}