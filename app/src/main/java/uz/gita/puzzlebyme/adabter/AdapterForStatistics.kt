package uz.gita.puzzlebyme.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import uz.gita.puzzlebyme.R
import uz.gita.puzzlebyme.model.DataStatistics

class AdapterForStatistics: ListAdapter<DataStatistics, HolderForStatistics>(object: DiffUtil.ItemCallback<DataStatistics>(){
    override fun areItemsTheSame(
        oldItem: DataStatistics,
        newItem: DataStatistics
    ): Boolean {
        return oldItem.moves==newItem.moves
    }

    override fun areContentsTheSame(
        oldItem: DataStatistics,
        newItem: DataStatistics
    ): Boolean {
        return oldItem==newItem
    }
} ){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderForStatistics {
        return HolderForStatistics(LayoutInflater.from(parent.context).inflate(R.layout.item_winners,parent, false))
    }

    override fun onBindViewHolder(holder: HolderForStatistics, position: Int) {
        holder.bind(getItem(position))
    }
}