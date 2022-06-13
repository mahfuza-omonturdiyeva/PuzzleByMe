package uz.gita.puzzlebyme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.gita.puzzlebyme.ContractForStatistics
import uz.gita.puzzlebyme.R
import uz.gita.puzzlebyme.adabter.AdapterForStatistics
import uz.gita.puzzlebyme.model.DataStatistics
import uz.gita.puzzlebyme.presenter.PresenterImplForStatistics

class StatisticsActivity : AppCompatActivity(), ContractForStatistics.View {
    private lateinit var presenter: ContractForStatistics.Presenter
    private val adapter = AdapterForStatistics()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        recyclerView = findViewById(R.id.container_statics)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        presenter = PresenterImplForStatistics(this, this)
        presenter.reload()
    }

    fun clickHomeButtonStatistics(view: View) {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    fun clickGameButtonStatistics(view: View) {
        startActivity(Intent(this, GameActivity::class.java))
        finish()
    }

    override fun showList(list: List<DataStatistics>) {
        adapter.submitList(list)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}