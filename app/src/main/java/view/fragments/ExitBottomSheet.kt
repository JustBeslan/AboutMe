package view.fragments

import about.me.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.system.exitProcess

class ExitBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "ExitBottomSheet"
    }

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.bottom_sheet_exit_layout, container, false)

        binding.findViewById<Button>(R.id.exitButton).setOnClickListener {
            exitProcess(0)
        }

        binding.findViewById<Button>(R.id.returnButton).setOnClickListener {
            dismiss()
        }

        return binding
    }
}