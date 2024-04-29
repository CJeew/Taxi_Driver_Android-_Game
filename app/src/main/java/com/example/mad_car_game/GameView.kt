package com.example.mad_car_game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(var c: Context, var gametask: Gametask) : View(c) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myCarPosition = 0
    private val otherCars = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    // Random car genarator
    private fun generateTrafficCars() {
        val map = HashMap<String, Any>()
        map["lane"] = (0..2).random()
        map["startTime"] = time
        otherCars.add(map)
    }

    // Update traffic car positions and handle collisions
    private fun updateTrafficCars(canvas: Canvas) {
        val carWidth = viewWidth / 3
        val carHeight = viewWidth / 4

        for (i in otherCars.indices) {
            try {
                val carX = otherCars[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var carY = time - otherCars[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.cargreen, null)//other cars

                d2.setBounds(
                    carX + 25, carY - carHeight, carX + carWidth - 25, carY
                )

                d2.draw(canvas)
                if (otherCars[i]["lane"] as Int == myCarPosition) {
                    if (carY > viewHeight - 2 - carHeight
                        && carY < viewHeight - 2
                    ) {
                        gametask.closeGame(score)
                    }
                }
                if (carY > viewHeight + carHeight) {
                    otherCars.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + speed) {
            generateTrafficCars()
        }

        time = time + 10 + speed

        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.car, null)


        val playerCarHeight = viewWidth / 4
        d.setBounds(
            myCarPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - playerCarHeight,
            myCarPosition * viewWidth / 3 + viewWidth / 15 + viewWidth / 3 - 25,
            viewHeight - 2
        )

        d.draw(canvas)
        myPaint!!.color = Color.GREEN


        updateTrafficCars(canvas)

        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myCarPosition > 0) {
                        myCarPosition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (myCarPosition < 2) {
                        myCarPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }
}
