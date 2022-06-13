package uz.gita.puzzlebyme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import uz.gita.puzzlebyme.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

    }

    fun clickHomeButtonAbout(view: View) {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}