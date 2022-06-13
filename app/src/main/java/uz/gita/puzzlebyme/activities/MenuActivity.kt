package uz.gita.puzzlebyme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import uz.gita.puzzlebyme.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickGameButtonMenu(view: View) {
        startActivity(Intent(this, GameActivity::class.java))
        finish()
    }

    fun clickStatisticsButtonMenu(view: View) {
        startActivity(Intent(this, StatisticsActivity::class.java))
        finish()
    }

    fun clickAboutButtonMenu(view: View) {
        startActivity(Intent(this, AboutActivity::class.java))
        finish()
    }

    fun clickExitButtonMenu(view: View) {
        finishAffinity()
    }
}