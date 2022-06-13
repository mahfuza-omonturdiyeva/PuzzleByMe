package uz.gita.puzzlebyme

import uz.gita.puzzlebyme.model.DataStatistics


interface ContractForStatistics {
    interface Model{
        fun getStatistics():List<DataStatistics>
    }
    interface Presenter{
        fun reload()
    }
    interface View{
        fun showList(list: List<DataStatistics>)
    }
}