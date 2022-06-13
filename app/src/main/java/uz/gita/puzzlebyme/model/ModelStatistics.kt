package uz.gita.puzzlebyme.model

import android.content.Context
import android.util.Log
import uz.gita.puzzlebyme.ContractForStatistics
import java.util.*
import kotlin.collections.ArrayList

class ModelStatistics(context: Context) : ContractForStatistics.Model {
    private lateinit var sharedPreferences: LocalStorage

    init {
        sharedPreferences = LocalStorage(context)
    }

    override fun getStatistics(): List<DataStatistics> {
        val list = ArrayList<DataStatistics>()
        val set = TreeSet<Int>()
        val string1 = sharedPreferences.winnerList
        if (string1 != "") {
            val string =
                sharedPreferences.winnerList.substring(1, sharedPreferences.winnerList.length)
                    .split("%")
            val listSort = ArrayList<DataStatistics>()

            val moves = ArrayList<String>()
            val timer = ArrayList<String>()
            if (string.size != 0) {
                for (item in 0..(string.size - 1)) {
                    if (item % 2 == 0) {
                        moves.add(string.get(item))
                        set.add(string.get(item).toInt())
                    } else {
                        timer.add(string.get(item))
                    }
                }
            }
            for (i in 0..(moves.size - 1)) {
                list.add(DataStatistics(moves.get(i), timer.get(i)))
            }

            val listBests = TreeSet<Int>()
            if (set.size > 5) {
                for (i in 1..5) {
                    listBests.add(set.pollFirst())
                }
            } else {
                for (i in 1..set.size) {
                    listBests.add(set.pollFirst())
                }
            }

            while (listBests.size != 0) {
                val int = listBests.pollFirst()
                for (j in list) {
                    if (j.moves.equals(int.toString())) {
                        listSort.add(j)
                    }
                }
            }
            if (listSort.size > 5)
                return listSort.subList(0, 4)
            else return listSort
        }
        return list
    }

}