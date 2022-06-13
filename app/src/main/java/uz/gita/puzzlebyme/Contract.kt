package uz.gita.puzzlebyme

interface Contract {

    interface Model {
        fun getShuffleGame(): ArrayList<String>
        fun isContinue(): Boolean
        fun getStateGame(): String
        fun saveStateGame(string: String)
        fun getStateVoice(): Boolean
        fun getStateMusic(): Boolean
        fun setStateVoice(boolean: Boolean): Boolean
        fun setStateMusic(boolean: Boolean): Boolean
        fun saveWinners(string: String)
    }

    interface Presenter {
        fun newGame()
        fun joinGame()
        fun joinMoves(xy: String)
        fun joinMusicState()
        fun joinVoiceState()
        fun saveGame(moves: String, time: String)
    }

    interface View {
        fun joinMoves(moves: String)
        fun joinClock(time: String)
        fun joinNumbers(list: List<String>)
        fun joinMusicState(onPlay: Boolean)
        fun joinVoiceState(onPlay: Boolean)
        fun getTimer():Long
        fun stopTimer()
        fun stopGame()
        fun joinFireworks()
        fun joinClickableGame()
        fun startVoice()
    }
}