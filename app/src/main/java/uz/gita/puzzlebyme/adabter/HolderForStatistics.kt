package uz.gita.puzzlebyme.adabter

import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.puzzlebyme.R
import uz.gita.puzzlebyme.model.DataStatistics

class HolderForStatistics(view:View):RecyclerView.ViewHolder(view) {
    private val moves:TextView=view.findViewById(R.id.moves_item)
    private val timer:Chronometer=view.findViewById(R.id.clock_item)

    fun bind(dataStatistics: DataStatistics){
        this.moves.text=dataStatistics.moves
//        if (dataStatistics.timer=="0") {
//            this.timer.base = 0
//        }
//        else
       // Log.d("tag","${dataStatistics.timer} 1")
            this.timer.base=SystemClock.elapsedRealtime()-dataStatistics.timer.toString().toLong()
    }
}