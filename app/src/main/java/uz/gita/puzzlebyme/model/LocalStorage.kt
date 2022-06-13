package uz.gita.puzzlebyme.model

import android.content.Context
import android.content.SharedPreferences

class LocalStorage(val context: Context) {
    private val stateVoiceKey = "VoiceState"
    private val stateMusicKey = "MusicState"
    private val stateGameKey = "GameState"
    private val winnersKey = "WinnersKey5"
    private lateinit var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("Local", Context.MODE_PRIVATE)
    }

    var isOnVoice: Boolean
        get() = sharedPreferences.getBoolean(stateVoiceKey, false)
        set(value) = sharedPreferences.edit().putBoolean(stateVoiceKey, value).apply()
    var isOnMusic: Boolean
        get() = sharedPreferences.getBoolean(stateMusicKey, false)
        set(value) = sharedPreferences.edit().putBoolean(stateMusicKey, value).apply()
    var stateGame: String
        get() = sharedPreferences.getString(stateGameKey, " ").toString()
        set(value) = sharedPreferences .edit().putString(stateGameKey, value).apply()

    var winnerList: String
        get() = sharedPreferences.getString(winnersKey, "").toString()
        set(value) = sharedPreferences.edit().putString(winnersKey, value).apply()
}