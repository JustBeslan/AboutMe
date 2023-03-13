package view

import about.me.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BottomFragmentWelcome().apply {
            isCancelable = false
            show(supportFragmentManager, "WelcomeNewUser_BottomSheetDialog")
        }
    }
}