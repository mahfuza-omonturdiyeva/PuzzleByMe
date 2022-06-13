package uz.gita.puzzlebyme.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.airbnb.lottie.LottieAnimationView
import uz.gita.puzzlebyme.Contract
import uz.gita.puzzlebyme.R
import uz.gita.puzzlebyme.presenter.PresenterImpl

class GameActivity : AppCompatActivity(), Contract.View {
    private lateinit var presenterImpl: Contract.Presenter
    private lateinit var movesTextView: TextView
    private lateinit var timeChronometer: Chronometer
    private lateinit var buttonMoved: Button
    private lateinit var musicImageView: ImageView
    private lateinit var songClick: ImageView
    private var onClickable = true
    private var pauseOffset: Long = 0
    private lateinit var containerButtons: ConstraintLayout
    private var music: MediaPlayer? = null
    private var soundPool: SoundPool?= null
    private var isPlaySound: Boolean = false
    private var isWinner: Boolean = false
    private var sound:Int=0

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4x4)
        movesTextView = findViewById(R.id.counterMoves)
        timeChronometer = findViewById(R.id.ic_chronometer)
        musicImageView = findViewById(R.id.song)
        songClick = findViewById(R.id.click_song)
        containerButtons = findViewById(R.id.containerButtons)
        presenterImpl = PresenterImpl(this, this)
        timeChronometer.stop()

        soundPool = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            val audioAttributes= AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()
        } else{
            SoundPool(1,AudioManager.STREAM_MUSIC,0)
        }
        sound= soundPool?.load(this,R.raw.tab, 1)!!
        presenterImpl.joinGame( )
    }

    override fun onRestart() {
        super.onRestart()
        presenterImpl.joinGame()
    }

    fun onClick(view: View) {
        if (onClickable) {
            timeChronometer.base = SystemClock.elapsedRealtime() - pauseOffset
            timeChronometer.start()
            onClickable = false
        }
        buttonMoved = view as Button
        presenterImpl.joinMoves(buttonMoved.tag.toString())
    }

    fun clickHomeButtonGame(view: View) {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
        pauseOffset=timeChronometer.base
        if (!isWinner && !onClickable)
            presenterImpl.saveGame(
                movesTextView.text.toString(),
                (SystemClock.elapsedRealtime() - pauseOffset).toString()
            )
        music?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        music?.stop()
        music = null
        soundPool?.release()
        soundPool = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    fun clickRestartButtonGame(view: View) {
        onClickable = true
        timeChronometer.stop()
        presenterImpl.newGame()
    }

    fun clickMusicButtonGame(view: View) {
        presenterImpl.joinMusicState()
    }

    fun clickVoiceButtonGame(view: View) {
        presenterImpl.joinVoiceState()
    }

    override fun joinMoves(moves: String) {
        movesTextView.text = moves
    }

    override fun joinClock(time: String) {
        pauseOffset = time.toLong()
        timeChronometer.base = SystemClock.elapsedRealtime() - pauseOffset
        timeChronometer.stop()
    }


    override fun joinNumbers(list: List<String>) {
        var k = 0
        for (i in 0..15) {
            (containerButtons.get(i) as Button).visibility = View.VISIBLE
        }

        for (item in list) {
            if (item == "*") {
                (containerButtons.get(k++) as Button).visibility = View.INVISIBLE
            } else
                (containerButtons.get(k++) as Button).text = item
        }
    }

    override fun joinMusicState(onPlay: Boolean) {
        if (onPlay) {
            musicImageView.setImageResource(R.drawable.ic_play_music_note_24)
            if(music==null) {
                music = MediaPlayer.create(this, R.raw.audio)
                music?.isLooping=true
                music?.start()
            }
            else
            music?.start()
        } else {
            musicImageView.setImageResource(R.drawable.ic_baseline_music_off_24)
            if (music != null) {
                music?.pause()
                //music?.prepare()
            }
        }
    }

    override fun joinVoiceState(onPlay: Boolean) {
        isPlaySound = onPlay
        if (onPlay) {
            songClick.setImageResource(R.drawable.ic_baseline_volume_up_24)
        }
        else{
            songClick.setImageResource(R.drawable.ic_baseline_volume_off_24)
        }
    }


    override fun getTimer(): Long {
        return SystemClock.elapsedRealtime()-timeChronometer.base
    }

    override fun stopTimer() {
        timeChronometer.stop()
    }


    override fun joinClickableGame() {
        for (item in 0..15)
            containerButtons.get(item).isClickable = true
        onClickable = true
    }

    override fun startVoice() {
        if (isPlaySound) {
            soundPool?.play(sound, 1F, 1F,0,0, 1F)
        }
    }

    override fun stopGame() {
        for (item in 0..15)
            containerButtons.get(item).isClickable = false
    }

    override fun joinFireworks() {
        isWinner = true
        val lottie1 = findViewById<LottieAnimationView>(R.id.lottie1)
        val lottie2 = findViewById<LottieAnimationView>(R.id.lottie2)
        val textWin = findViewById<TextView>(R.id.text_congratulations)

        lottie1.visibility = View.VISIBLE
        lottie1.setAnimation(R.raw.fireworks)
        lottie1.playAnimation()
        lottie2.visibility = View.VISIBLE
        lottie2.setAnimation(R.raw.fireworks)
        lottie2.playAnimation()
        textWin.visibility = View.VISIBLE

        textWin.animate().translationY(((-1400).toFloat())).setDuration(5000).setStartDelay(0)
        lottie1.animate().setDuration(5000).setStartDelay(5000)
        lottie2.animate().setDuration(5000).setStartDelay(5000)

        Handler().postDelayed((object : Runnable {
            override fun run() {
                presenterImpl.newGame()
                textWin.visibility = View.INVISIBLE
                textWin.animate().translationY(0.toFloat())

            }
        }), 5000)
    }
}