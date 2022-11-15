package es.juaparser.androidtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import es.juaparser.androidtest.ui.MainFragment

/**
 * Main activity launcher and parent of the fragments
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        installSplashScreen()

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_main_frame_layout, MainFragment(), "MAINFRAGMENT")
            .addToBackStack(null)
            .commit()
    }
}