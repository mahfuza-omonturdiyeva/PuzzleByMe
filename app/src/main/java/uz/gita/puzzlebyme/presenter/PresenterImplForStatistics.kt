package uz.gita.puzzlebyme.presenter

import android.content.Context
import uz.gita.puzzlebyme.ContractForStatistics
import uz.gita.puzzlebyme.model.ModelStatistics

class PresenterImplForStatistics(context: Context, view: ContractForStatistics.View) :
    ContractForStatistics.Presenter {
    private lateinit var  model: ContractForStatistics.Model
    private val view: ContractForStatistics.View = view
    init {
        model= ModelStatistics(context)
    }
    override fun reload() {
        view.showList(model.getStatistics())
    }
}