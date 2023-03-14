package view

import about.me.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import view.fragments.WelcomeBottomSheet

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.let {
                WelcomeBottomSheet().apply {
                isCancelable = false
                show(supportFragmentManager, WelcomeBottomSheet.TAG)
            }
        }
    }
}