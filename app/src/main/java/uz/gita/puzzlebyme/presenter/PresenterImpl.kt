package uz.gita.puzzlebyme.presenter

import android.content.Context
import uz.gita.puzzlebyme.Contract
import uz.gita.puzzlebyme.model.ModelImpl

class PresenterImpl(val view: Contract.View, val context: Context) : Contract.Presenter {
    private lateinit var viewImpl :Contract.View
    private lateinit var modelImpl: Contract.Model
    private lateinit var list: ArrayList<String>
    private var xEmpty: Int = 0
    private var yEmpty: Int = 0
    private var counter = 0

    init {
        modelImpl = ModelImpl(context)
        viewImpl=view
    }

    override fun newGame() {
        viewImpl.joinMoves("0")
        viewImpl.joinClock("0")
        list = modelImpl.getShuffleGame()
        val index = list.indexOf("*")
        xEmpty = index / 4
        yEmpty = index % 4
        viewImpl.joinNumbers(list)
        viewImpl.joinClickableGame()
        counter = 0

        viewImpl.joinMusicState(modelImpl.getStateMusic())
        viewImpl.joinVoiceState(modelImpl.getStateVoice())

        modelImpl.saveStateGame(" ")
    }

    override fun joinGame() {
        if (modelImpl.isContinue()) {
            val stateGame: ArrayList<String> =
                (modelImpl.getStateGame().split("%") as ArrayList<String>)
            counter = stateGame.removeAt(0).toInt()
            viewImpl.joinMoves(counter.toString())
            viewImpl.joinClock(stateGame.removeAt(0))
            list = stateGame
            val index = list.indexOf("*")
            xEmpty = index / 4
            yEmpty = index % 4
            viewImpl.joinNumbers(list)

            viewImpl.joinMusicState(modelImpl.getStateMusic())
            viewImpl.joinVoiceState(modelImpl.getStateVoice())
        } else
            newGame()

    }

    override fun joinMoves(xy: String) {
        var boolean = false
        val x1y1 = xy.toInt()
        val x: Int = x1y1 / 10
        val y: Int = x1y1 % 10
        val arr = Array(4) { Array(4) { " " } }
        var k = 0
        for (item in list) {
            arr[k / 4][k % 4] = item
            k++
        }
        if (x == xEmpty) {
            if (yEmpty > y) {
                while (yEmpty != y) {
                    val memory = arr[x][yEmpty - 1]
                    arr[x][yEmpty - 1] = arr[x][yEmpty]
                    arr[x][yEmpty] = memory
                    yEmpty--
                    boolean = true
                }
            }
            if (yEmpty < y) {
                while (yEmpty != y) {
                    val memory = arr[x][yEmpty + 1]
                    arr[x][yEmpty + 1] = arr[x][yEmpty]
                    arr[x][yEmpty] = memory
                    yEmpty++
                    boolean = true
                }
            }
        }
        if (y == yEmpty) {
            if (xEmpty > x) {
                while (xEmpty != x) {
                    val memory = arr[xEmpty - 1][yEmpty]
                    arr[xEmpty - 1][yEmpty] = arr[xEmpty][yEmpty]
                    arr[xEmpty][yEmpty] = memory
                    xEmpty--
                    boolean = true
                }
            }
            if (xEmpty < x) {
                while (xEmpty != x) {
                    val memory = arr[xEmpty + 1][yEmpty]
                    arr[xEmpty + 1][yEmpty] = arr[xEmpty][yEmpty]
                    arr[xEmpty][yEmpty] = memory
                    xEmpty++
                    boolean = true
                }
            }
        }

        k = 0
        for (row in arr) {
            for (column in row) {
                list.set(k++, column)
            }
        }

        if (boolean) {
            counter++
            if (isWinner()) {
                viewImpl.stopGame()
                viewImpl.stopTimer()
                modelImpl.saveStateGame(" ")
                val time=viewImpl.getTimer()
                var string=""
                if (time==0.toLong()){
                    string = "%${counter}%1"
                }
                else {string = "%${counter}%${viewImpl.getTimer()}"}
                modelImpl.saveWinners(string)
                viewImpl.joinFireworks()
            }

            viewImpl.startVoice()
            viewImpl.joinMoves(counter.toString())
            viewImpl.joinNumbers(list)
        }
    }

    override fun joinMusicState() {
        viewImpl.joinMusicState(!modelImpl.getStateMusic())
        modelImpl.setStateMusic(!modelImpl.getStateMusic())
    }

    override fun joinVoiceState() {
        viewImpl.joinVoiceState(!modelImpl.getStateVoice())
        modelImpl.setStateVoice(!modelImpl.getStateVoice())
    }

    override fun saveGame(moves: String, time: String) {
        var string = "$moves%$time%"
        for (item in 0..14) {
            string += list.get(item) + "%"
        }
        string+=list.get(list.size-1)
        modelImpl.saveStateGame(string)
    }

    private fun isWinner(): Boolean {
        var counter = 0
        var boolean = false
        for (item in 1..15) {
            if (list.get(item - 1) == "$item")
                counter++
        }
        if (counter == 15) {
            boolean = true
        }
        return boolean
    }
}