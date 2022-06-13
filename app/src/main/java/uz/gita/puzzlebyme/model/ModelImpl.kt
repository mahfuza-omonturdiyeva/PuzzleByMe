package uz.gita.puzzlebyme.model

import android.content.Context
import uz.gita.puzzlebyme.Contract



class ModelImpl(private val context: Context) : Contract.Model {

    private lateinit var sharedPreferences: LocalStorage

    init {
        sharedPreferences = LocalStorage(context)
    }

    override fun getShuffleGame(): ArrayList<String> {
        val list = ArrayList<String>()
        for (item in 1..14)
            list.add(item.toString())

        list.add("*")
        list.add("15")
        list.shuffle()
        return list
    }

    override fun isContinue(): Boolean {
        val string = sharedPreferences.stateGame
        return string != " "
    }

    override fun getStateGame(): String {
        return sharedPreferences.stateGame
    }

    override fun saveStateGame(string: String) {
        sharedPreferences.stateGame = string
    }

    override fun getStateVoice(): Boolean {
        return sharedPreferences.isOnVoice
    }

    override fun getStateMusic(): Boolean {
        return sharedPreferences.isOnMusic
    }

    override fun setStateVoice(boolean: Boolean): Boolean {
        sharedPreferences.isOnVoice = !sharedPreferences.isOnVoice
        return true
    }

    override fun setStateMusic(boolean: Boolean): Boolean {
        sharedPreferences.isOnMusic = !sharedPreferences.isOnMusic
        return true
    }

    override fun saveWinners(string: String) {
        var stringList = sharedPreferences.winnerList
        stringList += string
        sharedPreferences.winnerList=stringList

    }

}