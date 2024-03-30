package com.example.dragme

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import androidx.core.content.ContextCompat

class DragMeView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr), OnScaleGestureListener {

    enum class Mode {
        MOVE, SCALE
    }
    var MODE = Mode.SCALE
    var touchDown = false
    var scaleDown = false
    var circleStartX = 0f
    var circleStartY = 0f
    var touchDownX = 0f
    var touchDownY = 0f
    var moveX = 0f
    var moveY = 0f
    var RADIUS = 90f
    var offsetX = 0f
    var offsetY = 0f
    var scaleFactor = 1f
    val scaleGesture = ScaleGestureDetector(context, this@DragMeView)



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { scaleGesture.onTouchEvent(it) }
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.v("DragMeView", "down")
                if (MODE == Mode.MOVE) {
                    touchDown = true
                    // 判斷點擊在黑色circle內
                    val x = event.x
                    val y = event.y
                    offsetX = x - circleStartX
                    offsetY = y - circleStartY
                    val distance = Math.sqrt(
                        Math.pow(offsetX.toDouble(), 2.0) + Math.pow(
                            offsetY.toDouble(),
                            2.0
                        )
                    )
                    if (distance > RADIUS) {
                        return false
                    }
                    touchDownX = event.x
                    touchDownY = event.y
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                Log.v("DragMeView", "up")
                if (MODE == Mode.MOVE) {
                    touchDown = false
                    circleStartX = event.x - offsetX
                    circleStartY = event.y - offsetY
                    moveX = 0f
                    moveY = 0f
                    invalidate()
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (MODE == Mode.MOVE) {
                    val endX = event.x
                    val endY = event.y
                    moveX = endX - touchDownX
                    moveY = endY - touchDownY
                    invalidate()
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.black)
        canvas.drawCircle(circleStartX+moveX, circleStartY+moveY, RADIUS, paint)
        paint.textSize = 50f
        //讓文字顯示置中
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("Drag me", circleStartX+moveX, circleStartY+moveY+RADIUS*2f, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleStartX = (width*0.5f)
        circleStartY = (height*0.5f)
        Log.v("DragMeView", "circleStartX: $circleStartX, circleStartY: $circleStartY")
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        Log.v("DragMeView", "${detector.scaleFactor}")
        if (MODE == Mode.SCALE) {
            RADIUS*=detector.scaleFactor
            invalidate()
        }
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        Log.v("DragMeView", "onScaleBegin")
        scaleDown = true
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
        Log.v("DragMeView", "onScaleEnd")
        scaleDown = false
    }

}