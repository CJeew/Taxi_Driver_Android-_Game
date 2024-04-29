package com.example.mad_car_game

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), Gametask {
    lateinit var rootLayout: LinearLayout
    lateinit var startButton: Button
    lateinit var mGameView: GameView
    lateinit var score: TextView
    lateinit var textView:TextView
    lateinit var highScoreText: TextView
    lateinit var shapes: ImageView


    private var highScore = 0
    private val HIGH_SCORE_KEY = "high_score"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        shapes =findViewById(R.id.shapes)
        highScoreText =findViewById(R.id.highScore)
        startButton = findViewById(R.id.goBtn)
        score = findViewById(R.id.score)
        textView= findViewById(R.id.head)
        rootLayout = findViewById(R.id.rootLayout)

        startButton.setOnClickListener {
            startGame()
        }

        val sharedPreferences = getSharedPreferences("myPrefs" , Context.MODE_PRIVATE)
        highScore = sharedPreferences.getInt(HIGH_SCORE_KEY,0)
        highScoreText.text = "High Score : $highScore"


    }

    override fun closeGame(mScore: Int) {
        if (mScore > highScore) {
            highScore = mScore
            highScoreText.text = "High Score: $highScore"
            val sharedPreferences = getSharedPreferences("myPrefs" , Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(HIGH_SCORE_KEY , highScore)
            editor.apply()
        }

        runOnUiThread {
            score.text = "Score : $mScore"
            startButton.visibility = View.VISIBLE
            score.visibility = View.VISIBLE
            rootLayout.removeView(mGameView)
            textView.visibility = View.VISIBLE


        }
    }
    private fun startGame() {
        mGameView = GameView(this, this)
        mGameView.setBackgroundResource(R.drawable.road)
        rootLayout.addView(mGameView)
        startButton.visibility = View.GONE
        score.visibility = View.GONE
        score.visibility = View.GONE
        textView.visibility = View.GONE
        shapes.visibility =View.GONE

    }

}
