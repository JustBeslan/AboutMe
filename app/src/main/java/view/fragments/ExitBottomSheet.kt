package view.fragments

import about.me.R
import about.me.databinding.BottomSheetExitBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.system.exitProcess

@BindingAdapter("android:onClick")
fun setOnClick(button: Button, listener: ()->Unit) {
    button.setOnClickListener{ listener() }
}

class ExitBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "ExitBottomSheet"
    }

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = BottomSheetExitBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        lifecycleOwner = viewLifecycleOwner
    }.root

    fun exitButtonClick() {
        exitProcess(0)
    }

    fun returnButtonClick() {
        dismiss()
    }
}